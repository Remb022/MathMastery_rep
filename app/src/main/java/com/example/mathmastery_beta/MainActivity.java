package com.example.mathmastery_beta;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mathmastery_beta.forms.ChangeNicknameForm;
import com.example.mathmastery_beta.handlers.HandlerAdaptive;
import com.example.mathmastery_beta.handlers.HandlerDataSave;
import com.example.mathmastery_beta.level_status_model.EqualFoundModel;
import com.example.mathmastery_beta.handlers.HandlerJSON;
import com.example.mathmastery_beta.level_status_model.LevelModel;
import com.example.mathmastery_beta.level_status_model.OperandFoundModel;
import com.example.mathmastery_beta.level_status_model.OperationFoundModel;
import com.example.mathmastery_beta.level_status_model.ResultFoundModel;
import com.example.mathmastery_beta.utils.DeepLinkHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private final HandlerJSON handlerJSON = new HandlerJSON(this);
    private final HandlerDataSave handlerDataSave = new HandlerDataSave(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DeepLinkHandler.handleDeepLink(this);

        setNicknameFormListener();
        setChangeAvatarListener();
        setClickListener();

        setFunctionalHeaderIcon();
        downloadJsonFiles();
        loadNickname();
        loadAvatar();
        adaptiveComponent();

    }
    @Override
    protected void onResume() {
        super.onResume();
        updateCardLevels();
        updateProgress();
    }

    private void setNicknameFormListener(){
        TextView nicknameTextView = findViewById(R.id.nickname_header_info);
        ChangeNicknameForm changeNicknameForm = new ChangeNicknameForm(this, handlerDataSave);
        nicknameTextView.setOnClickListener(v -> changeNicknameForm.showEditNicknameDialog(nicknameTextView));
    }

    private static final int PICK_IMAGE_REQUEST = 1;
    private void setChangeAvatarListener() {
        ImageButton avatarButton = findViewById(R.id.avatar_header_icon);
        avatarButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                String filePath = handlerDataSave.copyImageFromUri(selectedImageUri);
                if (filePath != null) {
                    handlerDataSave.saveAvatar(filePath);
                    avatarRound(filePath);
                }
            }
        }
    }

    private void avatarRound(String filePath) {
        ImageButton avatarButton = findViewById(R.id.avatar_header_icon);
        Glide.with(this)
                .load(new File(filePath))
                .apply(RequestOptions.circleCropTransform())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(avatarButton);
    }

    private void loadAvatar() {
        String filePath = handlerDataSave.getAvatar();
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists()) {
                avatarRound(filePath);
            }
        }
    }

    private void setClickListener(){
        CardView operandFoundGame = findViewById(R.id.operand_found_game);
        CardView operationFoundGame = findViewById(R.id.operation_found_game);
        CardView resultFoundGame = findViewById(R.id.result_found_game);
        CardView equalFoundGame = findViewById(R.id.equal_found_game);
        CardView miniGame2048 = findViewById(R.id.mini_game_2048);
        CardView random = findViewById(R.id.random_game);

        miniGame2048.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), com.kynzai.game2048.game.MainActivity.class);
            v.getContext().startActivity(intent);
        });

        operandFoundGame.setOnClickListener(v -> startLevelBarActivity("operand_found.json",
                OperandFoundModel.class));

        operationFoundGame.setOnClickListener(v -> startLevelBarActivity("operation_found.json",
                OperationFoundModel.class));

        resultFoundGame.setOnClickListener(v -> startLevelBarActivity("result_found.json",
                ResultFoundModel.class));

        equalFoundGame.setOnClickListener(v -> startLevelBarActivity("equal_found.json",
                EqualFoundModel.class));

        random.setOnClickListener(v -> {
            String[] games = {"operand_found.json", "operation_found.json", "result_found.json", "equal_found.json", "mini_game_2048"};
            Class<?>[] cl = {OperandFoundModel.class, OperationFoundModel.class, ResultFoundModel.class, EqualFoundModel.class, null};

            int rand = (int) (Math.random() * games.length);
            String randomGame = games[rand];

            if ("mini_game_2048".equals(randomGame)) {
                Intent intent = new Intent(v.getContext(), com.kynzai.game2048.game.MainActivity.class);
                v.getContext().startActivity(intent);
            }
            else
            {
                startRandomLevelActivity(randomGame, cl[rand]);
            }
        });
    }

    private void startLevelBarActivity(String jsonFileName, Class<?> model) {
        Intent intent = new Intent(MainActivity.this, LevelBarActivity.class);
        intent.putExtra("json", jsonFileName);
        intent.putExtra("model", model.getName());

        startActivity(intent);
    }

    private void startRandomLevelActivity(String jsonFileName, Class<?> model) {
        String jsonContent = handlerJSON.loadJSON(jsonFileName);

        Gson gson = new Gson();
        Type type = TypeToken.getParameterized(List.class, model).getType();
        List<LevelModel> levelList = gson.fromJson(jsonContent, type);

        List<LevelModel> levelActive = levelList.stream()
                .filter(level -> "active".equals(level.getStatus()))
                .collect(Collectors.toList());

        Random random = new Random();
        LevelModel randomLevel = levelActive.get(random.nextInt(levelActive.size()));

        Intent intent = null;
        switch (jsonFileName) {
            case "operand_found.json":
                intent = new Intent(this, OperandFoundActivity.class);
                break;
            case "operation_found.json":
                intent = new Intent(this, OperationFoundActivity.class);
                break;
            case "result_found.json":
                intent = new Intent(this, ResultFoundActivity.class);
                break;
            case "equal_found.json":
                intent = new Intent(this, EqualFoundActivity.class);
                break;
        }

        Objects.requireNonNull(intent).putExtra("levelNumber", randomLevel.getLevel());
        intent.putExtra("json", jsonFileName);
        startActivity(intent);
    }


    private void setFunctionalHeaderIcon() {
        ImageButton functionalHeaderIcon = findViewById(R.id.functional_header_icon);
        functionalHeaderIcon.setVisibility(View.INVISIBLE);
    }

    private void downloadJsonFiles(){
        HandlerJSON handlerJSON = new HandlerJSON(this);
        handlerJSON.copyFileFromAssets("operation_found.json", "operation_found.json");
        handlerJSON.copyFileFromAssets("operand_found.json", "operand_found.json");
        handlerJSON.copyFileFromAssets("result_found.json", "result_found.json");
        handlerJSON.copyFileFromAssets("equal_found.json", "equal_found.json");
    }

    private void loadNickname() {
        TextView nicknameTextView = findViewById(R.id.nickname_header_info);
        String nickname = handlerDataSave.getNickname();
        nicknameTextView.setText(nickname);
    }

    private void adaptiveComponent(){
        HandlerAdaptive handlerAdaptive = new HandlerAdaptive(this);
        handlerAdaptive.setMainHeaderComponentSize();
        handlerAdaptive.setMainPageComponentSize();
        handlerAdaptive.setFooterTextSize();
    }

    @SuppressLint("SetTextI18n")
    private <T extends LevelModel> void setMaxLevelForCard(int id, String jsonFileName, Class<T> modelClass) {
        TextView cardTextView = findViewById(id);
        int maxLevel = handlerJSON.getMaxActiveLevel(jsonFileName, modelClass);
        cardTextView.setText("Level: " + maxLevel);
    }

    private void updateCardLevels() {
        setMaxLevelForCard(R.id.level_card_score_1, "operand_found.json", OperandFoundModel.class);
        setMaxLevelForCard(R.id.level_card_score_2, "operation_found.json", OperationFoundModel.class);
        setMaxLevelForCard(R.id.level_card_score_3, "result_found.json", ResultFoundModel.class);
        setMaxLevelForCard(R.id.level_card_score_4, "equal_found.json", EqualFoundModel.class);
    }

    private void updateProgress() {
        TextView levelFooterIndex = findViewById(R.id.level_footer_index);
        TextView levelHeaderInfo = findViewById(R.id.level_header_info);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        handlerDataSave.updateProgress(levelFooterIndex, levelHeaderInfo, progressBar);
    }

}