package com.example.mathmastery_beta.adaptor;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mathmastery_beta.EqualFoundActivity;
import com.example.mathmastery_beta.OperandFoundActivity;
import com.example.mathmastery_beta.OperationFoundActivity;
import com.example.mathmastery_beta.R;
import com.example.mathmastery_beta.ResultFoundActivity;
import com.example.mathmastery_beta.level_status_model.LevelModel;

import java.util.List;

public class LevelGridAdapter extends RecyclerView.Adapter<LevelGridAdapter.ViewHolder> {

    private final List<LevelModel> levels;
    private final Context context;
    private final String path;

    // Constructor with path parameter
    public LevelGridAdapter(List<LevelModel> levels, Context context, String path) {
        this.levels = levels;
        this.context = context;
        this.path = path; // Store the path value
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_level, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LevelModel levelModel = levels.get(position);
        int level = levelModel.getLevel();
        String status = levelModel.getStatus();

        holder.levelNumber.setText(String.valueOf(level));

        // Set icon based on level status
        if ("active".equals(status)) {
            holder.iconLock.setBackgroundResource(R.drawable.icon_unlock);
        } else {
            holder.iconLock.setBackgroundResource(R.drawable.icon_lock);
        }

        // Set onClickListener to open the relevant activity
        holder.itemView.setOnClickListener(v -> {
            if ("active".equals(status)) {
                Intent intent;
                switch (path) {
                    case "operand_found.json":
                        intent = new Intent(context, OperandFoundActivity.class);
                        break;
                    case "operation_found.json":
                        intent = new Intent(context, OperationFoundActivity.class);
                        break;
                    case "result_found.json":
                        intent = new Intent(context, ResultFoundActivity.class);
                        break;
                    case "equal_found.json":
                        intent = new Intent(context, EqualFoundActivity.class);
                        break;
                    default:
                        throw new IllegalStateException("Unknown type: " + path);
                }
                intent.putExtra("levelNumber", level);
                intent.putExtra("json", path);
                context.startActivity(intent);
            }
            else {
                setUnlockLevelAnimation(holder.iconLock);
            }
        });
    }

    private void setUnlockLevelAnimation(View view) {
        ObjectAnimator rotateLeft = ObjectAnimator.ofFloat(view, "rotation", 0f, -4f);
        rotateLeft.setDuration(100);

        ObjectAnimator rotateRight = ObjectAnimator.ofFloat(view, "rotation", 0f, 4f);
        rotateRight.setDuration(100);

        ObjectAnimator rotateBackLeft = ObjectAnimator.ofFloat(view, "rotation", -4f, 0f);
        rotateBackLeft.setDuration(100);

        ObjectAnimator rotateBackRight = ObjectAnimator.ofFloat(view, "rotation", 4f, 0f);
        rotateBackRight.setDuration(100);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(rotateLeft, rotateBackLeft, rotateRight, rotateBackRight);
        animatorSet.start();
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView levelNumber;
        public final View iconLock;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            levelNumber = itemView.findViewById(R.id.levelNumber);
            iconLock = itemView.findViewById(R.id.icon_lock);
        }
    }
}



