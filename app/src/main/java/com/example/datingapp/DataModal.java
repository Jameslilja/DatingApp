package com.example.datingapp;

import java.util.ArrayList;

public class DataModal {

    private String userUid;
    private String username;
    private String email; // was name
    private String firstname; // was job
    private String lastname;
    private String city;
    private String description;
    private String qualifications;

    public DataModal(String userUid, String username, String email, String firstname, String lastname, String city, String description, String qualifications) {
        this.userUid = userUid;
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.description = description;
        this.qualifications = qualifications;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String lastname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(ArrayList<String> qualifications) {
        this.qualifications = String.valueOf(qualifications);
    }

}