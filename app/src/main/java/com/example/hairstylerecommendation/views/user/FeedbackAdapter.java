package com.example.hairstylerecommendation.views.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hairstylerecommendation.R;

import java.util.ArrayList;
import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> implements Filterable {
    private List<FeedbackItem> feedbackItems;
    private List<FeedbackItem> feedbackItemsFull;

    // Constructor
    public FeedbackAdapter(List<FeedbackItem> feedbackItems) {
        this.feedbackItems = feedbackItems;
        this.feedbackItemsFull = new ArrayList<>(feedbackItems);
    }

    // ViewHolder class
    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        public TextView fullNameTextView;
        public TextView ratingTextView;
        public TextView feedbackTextView;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            fullNameTextView = itemView.findViewById(R.id.fullNameTextViews);
            ratingTextView = itemView.findViewById(R.id.ratingTextViews);
            feedbackTextView = itemView.findViewById(R.id.feedbackTextViews);
        }
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userfeedbackitem, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        FeedbackItem feedbackItem = feedbackItems.get(position);

        holder.fullNameTextView.setText("Name: " +feedbackItem.getFullName());
        holder.ratingTextView.setText("Ratings: " + feedbackItem.getRating());
        holder.feedbackTextView.setText("Comments: " + feedbackItem.getText());
    }

    @Override
    public int getItemCount() {
        return feedbackItems.size();
    }

    @Override
    public Filter getFilter() {
        return feedbackFilter;
    }

    private Filter feedbackFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<FeedbackItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(feedbackItemsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (FeedbackItem item : feedbackItemsFull) {
                    if (item.getFullName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            feedbackItems.clear();
            feedbackItems.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public void setFeedbackItems(List<FeedbackItem> feedbackItems) {
        this.feedbackItems = feedbackItems;
        this.feedbackItemsFull = new ArrayList<>(feedbackItems);
        notifyDataSetChanged();
    }
}
