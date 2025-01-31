package com.example.mathmastery_beta.adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mathmastery_beta.LevelBarActivity;
import com.example.mathmastery_beta.R;

import java.util.List;

public class LevelGridAdapter extends RecyclerView.Adapter<LevelGridAdapter.ViewHolder> {

    private final List<Integer> levels;
    private Context context;

    public LevelGridAdapter(List<Integer> levels, Context context) {
        this.levels = levels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_level, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int level = levels.get(position);
        holder.levelNumber.setText(String.valueOf(level));

        //Переход на уровни
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, LevelBarActivity.class);
            //Передача номера уровня
            intent.putExtra("levelNumber",level);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView levelNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            levelNumber = itemView.findViewById(R.id.levelNumber);
        }
    }
}


