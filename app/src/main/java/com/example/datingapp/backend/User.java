package com.example.datingapp.backend;

public class User {
    private String firebase_id;
    private String city;
    private String description;
    private String email;
    private String firstname;
    private String gender;
    private String lastname;
    private String password;
    private String username;

    public User(String firebase_id, String city, String description, String email, String firstname, String gender, String lastname, String password, String username) {
        this.firebase_id = firebase_id;
        this.city = city;
        this.description = description;
        this.email = email;
        this.firstname = firstname;
        this.gender = gender;
        this.lastname = lastname;
        this.password = password;
        this.username = username;
    }

    public User(){

    }

    public String getFirebase_id() {
        return firebase_id;
    }

    public void setFirebase_id(String firebase_id) {
        this.firebase_id = firebase_id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
