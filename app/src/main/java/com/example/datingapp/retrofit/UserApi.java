package com.example.datingapp.retrofit;

import com.example.datingapp.backend.User;
import com.example.datingapp.backend.UserPreferences;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {
    @POST("/saveUser")
    Call<User> registerUser(@Body User user);

    @POST("/save")
    Call <UserPreferences> savePreferences(@Body UserPreferences userPreferences);
}
