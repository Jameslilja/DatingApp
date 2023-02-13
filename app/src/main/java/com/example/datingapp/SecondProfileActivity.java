package com.example.datingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datingapp.backend.User;
import com.example.datingapp.backend.UserQualifications;
import com.example.datingapp.retrofit.RetrofitService;
import com.example.datingapp.retrofit.UserApi;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondProfileActivity extends AppCompatActivity {

    private String selectedUsername;
    TextView textViewDisplayUsername;
    User otherUser = new User();
    Long userId;
    TextView textViewDisplayCity;
    TextView textViewDisplayDescription;
    TextView textViewDisplayQualifications;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_profile);

        //get the users username and display it
        Bundle bundle = getIntent().getExtras();
        selectedUsername = bundle.getString("selectedUsername");
        textViewDisplayUsername = findViewById(R.id.textViewDisplayUsername);
        textViewDisplayUsername.setText(selectedUsername);
        textViewDisplayCity = findViewById(R.id.textViewItemCity);
        textViewDisplayDescription = findViewById(R.id.textViewItemDescription);
        textViewDisplayQualifications = findViewById(R.id.textViewItemQualifications);

        getUserInfo();
    }

    private void getUserInfo(){
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        userApi.findUserByUsername(selectedUsername).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                userId = response.body().getId();
                otherUser.setCity(response.body().getCity());
                textViewDisplayCity.setText(response.body().getCity());
                textViewDisplayDescription.setText(response.body().getDescription());
                //MATCHNINGSPROCENTEN

                System.out.println("userId: " + userId);
                System.out.println("body: " + response.body());
                System.out.println("code: " + response.code());

                getUserQualifications();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Logger.getLogger(NewUserFirstTimeLogin.class.getName()).log(Level.SEVERE, "Error occurred", t);
            }
        });
    }

    private void getUserQualifications(){
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        System.out.println("getUserId: " + userId);

        userApi.getUserQualificationsByUserId(userId).enqueue(new Callback<UserQualifications>() {
            @Override
            public void onResponse(Call<UserQualifications> call, Response<UserQualifications> response) {
                System.out.println("body: " + response.body());
                System.out.println("code: " + response.code());

                String allQualifications = "";

                String q1 = response.body().getQ1();
                if (!q1.equals("")){
                    allQualifications += q1 + ", ";
                }
                String q2 = response.body().getQ2();
                if (!q2.equals("")){
                    allQualifications += q2 + ", ";
                }
                String q3 = response.body().getQ3();
                if (!q3.equals("")){
                    allQualifications += q3 + ", ";
                }
                String q4 = response.body().getQ4();
                if (!q4.equals("")){
                    allQualifications += q4 + ", ";
                }
                String q5 = response.body().getQ5();
                if (!q5.equals("")){
                    allQualifications += q5 + ", ";
                }
                if (allQualifications.endsWith(",")) {
                    StringBuffer stringBuffer = new StringBuffer(allQualifications);
                    allQualifications = String.valueOf(stringBuffer.deleteCharAt(stringBuffer.length() -1));
                }
                textViewDisplayQualifications.setText(allQualifications);
            }

            @Override
            public void onFailure(Call<UserQualifications> call, Throwable t) {

            }
        });
    }

}

