package com.example.hairstylerecommendation.views.user;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hairstylerecommendation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class UserRegister extends AppCompatActivity {
    Button createAcc;
    EditText  nEmail, nPassword, nConfirmPass;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    public FirebaseUser firebaseUser;
    TextView nLogin;

    ImageView Togglebutton1, Togglebutton2;
    private static final String TAG= "UserRegister";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userregister);

        createAcc = findViewById(R.id.submitbutt);
        nEmail = findViewById(R.id.email1);
        nPassword = findViewById(R.id.password2);
        nConfirmPass = findViewById(R.id.password3);
        nLogin = findViewById(R.id.loginuser);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBarres);

        Togglebutton1 = findViewById(R.id.passwordVisibilityToggleReg);
        Togglebutton2 = findViewById(R.id.passwordVisibilityToggleReg2);

        Togglebutton1.setImageResource(R.drawable.passeyeclose);
        Togglebutton2.setImageResource(R.drawable.passeyeclose);

        Togglebutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    nPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    Togglebutton1.setImageResource(R.drawable.passeyeclose);
                }
                else {
                    nPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Togglebutton1.setImageResource(R.drawable.passeyeopen);
                }

            }
        });

        Togglebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nConfirmPass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    nConfirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    Togglebutton2.setImageResource(R.drawable.passeyeclose);
                }
                else {
                    nConfirmPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Togglebutton2.setImageResource(R.drawable.passeyeopen);
                }

            }
        });


        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = nEmail.getText().toString().trim();
                String password = nPassword.getText().toString().trim();
                String confirmpassword = nConfirmPass.getText().toString();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(UserRegister.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    nEmail.setError("Email is Required!");
                    nEmail.requestFocus();

                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(UserRegister.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    nEmail.setError("Valid Email is Required");
                    nEmail.requestFocus();
                }

                else if (TextUtils.isEmpty(password)){
                    Toast.makeText(UserRegister.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    nPassword.setError("Password is Required!");
                    return;
                }
                else if(password.length() < 6){
                    Toast.makeText(UserRegister.this, "Please should be at least 6 digits", Toast.LENGTH_SHORT).show();
                    nPassword.setError("Password too weak");
                    return;
                }
                else if (TextUtils.isEmpty(confirmpassword)){
                    Toast.makeText(UserRegister.this, "Please confirm your password", Toast.LENGTH_SHORT).show();
                    nConfirmPass.setError("Password Confirmation is Required");
                    nConfirmPass.requestFocus();
                }
                else if (!password.equals(confirmpassword)) {
                    Toast.makeText(UserRegister.this, "Please enter the same password", Toast.LENGTH_SHORT).show();
                    nConfirmPass.setError("Password does not match!");
                    nConfirmPass.requestFocus();

                    nPassword.clearComposingText();
                    nConfirmPass.clearComposingText();

                }
                progressBar.setVisibility(View.VISIBLE);
                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(UserRegister.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UserRegister.this, "Proceeding to Profiling", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserRegister.this, UserProfiling.class);
                                intent.putExtra("userEmail", email);
                                startActivity(intent);
                                finish();
                            } else {
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthUserCollisionException e) {
                                    nPassword.setError("User is already registered with this email. Use another!");
                                    nPassword.requestFocus();
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    nPassword.setError("Your email is invalid");
                                    nPassword.requestFocus();
                                } catch (Exception e) {
                                    Log.e(TAG, e.getMessage());
                                    Toast.makeText(UserRegister.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        }
                    });
                }



        });



        //for login
        nLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserRegister.this, UserLogin.class));
                finish();
            }
        });


    }
}
