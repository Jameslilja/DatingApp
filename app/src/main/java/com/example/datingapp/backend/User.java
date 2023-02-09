package com.example.datingapp.backend;

public class User {
    private Long id;
    private String city;
    private String description;
    private String email;
    private String firstname;
    private String gender;
    private String lastname;
    private String username;

    public User(Long id, String city, String description, String email, String firstname, String gender, String lastname, String username) {
        this.id = id;
        this.city = city;
        this.description = description;
        this.email = email;
        this.firstname = firstname;
        this.gender = gender;
        this.lastname = lastname;
        this.username = username;
    }

    /*
    public User(String city, String description, String email, String firstname, String gender, String lastname, String username) {
        this.city = city;
        this.description = description;
        this.email = email;
        this.firstname = firstname;
        this.gender = gender;
        this.lastname = lastname;
        this.username = username;
    }

     */

    public User(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "city='" + city + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", gender='" + gender + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
