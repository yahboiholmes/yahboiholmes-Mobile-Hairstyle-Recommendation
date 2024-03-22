package com.example.hairstylerecommendation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hairstylerecommendation.R;
import com.example.hairstylerecommendation.model.Haircut;
import com.example.hairstylerecommendation.model.HaircutTree;
import com.example.hairstylerecommendation.viewmodel.OnItemClickListener;
import com.example.hairstylerecommendation.viewmodel.RecommendViewHolder;

import java.util.List;

public class RecommendHaircutAdapter extends RecyclerView.Adapter<RecommendViewHolder> {
    private List<Haircut> cardTitles;
    private OnItemClickListener listener;

    public RecommendHaircutAdapter(List<Haircut> cardTitles, OnItemClickListener listener) {
        this.cardTitles = cardTitles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_recommend_item, parent, false);
        return new RecommendViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendViewHolder holder, int position) {
        String title = cardTitles.get(position).getName();
        holder.titleTextView.setText(title);
    }

    @Override
    public int getItemCount() {
        //uncomment mo yung line 45 kung gusto mo na 3 lang lumabas
        return cardTitles.size() > 3 ? 3 : cardTitles.size();
        //return cardTitles.size();
    }
}
