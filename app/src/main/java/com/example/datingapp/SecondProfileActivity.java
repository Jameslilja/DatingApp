package com.example.datingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datingapp.backend.User;
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
    TextView textViewDisplayCity;
    TextView textViewDisplayDescription;


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

        getUserInfo();
    }

    private void getUserInfo(){
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        userApi.findUserByUsername(selectedUsername).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                otherUser.setCity(response.body().getCity());
                textViewDisplayCity.setText(response.body().getCity());
                textViewDisplayDescription.setText(response.body().getDescription());
                //MATCHNINGSPROCENTEN
                //KVALIFIKATIONER

                System.out.println("body: " + response.body());
                System.out.println("code: " + response.code());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Logger.getLogger(NewUserFirstTimeLogin.class.getName()).log(Level.SEVERE, "Error occurred", t);
            }
        });
    }

}

