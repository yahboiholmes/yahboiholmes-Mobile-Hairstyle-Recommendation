package com.example.hairstylerecommendation.views.user;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hairstylerecommendation.R;
import com.example.hairstylerecommendation.adapter.RecommendHaircutAdapter;
import com.example.hairstylerecommendation.appdata.AppData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UserRecommend extends AppCompatActivity {

    ImageView userPictureImg;
    TextView faceShapeTxt;
    RecyclerView recommendView;
    Button saveBtn, tryAgainBtn;

    private int selectedHairCut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userrecommend);

        init();

        if (AppData.userPicture != null) {
            userPictureImg.setImageBitmap(AppData.userPicture);
            faceShapeTxt.setText(AppData.userFaceShape);
        }

        recommendView.setLayoutManager(new LinearLayoutManager(this));

        RecommendHaircutAdapter adapter = new RecommendHaircutAdapter(AppData.haircutList, this::onItemClick);
        recommendView.setAdapter(adapter);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference collectionRef = db.collection(AppData.userInfo.getGender().equals("Male") ? "mens_haircut_history" : "women_haircut_history");

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String todayDate = dateFormat.format(new Date());
                List<Object> hairCutArray = new ArrayList<>();
                for (int i = 0; i < AppData.recommendHaircutList.size(); i++) {
                    hairCutArray.add(AppData.recommendHaircutList.get(i).getName());
                }

                Map<String, Object> data = new HashMap<>();
                data.put("previous_haircut", AppData.hairCutName);
                data.put("user_faceshape", AppData.userFaceShape);
                data.put("user_hair_type", AppData.hairType);
                data.put("user_hair_size", AppData.hairSize);
                data.put("user_hair_color", AppData.hairColor);
                data.put("haircut_recommend", hairCutArray);
                data.put("haircut_choices", AppData.recommendHaircutList.get(selectedHairCut).getName());
                data.put("user_id", AppData.userInfo.getUserId());
                data.put("date", todayDate);

                collectionRef.add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                DocumentReference docRef = db.collection(AppData.userInfo.getGender().equals("Male") ? "mens_haircut" : "women_haircut").document(AppData.recommendHaircutList.get(selectedHairCut).getId());

                                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if (documentSnapshot.exists()) {
                                            Map<String, Object> updates = new HashMap<>();
                                            updates.put("user_choices", documentSnapshot.getLong("user_choices") + 1);

                                            docRef.update(updates)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(getApplicationContext(), "successfully saved!", Toast.LENGTH_SHORT).show();

                                                            startActivity(new Intent(UserRecommend.this, UserHistory.class));
                                                            finish();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            System.out.println( "Error updating document" + e.getMessage());
                                                        }
                                                    });
                                        }
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle errors
                                System.out.println( "Error adding history" + e.getMessage());
                            }
                        });
            }
        });

        tryAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppData.resetData();
                Toast.makeText(UserRecommend.this, "userFaceShape " + AppData.userFaceShape, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserRecommend.this, UserPreferences.class));
                finish();
            }
        });
    }

    private void init() {
        userPictureImg = findViewById(R.id.userPictureImg);
        faceShapeTxt = findViewById(R.id.faceShapeTxt);
        recommendView = findViewById(R.id.recommendView);
        saveBtn = findViewById(R.id.saveBtn);
        tryAgainBtn = findViewById(R.id.tryAgainBtn);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserRecommend.this, UserCapture.class));
        finish();
    }

    public void onItemClick(int position) {
        selectedHairCut = position;

        //dito simula ng changes ko
        //Toast.makeText(this, "Card clicked at position: " + AppData.haircutList.get(selectedHairCut).getName(), Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(UserRecommend.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialoghaircutinfo, viewGroup, false);
        builder.setCancelable(false);
        builder.setView(dialogView);

        TextView descriptionTxt = dialogView.findViewById(R.id.descriptionTxt);
        TextView haircutNameTxt = dialogView.findViewById(R.id.haircutNameTxt);
        Button closeBtn = dialogView.findViewById(R.id.closeBtn);

        haircutNameTxt.setText(AppData.haircutList.get(selectedHairCut).getName());
        descriptionTxt.setText(AppData.haircutList.get(selectedHairCut).getDescription());

        AlertDialog alertDialog = builder.create();
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.hide();
            }
        });
        alertDialog.show();
        // hanggang dito
    }
}
