package com.example.hairstylerecommendation.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hairstylerecommendation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserFeedback extends AppCompatActivity {

    private ImageView emojiImageView, nBackfeed;
    private RatingBar ratingBar;
    private EditText editTextComments;
    private Button submitButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userfeedback);

        emojiImageView = findViewById(R.id.emojiImageView);
        ratingBar = findViewById(R.id.ratingBar);
        editTextComments = findViewById(R.id.editTextTextMultiLine);
        submitButton = findViewById(R.id.subFeed);
        nBackfeed = findViewById(R.id.backFeed);

        mAuth = FirebaseAuth.getInstance();

        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> updateEmojiImage(rating));

        submitButton.setOnClickListener(v -> saveFeedbackToDatabase());

        nBackfeed.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), UserDashboard.class);
            startActivity(intent);
            finish(); // Optional: Finish the current activity to prevent going back to it from UserDashboard
        });
    }

    private void updateEmojiImage(float rating) {
        int roundedRating = Math.round(rating);
        int emojiResource = R.drawable.sadder; // Default
        switch (roundedRating) {
            case 5:
                emojiResource = R.drawable.excellent;
                break;
            case 4:
                emojiResource = R.drawable.happy;
                break;
            case 3:
                emojiResource = R.drawable.steady;
                break;
            case 2:
                emojiResource = R.drawable.sad;
                break;
        }
        emojiImageView.setImageResource(emojiResource);
    }

    private void saveFeedbackToDatabase() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            String feedbackText = editTextComments.getText().toString().trim();
            int rating = Math.round(ratingBar.getRating());

            DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("User_Profiling").child(userId);
            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String fullName = snapshot.child("fullname").getValue(String.class);

                        DatabaseReference feedbackRef = FirebaseDatabase.getInstance().getReference("Feedback User");
                        String feedbackId = feedbackRef.push().getKey();

                        feedbackRef.child(feedbackId).child("userId").setValue(userId);
                        feedbackRef.child(feedbackId).child("text").setValue(feedbackText);
                        feedbackRef.child(feedbackId).child("rating").setValue(rating);
                        feedbackRef.child(feedbackId).child("fullName").setValue(fullName);

                        Toast.makeText(UserFeedback.this, "Feedback submitted successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), UserDashboard.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(UserFeedback.this, "User profile not found", Toast.LENGTH_SHORT).show();
                        Log.e("UserProfile", "User profile not found");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(UserFeedback.this, "Error fetching user profile", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }
}
