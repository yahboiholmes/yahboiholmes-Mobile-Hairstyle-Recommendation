package com.example.hairstylerecommendation.viewmodel;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hairstylerecommendation.R;

public class HistoryHaircutViewHolder extends RecyclerView.ViewHolder {

    LinearLayout historyLyt;
    public TextView haircutNameTxt, dateTxt, haircut1, haircut2, haircut3;

    public HistoryHaircutViewHolder(View itemView) {
        super(itemView);
        historyLyt = itemView.findViewById(R.id.historyLyt);
        haircutNameTxt = itemView.findViewById(R.id.haircutNameTxt);
        dateTxt = itemView.findViewById(R.id.dateTxt);
        haircut1 = itemView.findViewById(R.id.haircut1);
        haircut2 = itemView.findViewById(R.id.haircut2);
        haircut3 = itemView.findViewById(R.id.haircut3);
    }

}
