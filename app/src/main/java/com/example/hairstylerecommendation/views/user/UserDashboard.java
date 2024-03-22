package com.example.hairstylerecommendation.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hairstylerecommendation.R;
import com.example.hairstylerecommendation.appdata.AppData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserDashboard extends AppCompatActivity {
    //dashboard image
    ImageButton nHaircut, nHistory, nFeedback, nVariety;

    TextView welcome;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    //navigation image
    ImageButton nFeedViewNav, nHomeNav, nAboutUsNav, nProfileNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdashboard);

        //call image id dashboard
        nHaircut = findViewById(R.id.haircutdash);
        nHistory = findViewById(R.id.historydash);
        nFeedback = findViewById(R.id.feedbackdash);
        nVariety = findViewById(R.id.varietyofhaircutsdashs);

        // call navigation id dashboard
        nFeedViewNav=findViewById(R.id.feedback_view_button);
        nHomeNav = findViewById(R.id.home_button);
        nAboutUsNav = findViewById(R.id.about_us_button);
        nProfileNav = findViewById(R.id.profile_button);

        //welcome message
        welcome = findViewById(R.id.welcomeTextView);
        String[] fullNameParts = AppData.userInfo.getFullName().split(" ");
        if (fullNameParts != null) {
            welcome.setText("Welcome!!" + " " + fullNameParts[0]);
        }

        nFeedViewNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserFeedbackView.class));
            }
        });

        nHomeNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserDashboard.class));
            }
        });


        nAboutUsNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserAboutUS.class));
            }
        });

        nProfileNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserProfile.class));
            }
        });



        nHaircut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboard.this, UserPreferences.class);
                startActivity(intent);
            }
        });


        nHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserHistory.class));
            }
        });

        nFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserFeedback.class));
            }
        });

        nVariety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserChoiceMaleFemale.class));
            }
        });
    }

}

