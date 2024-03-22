package com.example.hairstylerecommendation.views.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.hairstylerecommendation.R;

public class UserChoiceShortMediumLongFemale extends AppCompatActivity {

    ImageView nBack;
    ImageView nShortHairfemale, nMediumHairfemale, nLongHairfemale;

    ImageButton nFeedViewNav, nHomeNav, nAboutUsNav, nProfileNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userchoiceshortmediumlongfemale);

            nBack = findViewById(R.id.backfemalehairtype);
            nShortHairfemale = findViewById(R.id.ShortHairFemale);
            nMediumHairfemale = findViewById(R.id.MediumHairFemale);
            nLongHairfemale = findViewById(R.id.LongHairFemale);

            nFeedViewNav =findViewById(R.id.feedback_view_button);
            nHomeNav = findViewById(R.id.home_button);
            nAboutUsNav = findViewById(R.id.about_us_button);
            nProfileNav = findViewById(R.id.profile_button);






        nShortHairfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserVarietyofHairstyleWomenShort.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });



        nMediumHairfemale.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), UserVarietyofHairstyleWomenMedium.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        nLongHairfemale.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), UserVarietyofHairstyleWomenLong.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        nBack.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), UserChoiceMaleFemale.class)));

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

    }
}