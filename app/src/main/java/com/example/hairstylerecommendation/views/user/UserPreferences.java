package com.example.hairstylerecommendation.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hairstylerecommendation.R;
import com.example.hairstylerecommendation.appdata.AppData;

import java.util.ArrayList;
import java.util.Objects;

public class UserPreferences extends AppCompatActivity {

    Spinner hairSizeSpinner, hairTypeSpinner, hairColorSpinner, hairNameSpinner;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpreferences);

        init();
        setUpHairSizeSpinner();

        String[] hairSize = getResources().getStringArray(AppData.userInfo.getGender().equals("Male") ? R.array.hair_sizes_array_for_men : R.array.hair_sizes_array_for_women);
        String[] hairType = getResources().getStringArray(R.array.hair_types_array);
        String[] hairColor = getResources().getStringArray(R.array.hair_colors_array);
        String[] haircut = getResources().getStringArray(AppData.userInfo.getGender().equals("Male") ? R.array.mens_haircut_array : R.array.women_haircut_array);

        ArrayAdapter<String> hairSizeAdapter = new ArrayAdapter<String>(this, R.layout.spinner_txt, hairSize) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view1 = super.getDropDownView(position, convertView, parent);
                TextView txt = (TextView) view1;

                if (position == 0) {
                    // Set the text color to gray to indicate it's a hint
                    txt.setTextColor(getResources().getColor(R.color.white));
                } else {
                    // Set the text color to black for non-hint items
                    txt.setTextColor(getResources().getColor(R.color.white));
                }

                return view1;
            }
        };

        ArrayAdapter<String> hairTypeAdapter = new ArrayAdapter<String>(this, R.layout.spinner_txt, hairType) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view1 = super.getDropDownView(position, convertView, parent);
                TextView txt = (TextView) view1;

                txt.setTextColor(getResources().getColor(R.color.white));
                return view1;
            }
        };

        ArrayAdapter<String> hairColorAdapter = new ArrayAdapter<String>(this, R.layout.spinner_txt, hairColor) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view1 = super.getDropDownView(position, convertView, parent);
                TextView txt = (TextView) view1;

                txt.setTextColor(getResources().getColor(R.color.white));
                return view1;
            }
        };

        ArrayAdapter<String> haircutAdapter = new ArrayAdapter<String>(this, R.layout.spinner_txt, haircut) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view1 = super.getDropDownView(position, convertView, parent);
                TextView txt = (TextView) view1;

                txt.setTextColor(getResources().getColor(R.color.white));
                return view1;
            }
        };

        hairSizeSpinner.setAdapter(hairSizeAdapter);
        hairSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    // Set the text color of the selected item to white
                    ((TextView) view).setTextColor(getResources().getColor(R.color.white
                    ));
                }
                AppData.hairSizeId = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        hairTypeSpinner.setAdapter(hairTypeAdapter);
        hairTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    // Set the text color of the selected item to white
                    ((TextView) view).setTextColor(getResources().getColor(R.color.white));
                }
                AppData.hairTypeId = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        hairColorSpinner.setAdapter(hairColorAdapter);
        hairColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    // Set the text color of the selected item to white
                    ((TextView) view).setTextColor(getResources().getColor(R.color.white));
                }
                AppData.hairColorId = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        hairNameSpinner.setAdapter(haircutAdapter);
        hairNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    // Set the text color of the selected item to white
                    ((TextView) view).setTextColor(getResources().getColor(R.color.white));
                }
                AppData.hairCutNameId = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Check if data has value
        hairSizeSpinner.setSelection(AppData.hairSizeId != -1 ? AppData.hairSizeId : 0);
        hairTypeSpinner.setSelection(AppData.hairTypeId != -1 ? AppData.hairTypeId : 0);
        hairColorSpinner.setSelection(AppData.hairColorId != -1 ? AppData.hairColorId : 0);
        hairNameSpinner.setSelection(AppData.hairCutNameId != -1 ? AppData.hairCutNameId : 0);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hairSizeSpinner.getSelectedItemPosition() != 0 &&
                        hairTypeSpinner.getSelectedItemPosition() != 0 &&
                        hairColorSpinner.getSelectedItemPosition() != 0 &&
                        hairNameSpinner.getSelectedItemPosition() != 0) {

                    // Defensive programming checks
                    if (AppData.hairSizeId >= 0 && AppData.hairSizeId < hairSizeAdapter.getCount()) {
                        AppData.hairSize = hairSizeAdapter.getItem(AppData.hairSizeId);
                    } else {
                        // Handle the case where the index is out of bounds
                    }

                    if (AppData.hairTypeId >= 0 && AppData.hairTypeId < hairTypeAdapter.getCount()) {
                        AppData.hairType = hairTypeAdapter.getItem(AppData.hairTypeId);
                    } else {
                        // Handle the case where the index is out of bounds
                    }

                    if (AppData.hairColorId >= 0 && AppData.hairColorId < hairColorAdapter.getCount()) {
                        AppData.hairColor = hairColorAdapter.getItem(AppData.hairColorId);
                    } else {
                        // Handle the case where the index is out of bounds
                    }

                    if (AppData.hairCutNameId >= 0 && AppData.hairCutNameId < haircutAdapter.getCount()) {
                        AppData.hairCutName = haircutAdapter.getItem(AppData.hairCutNameId);
                    } else {
                        // Handle the case where the index is out of bounds
                    }

                    Intent intent = new Intent(UserPreferences.this, UserCapture.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please answer all preferences question.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUpHairSizeSpinner() {
        // Set hair size options based on gender
        if (AppData.userInfo != null && AppData.userInfo.getGender() != null) {
            String[] hairSizeOptions;

            // Check the identified gender
            if (AppData.userInfo.getGender().equals("Male")) {
                // Male options: Short or Long
                hairSizeOptions = new String[]{"Short", "Long"};
            } else {
                // Female options: Short, Medium, or Long
                hairSizeOptions = new String[]{"Short", "Medium", "Long"};
            }

            ArrayAdapter<String> hairSizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hairSizeOptions);
            hairSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            hairSizeSpinner.setAdapter(hairSizeAdapter);
        }
    }


    public void init() {
        hairSizeSpinner = findViewById(R.id.hairSizeSpinner);
        hairTypeSpinner = findViewById(R.id.hairTypeSpinner);
        hairColorSpinner = findViewById(R.id.hairColorSpinner);
        hairNameSpinner = findViewById(R.id.hairNameSpinner);
        submitBtn = findViewById(R.id.submitBtn);
    }

    @Override
    public void onBackPressed() {
        AppData.resetData();
        Toast.makeText(UserPreferences.this, "userFaceShape " + AppData.userFaceShape, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(UserPreferences.this, UserDashboard.class));
        finish();
    }
}
