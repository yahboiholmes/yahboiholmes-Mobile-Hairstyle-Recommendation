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

public class UserVarietyofHairstyleShortMen extends AppCompatActivity {
    ImageButton nFeedViewNav, nHomeNav, nAboutUsNav, nProfileNav;
    ImageView nBackShortMen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uservarietyofhairstylemenshort);

        nFeedViewNav = findViewById(R.id.feedback_view_button);
        nHomeNav = findViewById(R.id.home_button);
        nAboutUsNav = findViewById(R.id.about_us_button);
        nProfileNav = findViewById(R.id.profile_button);
        nBackShortMen = findViewById(R.id.backShortMen);

        GridView gridView = findViewById(R.id.imageGridShortMen);

        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.barbers);
        imageList.add(R.drawable.brushedbackcleancut);
        imageList.add(R.drawable.buzzcut);
        imageList.add(R.drawable.cleancutshortquiff);
        imageList.add(R.drawable.curledbangs);
        imageList.add(R.drawable.disconnectedundercut);
        imageList.add(R.drawable.easycomboverlook);
        imageList.add(R.drawable.fauxhawk);
        imageList.add(R.drawable.militarycut);
        imageList.add(R.drawable.moderncrewtrim);

        UserImageAdapterShortMen adapter = new UserImageAdapterShortMen(this, imageList);
        gridView.setAdapter(adapter);

        // Set item click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView = view.findViewById(R.id.gridImageViewShortMen);
                int selectedImageResourceId = (Integer) imageView.getTag();
                String selectedImageName = adapter.getImageName(selectedImageResourceId);
                String selectedImageDescription = adapter.getImageDescription(selectedImageResourceId);

                // Show a custom dialog with the format "Haircut\nImage Name\nDescription"
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

        nBackShortMen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserChoiceShortLongMale.class));
            }
        });
    }

    // Method to show a custom dialog with formatted title and description
    private void showDescriptionDialog(String title, String imageName, String imageDescription) {
        Dialog dialog = new Dialog(UserVarietyofHairstyleShortMen.this);
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
