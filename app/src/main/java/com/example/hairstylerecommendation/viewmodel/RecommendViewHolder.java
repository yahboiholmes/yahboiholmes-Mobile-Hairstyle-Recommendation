package com.example.hairstylerecommendation.viewmodel;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hairstylerecommendation.R;

import com.example.hairstylerecommendation.viewmodel.OnItemClickListener;

public class RecommendViewHolder extends RecyclerView.ViewHolder {
    public TextView titleTextView;

    public RecommendViewHolder(View itemView, final OnItemClickListener listener) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.titleTextView);
        // Add references to other views if needed
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }

}
