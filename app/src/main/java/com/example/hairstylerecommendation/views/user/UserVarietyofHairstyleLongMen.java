package com.example.hairstylerecommendation.views.user;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hairstylerecommendation.R;

import java.util.ArrayList;
import java.util.List;

public class UserVarietyofHairstyleLongMen extends AppCompatActivity {
    ImageButton nFeedViewNav, nHomeNav, nAboutUsNav, nProfileNav;
    ImageView nBackLongMen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uservarietyofhairstylemenlong);

        nFeedViewNav = findViewById(R.id.feedback_view_button);
        nHomeNav = findViewById(R.id.home_button);
        nAboutUsNav = findViewById(R.id.about_us_button);
        nProfileNav = findViewById(R.id.profile_button);
        nBackLongMen = findViewById(R.id.backLongMen);

        GridView gridView = findViewById(R.id.imageGridViewLongMen);

        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.bluntfringe);
        imageList.add(R.drawable.dyedhair);
        imageList.add(R.drawable.halflong);
        imageList.add(R.drawable.halfupmanbun);
        imageList.add(R.drawable.halfupmanponytail);
        imageList.add(R.drawable.highmanponytail);
        imageList.add(R.drawable.layeredlonghair);
        imageList.add(R.drawable.longandstraight);
        imageList.add(R.drawable.longcurlyhairwithundercut);
        imageList.add(R.drawable.longhair);

        UserImageAdapterLongMen adapter = new UserImageAdapterLongMen(UserVarietyofHairstyleLongMen.this, imageList);
        gridView.setAdapter(adapter);

        // Set item click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView = view.findViewById(R.id.gridImageViewLongMen);
                int selectedImageResourceId = (Integer) imageView.getTag();
                String selectedImageName = adapter.getImageName(selectedImageResourceId);
                String selectedImageDescription = adapter.getImageDescription(selectedImageResourceId);

                // Show a custom dialog with the title ("HairStyle") and image description
                showDescriptionDialog("HairStyle", selectedImageName, selectedImageDescription);
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

        nBackLongMen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserChoiceShortLongMale.class));
            }
        });
    }

    // Method to show a custom dialog with title and description
    private void showDescriptionDialog(String title, String name, String description) {
        Dialog dialog = new Dialog(UserVarietyofHairstyleLongMen.this);
        dialog.setContentView(R.layout.custom_image_description_dialog);

        // Set the title and description text
        TextView titleTextView = dialog.findViewById(R.id.titleTextView);
        TextView descriptionTextView = dialog.findViewById(R.id.descriptionTextView);

        // Display the title ("Haircut") above the image description
        String fullDescription = title + "\n\n" + name + "\n\n" + description;
        titleTextView.setText(fullDescription);

        // Close the dialog when the OK button is clicked
        ImageButton okButton = dialog.findViewById(R.id.okButton);
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
