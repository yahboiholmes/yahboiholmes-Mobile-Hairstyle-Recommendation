package com.example.hairstylerecommendation.views.user;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hairstylerecommendation.R;

import java.util.ArrayList;
import java.util.List;

public class UserVarietyofHairstyleWomenMedium extends AppCompatActivity {
    ImageButton nFeedViewNav, nHomeNav, nAboutUsNav, nProfileNav;
    ImageView nBackMedium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uservarietyofhairstylewomenmedium);

        nFeedViewNav = findViewById(R.id.feedback_view_button);
        nHomeNav = findViewById(R.id.home_button);
        nAboutUsNav = findViewById(R.id.about_us_button);
        nProfileNav = findViewById(R.id.profile_button);
        nBackMedium = findViewById(R.id.backMedium);

        GridView gridView = findViewById(R.id.imageGridViewMedium);

        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.flipmediumbob);
        imageList.add(R.drawable.blowoutbob);
        imageList.add(R.drawable.bluntmediumbob);
        imageList.add(R.drawable.braidedbob);
        imageList.add(R.drawable.dyedmediumbob);
        imageList.add(R.drawable.messylayeredbob);
        imageList.add(R.drawable.beachwaveswithbangs);
        imageList.add(R.drawable.sidesweep);
        imageList.add(R.drawable.sleekandshiny);
        imageList.add(R.drawable.topknot);

        UserImageAdapterMedium adapter = new UserImageAdapterMedium(UserVarietyofHairstyleWomenMedium.this, imageList);
        gridView.setAdapter(adapter);

        // Set item click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView = view.findViewById(R.id.gridImageViewMedium);
                TextView textView = view.findViewById(R.id.gridTextViewMedium);

                int selectedImageResourceId = (Integer) imageView.getTag();
                String selectedImageName = textView.getText().toString();
                String selectedImageDescription = adapter.getImageDescription(selectedImageResourceId);

                // Show a custom dialog with the title ("Haircut"), image name, and description
                showDescriptionDialog("Haircut", selectedImageName, selectedImageDescription);
            }
        });

        nFeedViewNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserFeedbackView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
                startActivity(new Intent(getApplicationContext(), UserProfile.class));
            }
        });

        nBackMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserChoiceShortMediumLongFemale.class));
            }
        });
    }

    // Method to show a custom dialog with title, image name, and description
    private void showDescriptionDialog(String title, String imageName, String imageDescription) {
        Dialog dialog = new Dialog(UserVarietyofHairstyleWomenMedium.this);
        dialog.setContentView(R.layout.custom_image_description_dialog);

        // Set the title and description text
        TextView titleTextView = dialog.findViewById(R.id.titleTextView);
        TextView descriptionTextView = dialog.findViewById(R.id.descriptionTextView);

        // Display the formatted title, image name, and description
        String formattedText = title + "\n" + imageName + "\n" + imageDescription;
        titleTextView.setText(formattedText);

        // Close the dialog when the OK button is clicked
        Button okButton = dialog.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Show the dialog
        dialog.show();
    }
}
