package com.example.mathmastery_beta.handlers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mathmastery_beta.R;
import com.example.mathmastery_beta.forms.ChangeNicknameForm;
import com.example.mathmastery_beta.utils.DeepLinkHandler;

import org.jetbrains.annotations.Nullable;

import java.io.File;

public abstract class HandlerHeaderEvents extends AppCompatActivity {
    protected static final int PICK_IMAGE_REQUEST = 1;
    protected final HandlerDataSave handlerDataSave = new HandlerDataSave(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeepLinkHandler.handleDeepLink(this);
    }

    protected void setNicknameFormListener() {
        TextView nicknameTextView = findViewById(R.id.nickname_header_info);
        ChangeNicknameForm changeNicknameForm = new ChangeNicknameForm(this, handlerDataSave);
        nicknameTextView.setOnClickListener(v -> changeNicknameForm.showEditNicknameDialog(nicknameTextView));
    }

    protected void setChangeAvatarListener() {
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

    protected void avatarRound(String filePath) {
        ImageButton avatarButton = findViewById(R.id.avatar_header_icon);
        Glide.with(this)
                .load(new File(filePath))
                .apply(RequestOptions.circleCropTransform())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(avatarButton);
    }

    protected void loadAvatar() {
        String filePath = handlerDataSave.getAvatar();
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists()) {
                avatarRound(filePath);
            }
        }
    }

    protected void loadNickname() {
        TextView nicknameTextView = findViewById(R.id.nickname_header_info);
        String nickname = handlerDataSave.getNickname();
        nicknameTextView.setText(nickname);
    }

    protected void updateProgress() {
        TextView levelFooterIndex = findViewById(R.id.level_footer_index);
        TextView levelHeaderInfo = findViewById(R.id.level_header_info);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        handlerDataSave.updateProgress(levelFooterIndex, levelHeaderInfo, progressBar);
    }

}
