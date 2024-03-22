package com.example.hairstylerecommendation.views.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hairstylerecommendation.R;
import com.example.hairstylerecommendation.appdata.AppData;
import com.example.hairstylerecommendation.model.UserProfiling;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserLogin extends AppCompatActivity {

    TextView nCreateBtn, nForgetPass, nLoginAd;
    EditText nEmail, nPassword;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    Button nLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlogin);

        nLoginBtn = findViewById(R.id.buttonlogin);
        nForgetPass = findViewById(R.id.forgotpassword);
        nCreateBtn = findViewById(R.id.account);
        nEmail = findViewById(R.id.email);
        nPassword = findViewById(R.id.password);

        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();

        // show password
        ImageView Togglebutton = findViewById(R.id.passwordVisibilityToggle);
        Togglebutton.setImageResource(R.drawable.passeyeclose);

        Togglebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    nPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Togglebutton.setImageResource(R.drawable.passeyeclose);
                } else {
                    nPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Togglebutton.setImageResource(R.drawable.passeyeopen);
                }
            }
        });

        nLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = nEmail.getText().toString().trim();
                String password = nPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    nEmail.setError("Email is Required!");
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    nPassword.setError("Password is Required!");
                    return;
                } else if (password.length() < 6) {
                    nPassword.setError("Password must be equal or more than 6 characters.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Inside the onClick method of nLoginBtn.setOnClickListener
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        System.out.println("fucking error");
                        if (task.isSuccessful()) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            if (user != null && user.isEmailVerified()) {
                                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User_Profiling").child(user.getUid());

                                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            com.example.hairstylerecommendation.model.UserProfiling userProfiling = new UserProfiling();
                                            userProfiling.setUserId(user.getUid());
                                            userProfiling.setAge(dataSnapshot.child("age").getValue(String.class));
                                            userProfiling.setDob(dataSnapshot.child("dob").getValue(String.class));
                                            userProfiling.setEmail(dataSnapshot.child("email").getValue(String.class));
                                            userProfiling.setFullName(dataSnapshot.child("fullname").getValue(String.class));
                                            userProfiling.setGender(dataSnapshot.child("gender").getValue(String.class));
                                            userProfiling.setPhoneNumber(dataSnapshot.child("phonenumber").getValue(String.class));
                                            //userProfiling.setProfilePicURI(dataSnapshot.child("profilePicUri").getValue(String.class));
                                            AppData.userInfo = userProfiling;

                                            //Toast.makeText(UserLogin.this, "Gender " + AppData.userInfo.getGender(), Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(UserLogin.this, UserDashboard.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(UserLogin.this, "User data does not exist", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(UserLogin.this, "Error fetching gender: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(UserLogin.this, "Please verify your email first.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(UserLogin.this, "Unable to complete. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });



        nForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLogin.this, UserForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });

        nCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserRegister.class));
                finish();
            }
        });

    }
}
