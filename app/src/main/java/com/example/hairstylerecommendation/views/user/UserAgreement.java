package com.example.hairstylerecommendation.views.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hairstylerecommendation.R;

public class UserAgreement extends AppCompatActivity {

    private CheckBox agreeCheckBox;
    private Button proceedButton;

    private TextView privacyTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.useragreement);

        agreeCheckBox = findViewById(R.id.agreeCheckBox);
        proceedButton = findViewById(R.id.proceedButton);
        privacyTextView = findViewById(R.id.privacyContent);

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (agreeCheckBox.isChecked()) {
                    // User has agreed to the terms
                    // Proceed to UserLogin activity
                    Intent intent = new Intent(UserAgreement.this, UserLogin.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(UserAgreement.this, "Directing to Login", Toast.LENGTH_SHORT).show();

                } else {
                    // User has not agreed to the terms, show a toast message
                    Toast.makeText(UserAgreement.this, "Please agree to the Terms of Service and Privacy Policy", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Set the text
        String fullText = "Your privacy is important to us. Please review our Privacy Policy. Data Privacy Act of 2012 for information on how we collect, use, and disclose your personal information.";

        // Create a clickable span
        String clickableText = "Data Privacy Act of 2012";
        int startIndex = fullText.indexOf(clickableText);
        int endIndex = startIndex + clickableText.length();

        SpannableString spannableString = new SpannableString(fullText);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Handle the click action (open a link)
                Uri uri = Uri.parse("https://privacy.gov.ph/data-privacy-act/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        };

        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Apply the spannable string to the TextView
        privacyTextView.setText(spannableString);
        privacyTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }


}

