package com.example.mathmastery_beta.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mathmastery_beta.R;
import java.util.List;
import android.content.Context;


public class LevelPagerAdapter extends RecyclerView.Adapter<LevelPagerAdapter.ViewHolder> {

    private final List<List<Integer>> levelPages;
    private final Context context;

    public LevelPagerAdapter(List<List<Integer>> levelPages, Context context) {
        this.levelPages = levelPages;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_level, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<Integer> levels = levelPages.get(position);

        // Установка адаптера для сетки
        LevelGridAdapter gridAdapter = new LevelGridAdapter(levels, context);
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
