// ReadWriteUserDetails.java
package com.example.hairstylerecommendation.views.user;

public class ReadWriteUserDetails {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String age;
    private String dob;
    private String gender;

    private String profilePicUri;

    public ReadWriteUserDetails() {
        // Default constructor required for Firebase
    }

    public ReadWriteUserDetails(String textFullName, String textEmail, String textPhoneNumber, String textAge, String textDob, String textGender) {
        this.fullName = textFullName;
        this.email = textEmail;
        this.phoneNumber = textPhoneNumber;
        this.age = textAge;
        this.dob = textDob;
        this.gender = textGender;
        this.profilePicUri = profilePicUri;
    }

    // Add getters and setters as needed
    public String getFullname() {
        return fullName;
    }

    public void setFullname(String fullname) {
        this.fullName = fullname;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phoneNumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phoneNumber = phonenumber;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfilePicUri() {return profilePicUri;}
}
