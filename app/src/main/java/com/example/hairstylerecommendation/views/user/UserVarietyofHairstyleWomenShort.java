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

public class UserVarietyofHairstyleWomenShort extends AppCompatActivity {
    ImageButton nFeedViewNav, nHomeNav, nAboutUsNav, nProfileNav;
    ImageView nBackShort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uservarietyofhairstylewomenshort);

        nFeedViewNav = findViewById(R.id.feedback_view_button);
        nHomeNav = findViewById(R.id.home_button);
        nAboutUsNav = findViewById(R.id.about_us_button);
        nProfileNav = findViewById(R.id.profile_button);
        nBackShort = findViewById(R.id.backShort);

        GridView gridView = findViewById(R.id.imageGridView);

        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.pixieundercut);
        imageList.add(R.drawable.pixiefringe);
        imageList.add(R.drawable.pixiesidefringe);
        imageList.add(R.drawable.pixiewithquiff);
        imageList.add(R.drawable.sleekpixiecut);
        imageList.add(R.drawable.classicpixie);
        imageList.add(R.drawable.sidepartedpixiecut);
        imageList.add(R.drawable.bowlcutwihtbluntbangs);
        imageList.add(R.drawable.shortstraightbob);
        imageList.add(R.drawable.wispycurtain);

        UserImageAdapterShort adapter = new UserImageAdapterShort(this, imageList);
        gridView.setAdapter(adapter);

        // Set item click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView = view.findViewById(R.id.gridImageView);
                int selectedImageResourceId = (Integer) imageView.getTag();
                String selectedImageName = adapter.getImageName(selectedImageResourceId);
                String selectedImageDescription = adapter.getImageDescription(selectedImageResourceId);

                // Show a custom dialog with the format "Hairstyle\nImage Name\nDescription"
                showDescriptionDialog("HairCut", selectedImageName, selectedImageDescription);
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

        nBackShort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserChoiceShortMediumLongFemale.class));
            }
        });
    }

    // Method to show a custom dialog with formatted title and description
    private void showDescriptionDialog(String title, String imageName, String imageDescription) {
        Dialog dialog = new Dialog(UserVarietyofHairstyleWomenShort.this);
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
