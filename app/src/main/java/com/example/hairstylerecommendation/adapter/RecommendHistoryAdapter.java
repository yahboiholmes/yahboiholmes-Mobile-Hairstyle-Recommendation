package com.example.hairstylerecommendation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hairstylerecommendation.R;

import java.util.List;

public class RecommendHistoryAdapter extends RecyclerView.Adapter<RecommendHistoryAdapter.ViewHolder> {
    private List<String> recommendedHaircuts;

    public RecommendHistoryAdapter(List<String> recommendedHaircuts) {
        this.recommendedHaircuts = recommendedHaircuts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String haircut = recommendedHaircuts.get(position);
        holder.haircutTextView.setText(haircut);
    }

    @Override
    public int getItemCount() {
        return recommendedHaircuts.size() > 3 ? 3 : recommendedHaircuts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView haircutTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            haircutTextView = itemView.findViewById(R.id.haircutTextViewhistory);
        }
    }
}
