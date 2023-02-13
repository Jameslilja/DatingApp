package com.example.datingapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datingapp.backend.User;
import com.example.datingapp.backend.UserQualifications;
import com.example.datingapp.retrofit.RetrofitService;
import com.example.datingapp.retrofit.UserApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondProfileActivity extends AppCompatActivity {

    private String selectedUsername;
    TextView textViewDisplayUsername;
    User otherUser = new User();
    Long visitedProfileId;
    String signedInUserEmail;
    Long signedInUserId;
    TextView textViewDisplayCity;
    TextView textViewDisplayDescription;
    TextView textViewDisplayQualifications;
    int matchPercent;
    TextView textViewMatchPercent;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser;

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
        textViewMatchPercent = findViewById(R.id.textViewMatchPercent);

        getOtherUserInfo();
        getCurrentUserByEmail();
    }

    private void getOtherUserInfo(){
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        userApi.findUserByUsername(selectedUsername).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                visitedProfileId = response.body().getId();
                otherUser.setCity(response.body().getCity());
                textViewDisplayCity.setText(response.body().getCity());
                textViewDisplayDescription.setText(response.body().getDescription());
                //MATCHNINGSPROCENTEN

                System.out.println("userId: " + visitedProfileId);
                System.out.println("body: " + response.body());
                System.out.println("code: " + response.code());

                getOtherUserQualifications();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Logger.getLogger(NewUserFirstTimeLogin.class.getName()).log(Level.SEVERE, "Error occurred", t);
            }
        });
    }

    private void getOtherUserQualifications(){
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        System.out.println("getUserId: " + visitedProfileId);

        userApi.getUserQualificationsByUserId(visitedProfileId).enqueue(new Callback<UserQualifications>() {
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
                Logger.getLogger(NewUserFirstTimeLogin.class.getName()).log(Level.SEVERE, "Error occurred", t);
            }
        });
    }

    private void getCurrentUserByEmail(){
        signedInUserEmail = firebaseAuth.getCurrentUser().getEmail();

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        userApi.findUserByEmail(signedInUserEmail).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                signedInUserId = response.body().getId();
                matchUserAndCurrentProfile();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Logger.getLogger(NewUserFirstTimeLogin.class.getName()).log(Level.SEVERE, "Error occurred", t);
            }
        });

    }

    private void matchUserAndCurrentProfile() {
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        System.out.println("signedInUserId: " + signedInUserId);
        System.out.println("visitedProfileId: " + visitedProfileId);

        userApi.matchUserAndCurrentProfile(signedInUserId, visitedProfileId).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                matchPercent = response.body();
                System.out.println("%: " + matchPercent);
                textViewMatchPercent.setText("Ni matchar till " + matchPercent + "%");
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Logger.getLogger(NewUserFirstTimeLogin.class.getName()).log(Level.SEVERE, "Error occurred", t);
            }
        });
    }

}

