package com.example.hairstylerecommendation.views.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hairstylerecommendation.R;
import com.example.hairstylerecommendation.appdata.AppData;
import com.example.hairstylerecommendation.ml.Model;
import com.example.hairstylerecommendation.model.Haircut;
import com.example.hairstylerecommendation.model.HaircutTree;
import com.example.hairstylerecommendation.model.HistoryCut;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserCapture extends AppCompatActivity {

    ImageView userPictureImg;
    Button captureBtn, proceedBtn;

    private static final int Image_Size = 32;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usercapture);

        //Initialize variable
        init();

        if (AppData.userPicture != null) {
            userPictureImg.setImageBitmap(AppData.userPicture);
            captureBtn.setText("Re-take");
            proceedBtn.setEnabled(true);
            proceedBtn.setText("Proceed");
        }

        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 3);
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppData.userPicture != null && !AppData.userFaceShape.isEmpty()) {
                    proceedBtn.setEnabled(false);
                    proceedBtn.setText("Please wait...");

                    AppData.haircutList = new ArrayList<>();
                    AppData.historyCutList = new ArrayList<>();

                    awaitHaircutQuery(new Callback() {
                        @Override
                        public void onSuccess() {
                            awaitHaircutHistoryQuery(new Callback() {
                                @Override
                                public void onSuccess() {
                                    awaitRecommendHaircut(new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            Intent intent = new Intent(UserCapture.this, UserRecommend.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                        @Override
                                        public void onFailure(Exception e) {
                                            e.printStackTrace();
                                            Toast.makeText(getApplicationContext(), "awaitHaircutOrder error", Toast.LENGTH_SHORT).show();
                                            proceedBtn.setEnabled(true);
                                            proceedBtn.setText("Proceed");
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "awaitHaircutHistoryQuery error", Toast.LENGTH_SHORT).show();
                                    proceedBtn.setEnabled(true);
                                    proceedBtn.setText("Proceed");
                                }
                            });
                        }

                        @Override
                        public void onFailure(Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "awaitHaircutQuery error", Toast.LENGTH_SHORT).show();
                            proceedBtn.setEnabled(true);
                            proceedBtn.setText("Proceed");
                        }
                    });
                }
            }
        });
    }


    private void init() {
        userPictureImg = findViewById(R.id.userPictureImg);
        captureBtn = findViewById(R.id.captureBtn);
        proceedBtn = findViewById(R.id.proceedBtn);
    }

    public void classifyImage(Bitmap image){
        try {
            Model model = Model.newInstance(getApplicationContext());

            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 32, 32, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * Image_Size * Image_Size * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[Image_Size * Image_Size];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;

            for(int i = 0; i < Image_Size; i ++){
                for(int j = 0; j < Image_Size; j++){
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 1));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }

            //Wag mo comment ito
            String[] classes = {"Oval", "Round", "Square", "Diamond", "Oblong", "Heart"};
            AppData.userFaceShape = classes[maxPos];
            //Toast.makeText(getApplicationContext(), AppData.userFaceShape, Toast.LENGTH_SHORT).show();

            proceedBtn.setEnabled(true);
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == 3){
                AppData.userPicture = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(AppData.userPicture.getWidth(), AppData.userPicture.getHeight());
                AppData.userPicture = ThumbnailUtils.extractThumbnail(AppData.userPicture, dimension, dimension);
                userPictureImg.setImageBitmap(AppData.userPicture);
                captureBtn.setText("Re-take");

                isFaceValid(AppData.userPicture);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserCapture.this, UserPreferences.class));
        finish();
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

    private void awaitHaircutQuery(final Callback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference haircutCollection = db.collection(AppData.userInfo.getGender().equals("Male") ? "mens_haircut" : "women_haircut");

        Query query = haircutCollection.whereArrayContains("suitable_for_face_shape", AppData.userFaceShape);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                int totalDocuments = task.getResult().size();
                int processedDocuments = 0;

                for (QueryDocumentSnapshot document : task.getResult()) {
                    Object faceShapeObject = document.get("suitable_for_face_shape");
                    Object sizeObject = document.get("suitable_for_hair_size");
                    Object typeObject = document.get("suitable_for_hair_type");

                    if (faceShapeObject != null && sizeObject != null && typeObject != null) {
                        String[] faceShapes = convertObjectArrayToStringArray(faceShapeObject);
                        String[] sizes = convertObjectArrayToStringArray(sizeObject);
                        String[] types = convertObjectArrayToStringArray(typeObject);

                        if (faceShapes != null && Arrays.asList(faceShapes).contains(AppData.userFaceShape) &&
                                sizes != null && Arrays.asList(sizes).contains(AppData.hairSize) &&
                                types != null && Arrays.asList(types).contains(AppData.hairType)) {

                            Haircut haircut = new Haircut();
                            haircut.setId(document.getId());
                            haircut.setName(document.get("name").toString());
                            haircut.setSuitableForFaceShape(Arrays.toString(faceShapes));
                            haircut.setSuitableForHairType(Arrays.toString(types));
                            haircut.setSuitableForHairSize(Arrays.toString(sizes));
                            haircut.setDescription(document.get("description").toString());
                            haircut.setUserChoices(Integer.parseInt(document.get("user_choices").toString()));

                            AppData.haircutList.add(haircut);
                        }
                    }

                    processedDocuments++;
                }

                if (processedDocuments == totalDocuments) {
                    callback.onSuccess();
                }
            } else {
                Exception exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                    callback.onFailure(exception);
                }
            }
        });
    }


    private void awaitHaircutHistoryQuery(final Callback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference haircutCollection = db.collection(AppData.userInfo.getGender().equals("Male") ? "mens_haircut_history" : "women_haircut_history");

        Query query = haircutCollection.whereEqualTo("user_faceshape", AppData.userFaceShape)
                .whereEqualTo("user_hair_size", AppData.hairSize)
                .whereEqualTo("user_hair_type", AppData.hairType);

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

                    AppData.historyCutList.add(historyCut);
                }

                Map<String, Integer> count = new HashMap<>();
                for (HistoryCut cut : AppData.historyCutList) {
                    String choice = cut.getUserChoices();
                    count.put(choice, count.getOrDefault(choice, 0) + 1);
                }

                for (Map.Entry<String, Integer> entry : count.entrySet()) {
                    System.out.println("Department: " + entry.getKey() + ", Count: " + entry.getValue());
                    HaircutTree tree = new HaircutTree();
                    tree.setName(entry.getKey());
                    tree.setCount(entry.getValue());

                    AppData.cutCount.add(tree);
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


    // Callback interface
    public interface Callback {
        void onSuccess();
        void onFailure(Exception e);
    }

    private void isFaceValid(Bitmap bitmap) {
        FirebaseApp.initializeApp(this);
        FirebaseVisionFaceDetectorOptions options =
                new FirebaseVisionFaceDetectorOptions.Builder()
                        .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                        .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                        .setMinFaceSize(0.15f)
                        .enableTracking()
                        .build();
        FirebaseVisionFaceDetector detector = FirebaseVision.getInstance()
                .getVisionFaceDetector(options);

        FirebaseVisionImage visionImage = FirebaseVisionImage.fromBitmap(bitmap);

        detector.detectInImage(visionImage)
                .addOnSuccessListener(faces -> {
                    if (faces.size() > 0) {
                        classifyImage(Bitmap.createScaledBitmap(AppData.userPicture, Image_Size, Image_Size, false));
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UserCapture.this);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialoghasnoface, viewGroup, false);
                        builder.setCancelable(false);
                        builder.setView(dialogView);
                        Button tryAgainBtn = dialogView.findViewById(R.id.tryAgainBtn);
                        AlertDialog alertDialog = builder.create();
                        tryAgainBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.hide();
                            }
                        });
                        alertDialog.show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle errors
                    System.out.println("Something went wrong");
                });
    }

    private void awaitHaircutOrder(final Callback callback) {
        Comparator<Haircut> userChoiceComparison = Comparator.comparingInt(Haircut::getUserChoices);
        Collections.sort(AppData.haircutList, userChoiceComparison.reversed());

        callback.onSuccess();
    }

    private void awaitRecommendHaircut(final Callback callback) {
        Map<String, Integer> combinedCounts = new HashMap<>();
        Map<String, Integer> combinedCountsTotal = new HashMap<>();
        for (Haircut haircut : AppData.haircutList) {
            combinedCounts.put(haircut.getName(), haircut.getUserChoices());
            combinedCountsTotal.put(haircut.getName(), haircut.getUserChoices());
        }
        for (HaircutTree scenario : AppData.cutCount) {
            combinedCounts.put(scenario.getName(), combinedCounts.getOrDefault(scenario.getName(), 0) + scenario.getCount());
            combinedCountsTotal.put(scenario.getName(), combinedCountsTotal.getOrDefault(scenario.getName(), 0) + scenario.getCount());
        }

        for (String name : combinedCounts.keySet()) {
            int total = combinedCountsTotal.get(name);
            int combinedCount = combinedCounts.get(name);
            int haircutCount = AppData.haircutList.stream().filter(h -> h.getName().equals(name)).findFirst().orElse(new Haircut("", "", "", "", "", "", 0)).getUserChoices();
            String haircutId = AppData.haircutList.stream().filter(h -> h.getName().equals(name)).findFirst().orElse(new Haircut("", "", "", "", "", "", 0)).getId();

            double average = (double) combinedCount / 2;
            double percentage = (average / haircutCount) * 100;

            HaircutTree r = new HaircutTree(haircutId, name, (int)Math.round(percentage));
            AppData.recommendHaircutList.add(r);
        }

        Comparator<HaircutTree> recommendHaircutComparison = Comparator.comparingInt(HaircutTree::getCount);
        Collections.sort(AppData.recommendHaircutList, recommendHaircutComparison.reversed());

        callback.onSuccess();
    }
}
