package com.example.hairstylerecommendation.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hairstylerecommendation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserFeedbackView extends AppCompatActivity {

    private EditText editTextSearch;
    private RecyclerView recyclerViewFeedback;
    private DatabaseReference feedbackRef;

    private ImageButton nFeedViewNav, nHomeNav, nAboutUsNav, nProfileNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userfeedbackview);

        nFeedViewNav = findViewById(R.id.feedback_view_button);
        nHomeNav = findViewById(R.id.home_button);
        nAboutUsNav = findViewById(R.id.about_us_button);
        nProfileNav = findViewById(R.id.profile_button);

        editTextSearch = findViewById(R.id.editTextSearchs);
        recyclerViewFeedback = findViewById(R.id.recyclerViewFeedbacks);

        // Setup RecyclerView
        FeedbackAdapter adapter = new FeedbackAdapter(new ArrayList<>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewFeedback.setAdapter(adapter);
        recyclerViewFeedback.setLayoutManager(layoutManager);

        // Initialize Firebase Database reference
        feedbackRef = FirebaseDatabase.getInstance().getReference("Feedback User");

        // Set a TextChangedListener for searching
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Not used in this example
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Filter the feedback items based on the entered text
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not used in this example
            }
        });

        // Fetch feedback data from Firebase
        fetchFeedbackData();

        // Set up navigation onClickListeners
        setNavigationClickListeners();
    }

    private void setNavigationClickListeners() {
        nFeedViewNav.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), UserFeedbackView.class)));

        nHomeNav.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), UserDashboard.class)));

        nAboutUsNav.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), UserAboutUS.class)));

        nProfileNav.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), UserProfile.class)));
    }

    private void fetchFeedbackData() {
        feedbackRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<FeedbackItem> feedbackItems = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FeedbackItem feedbackItem = snapshot.getValue(FeedbackItem.class);
                    if (feedbackItem != null) {
                        feedbackItems.add(feedbackItem);
                    }
                }

                // Update the RecyclerView with the fetched data
                FeedbackAdapter adapter = (FeedbackAdapter) recyclerViewFeedback.getAdapter();
                if (adapter != null) {
                    adapter.setFeedbackItems(feedbackItems);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserFeedbackView.this, "Failed to fetch feedback data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
