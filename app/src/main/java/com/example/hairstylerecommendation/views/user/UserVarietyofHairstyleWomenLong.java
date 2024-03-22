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

public class UserVarietyofHairstyleWomenLong extends AppCompatActivity {
    ImageButton nFeedViewNav, nHomeNav, nAboutUsNav, nProfileNav;
    ImageView nBackLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uservarietyofhairstylewomenlong);

        nFeedViewNav = findViewById(R.id.feedback_view_button);
        nHomeNav = findViewById(R.id.home_button);
        nAboutUsNav = findViewById(R.id.about_us_button);
        nProfileNav = findViewById(R.id.profile_button);
        nBackLong = findViewById(R.id.backLong);

        GridView gridView = findViewById(R.id.imageGridViewLong);

        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.asteriahair);
        imageList.add(R.drawable.bigtopknot);
        imageList.add(R.drawable.doublebraidedponytail);
        imageList.add(R.drawable.halfcrownbraid);
        imageList.add(R.drawable.longandlayered);
        imageList.add(R.drawable.longandwavy);
        imageList.add(R.drawable.longcurlyhair);
        imageList.add(R.drawable.longhairwithbangs);
        imageList.add(R.drawable.lowmessyponytail);
        imageList.add(R.drawable.messyfishtail);

        UserImageAdapterLong adapter = new UserImageAdapterLong(this, imageList);
        gridView.setAdapter(adapter);

        // Set item click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView = view.findViewById(R.id.gridImageViewlong);
                int selectedImageResourceId = (Integer) imageView.getTag();
                String selectedImageName = adapter.getImageName(selectedImageResourceId);
                String selectedImageDescription = adapter.getImageDescription(selectedImageResourceId);

                // Show a custom dialog with the format "Hairstyle\nImage Name\nDescription"
                showDescriptionDialog("Hairstyle", selectedImageName, selectedImageDescription);
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

        nBackLong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserChoiceShortMediumLongFemale.class));
            }
        });
    }

    // Method to show a custom dialog with formatted title and description
    private void showDescriptionDialog(String title, String imageName, String imageDescription) {
        Dialog dialog = new Dialog(UserVarietyofHairstyleWomenLong.this);
        dialog.setContentView(R.layout.custom_image_description_dialog);

        // Set the title and description text
        TextView titleTextView = dialog.findViewById(R.id.titleTextView);
        TextView descriptionTextView = dialog.findViewById(R.id.descriptionTextView);

        // Display the formatted title and description
        String formattedTitle = title + "\n" + imageName + "\n" + imageDescription;
        titleTextView.setText(formattedTitle);

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
