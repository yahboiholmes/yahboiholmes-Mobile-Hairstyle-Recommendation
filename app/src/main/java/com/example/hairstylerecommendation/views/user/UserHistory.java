// Create UserHistory.java

package com.example.hairstylerecommendation.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hairstylerecommendation.R;
import com.example.hairstylerecommendation.adapter.HistoryHaircutAdapter;
import com.example.hairstylerecommendation.adapter.RecommendHistoryAdapter;
import com.example.hairstylerecommendation.appdata.AppData;
import com.example.hairstylerecommendation.model.HistoryCut;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserHistory extends AppCompatActivity {
    ImageButton nFeedViewNav, nHomeNav, nAboutUsNav, nProfileNav;
    RecyclerView historyView;
    TextView noHistoryTxt;

    List<HistoryCut> historyCutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userhistory);

        historyCutList = new ArrayList<>();
        init();

        awaitHaircutHistoryQuery(new Callback() {
            @Override
            public void onSuccess() {
                if (historyCutList.size() != 0) {
                    noHistoryTxt.setVisibility(View.GONE);
                    historyView.setVisibility(View.VISIBLE);

                    historyView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    HistoryHaircutAdapter adapter = new HistoryHaircutAdapter(historyCutList);
                    historyView.setAdapter(adapter);
                } else {
                    noHistoryTxt.setText("No History");
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

        nFeedViewNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserFeedbackView.class));
            }
        });

        nHomeNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserDashboard.class));
            }
        });


        nAboutUsNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserAboutUS.class));
            }
        });

        nProfileNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserProfile.class));
            }
        });

    }

    private void init() {
        nFeedViewNav =findViewById(R.id.feedback_view_button);
        nHomeNav = findViewById(R.id.home_button);
        nAboutUsNav = findViewById(R.id.about_us_button);
        nProfileNav = findViewById(R.id.profile_button);
        historyView = findViewById(R.id.historyView);
        noHistoryTxt =findViewById(R.id.noHistoryTxt);
    }

    private void awaitHaircutHistoryQuery(final Callback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference haircutCollection = db.collection(AppData.userInfo.getGender().equals("Male") ? "mens_haircut_history" : "women_haircut_history");

        Query query = haircutCollection.whereEqualTo("user_id", AppData.userInfo.getUserId());

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    HistoryCut historyCut = new HistoryCut();
                    historyCut.setId(document.getId().toString());
                    historyCut.setUserChoices(document.getString("haircut_choices"));
                    historyCut.setFaceshape(document.getString("user_faceshape"));
                    historyCut.setName(document.getString("previous_haircut"));
                    historyCut.setHairSize(document.getString("user_hair_size"));
                    historyCut.setHairColor(document.getString("user_hair_color"));
                    historyCut.setHairType(document.getString("user_hair_type"));
                    historyCut.setDate(document.getString("date"));
                    historyCut.setUserId(document.getString("user_id"));
                    Object recommend = document.get("haircut_recommend");
                    historyCut.setListOfRecommend(convertObjectArrayToStringArray(recommend));

                    historyCutList.add(historyCut);
                }

                callback.onSuccess();
            } else {
                Exception exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                    callback.onFailure(exception);
                }
            }
        });
    }

    public interface Callback {
        void onSuccess();
        void onFailure(Exception e);
    }

    private static String[] convertObjectArrayToStringArray(Object objectArray) {
        ArrayList<?> arrayAsList;
        String[] stringArray= null;
        if (objectArray instanceof ArrayList<?>) {
            arrayAsList = (ArrayList<?>) objectArray;

            stringArray = arrayAsList.toArray(new String[0]);
        }
        return stringArray;
    }
}
