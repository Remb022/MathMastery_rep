package com.example.mathmastery_beta.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mathmastery_beta.R;
import com.example.mathmastery_beta.level_status_model.LevelModel;

import java.util.List;
import android.content.Context;


public class LevelPagerAdapter extends RecyclerView.Adapter<LevelPagerAdapter.ViewHolder> {

    private final List<List<LevelModel>> levelPages;
    private final Context context;
    private final String path;

    public LevelPagerAdapter(List<List<LevelModel>> levelPages, Context context, String path) {
        this.levelPages = levelPages;
        this.context = context;
        this.path = path;
    }

    @Override @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_level, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<LevelModel> levels = levelPages.get(position);

        LevelGridAdapter gridAdapter = new LevelGridAdapter(levels, context, path);
        holder.recyclerView.setLayoutManager(new GridLayoutManager(holder.itemView.getContext(), 3));
        holder.recyclerView.setAdapter(gridAdapter);
    }

    @Override
    public int getItemCount() {
        return levelPages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }
}