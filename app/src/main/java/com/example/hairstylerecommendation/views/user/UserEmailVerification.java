package com.example.hairstylerecommendation.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hairstylerecommendation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserEmailVerification extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailInput;

    private Button verifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.useremailverification);

        mAuth = FirebaseAuth.getInstance();
        emailInput = findViewById(R.id.emailinput);
        verifyButton = findViewById(R.id.verifybuttons);

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString().trim();

                if (!email.isEmpty()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        if (user.getEmail().equals(email)) {
                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(UserEmailVerification.this, "Verification email sent", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(UserEmailVerification.this, UserEmailConfirmation.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(UserEmailVerification.this, "Failed to send verification email", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(UserEmailVerification.this, "Email does not match", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(UserEmailVerification.this, "Please enter an email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
