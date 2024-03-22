package com.example.hairstylerecommendation.views.user;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hairstylerecommendation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserProfiling extends AppCompatActivity {
    private EditText nFullname, nEmail, nPhoneNumber, nAge, nDob;
    private RadioGroup genderselectedgroup;
    private RadioButton genderselected;
    private ProgressBar progressBar;
    private Button buttonSubmit;

    private String userEmail;
    private ImageView calendarIcon;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofiling);


        Toast.makeText(UserProfiling.this, "You can Input your informations", Toast.LENGTH_LONG).show();

        nFullname = findViewById(R.id.fullname);
        nEmail = findViewById(R.id.emailaddress);
        nPhoneNumber = findViewById(R.id.phonenumber);
        nAge = findViewById(R.id.agenumber);
        nDob = findViewById(R.id.birthdate);
        calendarIcon =findViewById(R.id.calendarIcon);
        progressBar = findViewById(R.id.progressbarprofiling);

        //Radio Group for gender
        genderselectedgroup = findViewById(R.id.radio_group_profiling_gender);
        genderselectedgroup.clearCheck();

        userEmail = getIntent().getStringExtra("userEmail");

        firebaseAuth = FirebaseAuth.getInstance();



       buttonSubmit = findViewById(R.id.buttonSubmit);





        nDob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateAgeFromDob();
            }
        });








        calendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarDialog(nDob);
            }

            private boolean isEditTextUnder18(EditText dobEditText) {
                String enteredDate = dobEditText.getText().toString().trim();

                if (!enteredDate.isEmpty()) {
                    String[] parts = enteredDate.split("/");
                    if (parts.length == 3) {
                        int day = Integer.parseInt(parts[0]);
                        int month = Integer.parseInt(parts[1]) - 1;
                        int year = Integer.parseInt(parts[2]);

                        return isUnder18(year, month, day);
                    }
                }

                return false;
            }
            private void showCalendarDialog(EditText dobEditText) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UserProfiling.this,
                        (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                            // Perform validation checks
                            if (isFutureDate(selectedYear, selectedMonth, selectedDayOfMonth)) {
                                Toast.makeText(UserProfiling.this, "That doesn't seem right", Toast.LENGTH_SHORT).show();
                            } else {
                                Calendar today = Calendar.getInstance();
                                Calendar dob = new GregorianCalendar(selectedYear, selectedMonth, selectedDayOfMonth);
                                int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
                                if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
                                    age--;
                                }

                                // Display age
                                String selectedDate = String.format("%02d/%02d/%d", selectedDayOfMonth, selectedMonth + 1, selectedYear);
                                dobEditText.setText(selectedDate);

                                if (age < 18) {
                                    Toast.makeText(UserProfiling.this, "You are under 18", Toast.LENGTH_SHORT).show();
                                    dobEditText.setText(""); // Clear the EditText
                                } else {
                                    nAge.setText(String.valueOf(age));
                                }
                            }
                        },
                        year,
                        month,
                        day);

                datePickerDialog.show();
            }

            private boolean isFutureDate(int year, int month, int day) {
                Calendar selectedCalendar = new GregorianCalendar(year, month, day);
                Calendar currentCalendar = Calendar.getInstance();
                return selectedCalendar.after(currentCalendar);
            }

            private boolean isUnder18(int year, int month, int day) {
                Calendar dobCalendar = new GregorianCalendar(year, month, day);
                Calendar eighteenYearsAgo = Calendar.getInstance();
                eighteenYearsAgo.add(Calendar.YEAR, -18);
                return dobCalendar.after(eighteenYearsAgo);
            }
        });










        buttonSubmit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                int selectedGenderId = genderselectedgroup.getCheckedRadioButtonId();
                genderselected = findViewById(selectedGenderId);

                String textFullname = nFullname.getText().toString();
                String textEmail = nEmail.getText().toString();
                String textPhonenumber = nPhoneNumber.getText().toString();
                String textAge = nAge.getText().toString();
                String textDob = nDob.getText().toString();
                String textGender;

                if (TextUtils.isEmpty(textFullname)){
                    Toast.makeText(UserProfiling.this, "Please input your full name", Toast.LENGTH_SHORT).show();
                    nFullname.setError("Full Name Required!");
                    nFullname.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(textEmail)){
                    Toast.makeText(UserProfiling.this, "Please input your email address", Toast.LENGTH_SHORT).show();
                    nEmail.setError("Email Address is Required!");
                    nEmail.requestFocus();
                    return;
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(UserProfiling.this, "Please re-enter your email address", Toast.LENGTH_SHORT).show();
                    nEmail.setError("Valid Email Address is Required!");
                    nEmail.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(textPhonenumber)) {
                    Toast.makeText(UserProfiling.this, "Please enter your mobile number", Toast.LENGTH_SHORT).show();
                    nPhoneNumber.setError("Phone Number is Required!");
                    nPhoneNumber.requestFocus();
                    return;

                } else if (textPhonenumber.length()!=11) {
                    Toast.makeText(UserProfiling.this, "Please re-enter your mobile number", Toast.LENGTH_SHORT).show();
                    nPhoneNumber.setError("Mobile Number should not exceed 11 digits");
                    nPhoneNumber.requestFocus();
                    return;
                } else if (!textPhonenumber.startsWith("09")) {
                    Toast.makeText(UserProfiling.this, "Phone Number should start with 09", Toast.LENGTH_SHORT).show();
                    nPhoneNumber.setError("Error Invalid Phone Format");
                    nPhoneNumber.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(textDob)) {
                    Toast.makeText(UserProfiling.this, "Please enter your date of birth", Toast.LENGTH_SHORT).show();
                    nDob.setError("Date of Birth is Required!");
                    nDob.requestFocus();
                    return;
                }else if (!isValidDateFormat(textDob)) {
                    Toast.makeText(UserProfiling.this, "Please enter a valid date of birth (dd/mm/yyyy)", Toast.LENGTH_SHORT).show();
                    nDob.setError("Invalid date format");
                    nDob.requestFocus();
                    return;
                } else if (genderselectedgroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(UserProfiling.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(textAge)) {
                    Toast.makeText(UserProfiling.this, "Please enter your age", Toast.LENGTH_SHORT).show();
                    nAge.setError("Age is Required!");
                    nAge.requestFocus();
                    return;
                }  else {
                    try {
                        int age = Integer.parseInt(textAge);

                        if (age >= 18 && age <= 30) {
                            // Age is within the valid range (18 to 30)

                        } else {
                            // Age is not within the valid range
                            Toast.makeText(UserProfiling.this, "Age must be between 18 and 30", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            return;
                        }
                    } catch (NumberFormatException e) {
                        // Handle the case where the entered text is not a valid integer
                        Toast.makeText(UserProfiling.this, "Please enter a valid age", Toast.LENGTH_SHORT).show();
                        nAge.setError("Invalid Age!");
                        nAge.requestFocus();
                        progressBar.setVisibility(View.GONE);
                        return;
                    }
                }

                textGender = genderselected.getText().toString();
                saveUserDataToFirebase(textFullname, textEmail, textPhonenumber, textAge, textDob, textGender);
            }

            private boolean isValidDateFormat(String date) {
                // Define the regular expression for dd/mm/yyyy format
                String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$";

                // Compile the regular expression
                Pattern pattern = Pattern.compile(regex);

                // Create matcher object
                Matcher matcher = pattern.matcher(date);

                // Return true if the date matches the pattern, false otherwise
                return matcher.matches();
            }

            private void saveUserDataToFirebase(String textFullname, String textEmail, String textPhonenumber, String textAge, String textDob, String textGender) {

                // Create a unique key for the user
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = database.getReference("User_Profiling");
                // Create a User object with the provided data
                ReadWriteUserDetails writeUserDetails  = new ReadWriteUserDetails(textFullname, textEmail, textPhonenumber,textAge, textDob, textGender);

                //DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("UserProfling");
                if (databaseReference != null) {
                    databaseReference.child(firebaseAuth.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(UserProfiling.this, UserEmailVerification.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Handle the error, log it, or show a message to the user
                                Toast.makeText(UserProfiling.this, "Failed to save user details.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // Log an error or handle the null reference appropriately
                    Toast.makeText(UserProfiling.this, "Database reference is null.", Toast.LENGTH_SHORT).show();
                }



            }


        });
    }
    private boolean isValidDateFormat(String date) {
        // Define the regular expression for dd/mm/yyyy format
        String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$";

        // Compile the regular expression
        Pattern pattern = Pattern.compile(regex);

        // Create matcher object
        Matcher matcher = pattern.matcher(date);

        // Return true if the date matches the pattern, false otherwise
        return matcher.matches();
    }

    private boolean isUnder18(int year, int month, int day) {
        Calendar dobCalendar = new GregorianCalendar(year, month, day);
        Calendar eighteenYearsAgo = Calendar.getInstance();
        eighteenYearsAgo.add(Calendar.YEAR, -18);
        return dobCalendar.after(eighteenYearsAgo);
    }

    private void updateAgeFromDob() {

        String enteredDate = nDob.getText().toString().trim();

        if (isValidDateFormat(enteredDate)) {
            String[] parts = enteredDate.split("/");
            if (parts.length == 3) {
                int day = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]) - 1;
                int year = Integer.parseInt(parts[2]);

                if (!isUnder18(year, month, day)) {
                    Calendar today = Calendar.getInstance();
                    Calendar dob = new GregorianCalendar(year, month, day);
                    int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
                    if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
                        age--;
                    }

                    nAge.setText(String.valueOf(age));
                } else {
                    nAge.setText(""); // Clear the age if the user is under 18
                }
            }
        } else {
            nAge.setText(""); // Clear the age if the date format is invalid
        }
    }


}
