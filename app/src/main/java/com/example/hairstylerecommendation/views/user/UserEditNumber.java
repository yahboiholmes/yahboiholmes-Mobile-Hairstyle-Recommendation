package com.example.hairstylerecommendation.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hairstylerecommendation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserEditNumber extends AppCompatActivity {

    private static final String USER_PROFILING_DB = "User_Profiling";
    private static final String PHONE_NUMBER_KEY = "phonenumber";

    private TextView textPhoneOld;
    private EditText textPhoneNew;
    private Button saveButton;

    private ImageView nBackEdit;

    private ImageButton nFeedViewNav, nHomeNav, nAboutUsNav, nProfileNav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usereditnumber);

        textPhoneOld = findViewById(R.id.TextPhoneOld);
        textPhoneNew = findViewById(R.id.textPhonenew);
        saveButton = findViewById(R.id.savePhone);
        nBackEdit = findViewById(R.id.backeditphone);

        nFeedViewNav = findViewById(R.id.feedback_view_button);
        nHomeNav = findViewById(R.id.home_button);
        nAboutUsNav = findViewById(R.id.about_us_button);
        nProfileNav = findViewById(R.id.profile_button);

        // Fetch old number from the database
        fetchOldNumberFromDatabase();

        // Set a click listener for the save button
        // Inside the saveButton click listener
        saveButton.setOnClickListener(view -> {
            // Get the new number from the EditText
            String newNumber = textPhoneNew.getText().toString();

            // Validate the new number
            if (validatePhoneNumber(newNumber)) {
                // Save the new number to Firebase
                saveNewNumberToFirebase(newNumber);

                // Update UI with the new number
                textPhoneOld.setText(newNumber);

                // Redirect to UserProfile.java
                Intent userProfileIntent = new Intent(getApplicationContext(), UserProfile.class);
                userProfileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(userProfileIntent);
                finish(); // Finish the current activity
            }
        });

        nFeedViewNav.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), UserFeedbackView.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        nHomeNav.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), UserDashboard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        nAboutUsNav.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), UserAboutUS.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        nProfileNav.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), UserProfile.class)));

        nBackEdit.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), UserProfile.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void fetchOldNumberFromDatabase() {
        // Get the current authenticated user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String userId = user.getUid();

            // Initialize Firebase Database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userReference = database.getReference(USER_PROFILING_DB);

            // Query the database to fetch the old phone number
            userReference.child(userId).child(PHONE_NUMBER_KEY).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String oldNumber = dataSnapshot.getValue(String.class);

                        // Set the old number to the TextView
                        textPhoneOld.setText(oldNumber);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors here
                    showToast("Error fetching old number");
                }
            });
        } else {
            // User is not authenticated, handle accordingly
        }
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            showToast("Please enter a phone number.");
            return false;
        }

        if (phoneNumber.length() > 11) {
            showToast("Phone number exceeds 11 digits.");
            return false;
        }

        if (!phoneNumber.startsWith("09")) {
            showToast("Phone number must start with 09.");
            return false;
        }

        return true;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void saveNewNumberToFirebase(String newNumber) {
        // Get the current authenticated user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String userId = user.getUid();

            // Initialize Firebase Database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userReference = database.getReference(USER_PROFILING_DB);

            // Update the new phone number in the database
            userReference.child(userId).child(PHONE_NUMBER_KEY).setValue(newNumber);
        } else {
            // User is not authenticated
            showToast("User not authenticated. Please log in.");

            // Or redirect to the authentication screen (assuming LoginActivity is your authentication screen)
            Intent loginIntent = new Intent(this, UserLogin.class);
            startActivity(loginIntent);
            finish();
        }
    }
}
