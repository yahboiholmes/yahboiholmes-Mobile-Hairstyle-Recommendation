package com.example.hairstylerecommendation.views.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hairstylerecommendation.R;

public class UserProfile extends AppCompatActivity {
    private static final int YOUR_IMAGE_REQUEST_CODE = 100;
    private Uri selectedImageUri;

    ImageButton nFeedViewNav, nHomeNav, nAboutUsNav, nProfileNav;
    private Button nLogout;
    private TextView fullNameTextView, UAge, UGender, UDob, UPhoneNumber, Uemail, resetPassTextView, EditNumb;
    private FirebaseAuth firebaseAuth;
    private SwipeRefreshLayout swipeContainer;
    private ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofile);
        firebaseAuth = FirebaseAuth.getInstance();
        updateUI();
        swipetoRefresh();

        nFeedViewNav = findViewById(R.id.feedback_view_button);
        nHomeNav = findViewById(R.id.home_button);
        nAboutUsNav = findViewById(R.id.about_us_button);
        nProfileNav = findViewById(R.id.profile_button);

        fullNameTextView = findViewById(R.id.textviewFullname);
        Uemail = findViewById(R.id.TextEmail);
        UPhoneNumber = findViewById(R.id.TextPhone);
        UAge = findViewById(R.id.textviewAge);
        UGender = findViewById(R.id.textviewGender);
        UDob = findViewById(R.id.textviewBirthdate);
        nLogout = findViewById(R.id.Logout);
        profilePic = findViewById(R.id.profilepic);
        resetPassTextView = findViewById(R.id.resetpass);
        EditNumb = findViewById(R.id.editNumb);

        EditNumb.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), UserEditNumber.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        nLogout.setOnClickListener(view -> {
            Intent intent = new Intent(UserProfile.this, UserLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        nFeedViewNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserFeedbackView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        nHomeNav.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), UserDashboard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        nAboutUsNav.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), UserAboutUS.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        nProfileNav.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), UserProfile.class)));

        resetPassTextView.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), UserResetPassword.class)));

        profilePic.setOnClickListener(view -> openGallery());

        // Call loadProfilePicture after user registration or wherever it's appropriate
        // For example:
        // if (isNewUser) {
        //     loadProfilePicture(uid);
        // }
    }

    private void swipetoRefresh() {
        swipeContainer = findViewById(R.id.swipecontainer);
        swipeContainer.setOnRefreshListener(() -> {
            updateUI();
            overridePendingTransition(0, 0);
            swipeContainer.setRefreshing(false);
        });

        swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();

            DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("User_Profiling").child(uid);
            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        ReadWriteUserDetails userDetails = dataSnapshot.getValue(ReadWriteUserDetails.class);
                        fullNameTextView.setText(userDetails.getFullname());
                        Uemail.setText(userDetails.getEmail());
                        UAge.setText(userDetails.getAge());
                        UPhoneNumber.setText(userDetails.getPhonenumber());
                        UDob.setText(userDetails.getDob());
                        UGender.setText(userDetails.getGender());
                        loadProfilePicture(uid);
                    } else {
                        Toast.makeText(UserProfile.this, "User details not found", Toast.LENGTH_SHORT).show();
                    }
                }

                private void loadProfilePicture(String uid) {
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile_pics/" + uid + "/profile_pic.jpg");
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> setProfilePicture(uri))
                            .addOnFailureListener(e -> {
                                setProfilePicture(null);

                                DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("User_Profiling").child(uid);
                                userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            ReadWriteUserDetails userDetails = dataSnapshot.getValue(ReadWriteUserDetails.class);
                                            if (userDetails != null && userDetails.getProfilePicUri() == null) {
                                                Toast.makeText(UserProfile.this, "Welcome! Please upload a profile picture.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(UserProfile.this, "Something is Wrong! ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            });
                }

                private void setProfilePicture(Uri uri) {
                    if (uri != null) {
                        Glide.with(UserProfile.this)
                                .load(uri)
                                .centerCrop()
                                .placeholder(R.drawable.profileframeholder)
                                .into(profilePic);
                    } else {
                        Glide.with(UserProfile.this)
                                .load(R.drawable.profileframeholder)
                                .centerCrop()
                                .into(profilePic);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(UserProfile.this, "Failed to retrieve user details", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(UserProfile.this, "User not signed in", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, YOUR_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == YOUR_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            profilePic.setImageURI(selectedImageUri);
            saveImageUriToDatabase(selectedImageUri);
        } else {
            if (resultCode != RESULT_OK) {
                Toast.makeText(UserProfile.this, "Image selection canceled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(UserProfile.this, "Failed to select/upload image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveImageUriToDatabase(Uri imageUri) {
        FirebaseUser users = firebaseAuth.getCurrentUser();
        if (users != null) {
            String uid = users.getUid();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile_pics/" + uid + "/profile_pic.jpg");

            storageReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("User_Profiling").child(uid);
                        userReference.child("profilePicUri").setValue(uri.toString());
                        Toast.makeText(UserProfile.this, "Profile picture updated successfully", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(e -> Toast.makeText(UserProfile.this, "Failed to get download URL", Toast.LENGTH_SHORT).show()))
                    .addOnFailureListener(e -> {
                        Toast.makeText(UserProfile.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        Log.e("UserProfile", "Failed to upload image", e);
                    });
        }
    }
}
