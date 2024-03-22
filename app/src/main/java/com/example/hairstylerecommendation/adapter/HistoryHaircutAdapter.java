package com.example.hairstylerecommendation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hairstylerecommendation.R;
import com.example.hairstylerecommendation.model.HistoryCut;
import com.example.hairstylerecommendation.viewmodel.HistoryHaircutViewHolder;
import com.example.hairstylerecommendation.viewmodel.OnItemClickListener;

import java.util.List;

public class HistoryHaircutAdapter extends RecyclerView.Adapter<HistoryHaircutViewHolder> {
    private List<HistoryCut> historyCutList;
    private OnItemClickListener listener;

    public HistoryHaircutAdapter(List<HistoryCut> historyCutList) {
        this.historyCutList = historyCutList;
    }

    @NonNull
    @Override
    public HistoryHaircutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_history, parent, false);
        return new HistoryHaircutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHaircutViewHolder holder, int position) {
        String name = historyCutList.get(position).getUserChoices();
        String date = historyCutList.get(position).getDate();
        holder.haircutNameTxt.setText(name);
        holder.dateTxt.setText(date);
        holder.haircut1.setText("1. " + historyCutList.get(position).getListOfRecommend()[0]);
        holder.haircut2.setText("2. " + historyCutList.get(position).getListOfRecommend()[1]);
        holder.haircut3.setText("3. " + historyCutList.get(position).getListOfRecommend()[2]);
    }

    @Override
    public int getItemCount() {
        return historyCutList.size();
    }
}
