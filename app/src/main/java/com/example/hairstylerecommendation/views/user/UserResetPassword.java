package com.example.hairstylerecommendation.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hairstylerecommendation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserResetPassword extends AppCompatActivity {

    private EditText currentPasswordEditText;
    private EditText newPasswordEditText;
    private FirebaseAuth firebaseAuth;

    ImageView Current, Newrest, backButton;

    ImageButton nFeedViewNav, nHomeNav, nAboutUsNav, nProfileNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userresetpassword);

        // Assuming you have EditText fields for current and new passwords in your XML layout
        currentPasswordEditText = findViewById(R.id.currentpass);
        newPasswordEditText = findViewById(R.id.newpass);
        nFeedViewNav = findViewById(R.id.feedback_view_button);
        nHomeNav = findViewById(R.id.home_button);
        nAboutUsNav = findViewById(R.id.about_us_button);
        nProfileNav = findViewById(R.id.profile_button);

        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();

        // reset icon button
        Current = findViewById(R.id.passwordVisibilityToggleres1);
        Newrest = findViewById(R.id.passwordVisibilityToggleres2);
        backButton = findViewById(R.id.backbutton);

        Current.setImageResource(R.drawable.passeyeclose);
        Newrest.setImageResource(R.drawable.passeyeclose);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserProfile.class));
                finish();
            }
        });

        Current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPasswordEditText.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    currentPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Current.setImageResource(R.drawable.passeyeclose);
                } else {
                    currentPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Current.setImageResource(R.drawable.passeyeopen);
                }
            }
        });

        Newrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newPasswordEditText.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    newPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Newrest.setImageResource(R.drawable.passeyeclose);
                } else {
                    newPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Newrest.setImageResource(R.drawable.passeyeopen);
                }
            }
        });

        // Navigation Buttons
        nFeedViewNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserResetPassword.this, UserFeedbackView.class));
                finish();
            }
        });

        nHomeNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserDashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        nAboutUsNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserAboutUS.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        nProfileNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserProfile.class));
            }
        });
    }

    // Method called when the "Save Changes" button is clicked
    public void onSaveChangesClicked(View view) {
        String currentPassword = currentPasswordEditText.getText().toString();
        String newPassword = newPasswordEditText.getText().toString();

        // Get the current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            // Reauthenticate the user before updating the password
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Reauthentication successful, now update the user's password
                                updatePassword(user, newPassword);
                            } else {
                                // If reauthentication fails, handle the error
                                Toast.makeText(UserResetPassword.this, "Reauthentication failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void updatePassword(FirebaseUser user, String newPassword) {
        // Update the user's password
        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Password updated successfully
                            Toast.makeText(UserResetPassword.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                            // Logout the user
                            logoutUser();
                        } else {
                            // If updating the password fails, handle the error
                            Toast.makeText(UserResetPassword.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void logoutUser() {
        firebaseAuth.signOut();
        // Redirect to UserLogin.java
        startActivity(new Intent(UserResetPassword.this, UserLogin.class));
        finish(); // Close the current activity to prevent going back to it with the back button
    }
}
