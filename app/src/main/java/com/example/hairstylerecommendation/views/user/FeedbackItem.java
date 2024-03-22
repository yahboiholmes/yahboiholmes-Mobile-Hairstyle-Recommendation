package com.example.hairstylerecommendation.views.user;

import com.google.firebase.database.FirebaseDatabase;

public class FeedbackItem {
    private String userId;
    private String text;
    private int rating;
    private String fullName;

    public FeedbackItem() {
        // Default constructor required for Firebase
    }

    public FeedbackItem(String text, int rating, String fullName) {
        this.userId = generateFeedbackId(); // Use .getKey() to generate a unique userId
        this.text = text;
        this.rating = rating;
        this.fullName = fullName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    private String generateFeedbackId() {
        return FirebaseDatabase.getInstance().getReference().child("Feedback User").push().getKey();
    }
}
