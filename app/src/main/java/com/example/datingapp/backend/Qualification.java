package com.example.datingapp.backend;

public class Qualification {
    private Long id;
    private String qualification;

    public Qualification(Long id, String qualification){
        this.id = id;
        this.qualification = qualification;}

    public Qualification(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    @Override
    public String toString() {
        return "Kvalifikation: " + qualification;
    }
}
