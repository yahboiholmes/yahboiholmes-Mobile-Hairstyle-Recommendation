package com.example.hairstylerecommendation.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hairstylerecommendation.R;

public class UserSplashScreen extends AppCompatActivity {
    View nContentView;
    Handler handler;
    ImageView logoImageView;
    TextView textTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersplashscreen);

        nContentView = findViewById(R.id.splashscreen);
        nContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        logoImageView = findViewById(R.id.splashscreen);
        textTextView = findViewById(R.id.hairstyle);

        // Logo animation
        Animation logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation); // an XML animation resource for the logo
        logoImageView.startAnimation(logoAnimation);

        // Text animation
        Animation textAnimation = AnimationUtils.loadAnimation(this, R.anim.text_animation); //  an XML animation resource for the text
        textTextView.startAnimation(textAnimation);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), UserAgreement.class));
                finish();
            }
        }, 3000);
    }
}
