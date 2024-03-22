// User.java
package com.example.hairstylerecommendation.views.user;

public class User {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String age;
    private String dob;
    private String gender;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String textFullName, String textEmail, String textPhoneNumber, String textAge, String textDob, String textGender) {
        this.fullName = textFullName;
        this.email = textEmail;
        this.phoneNumber = textPhoneNumber;
        this.age = textAge;
        this.dob = textDob;
        this.gender = textGender;
    }

    // Add getters and setters as needed
}
