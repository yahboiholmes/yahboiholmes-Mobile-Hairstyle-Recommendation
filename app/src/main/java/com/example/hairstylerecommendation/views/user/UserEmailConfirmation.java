package com.example.hairstylerecommendation.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hairstylerecommendation.R;

public class UserEmailConfirmation extends AppCompatActivity {

    private Button loginVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.useremailconfirmation);

        loginVerify = findViewById(R.id.buttonlogins);


        loginVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserLogin.class));
                Toast.makeText(UserEmailConfirmation.this, "Directing to Login", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
