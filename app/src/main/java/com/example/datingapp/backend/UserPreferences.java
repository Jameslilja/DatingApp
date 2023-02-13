package com.example.datingapp.backend;

public class UserPreferences {
   private Long id;
   private String p1 = "";
   private String p2 = "";
   private String p3 = "";
   private String p4 = "";
   private String p5 = "";
   private Long userId;

    public UserPreferences(Long id, String p1, String p2, String p3, String p4, String p5, Long userId) {
        this.id = id;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;
        this.userId = userId;
    }

    public UserPreferences(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public String getP3() {
        return p3;
    }

    public void setP3(String p3) {
        this.p3 = p3;
    }

    public String getP4() {
        return p4;
    }

    public void setP4(String p4) {
        this.p4 = p4;
    }

    public String getP5() {
        return p5;
    }

    public void setP5(String p5) {
        this.p5 = p5;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return p1 + p2 + p3 +p4 +p5;
    }
}
