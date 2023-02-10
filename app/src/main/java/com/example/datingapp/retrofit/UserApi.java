package com.example.datingapp.retrofit;

import com.example.datingapp.backend.Qualification;
import com.example.datingapp.backend.User;
import com.example.datingapp.backend.UserPreferences;
import com.example.datingapp.backend.UserQualifications;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {
    //Retrofit2
    @POST("user/saveUser")
    Call<User> registerUser(@Body User user);

    @POST("userPref/save")
    Call<String> savePreferences(@Body UserPreferences userPreferences);

    @POST("userQ/save")
    Call<String> saveQualifications(@Body UserQualifications userQualifications);

    @POST("user/updateUser")
    Call<String> updateUser(@Body User user);

    @POST("userPref/update")
    Call<String> updateUserPreferences(@Body UserPreferences userPreferences);

    @POST("userQ/changeQualifications")
    Call<String> updateUserQualifications(@Body UserQualifications userQualifications);

    @GET("user/findUserByEmail")
    Call<User> findUserByEmail(@Query("email") String email);

    @GET("user/findAllUsers")
    Call<List<User>> findAllUsers();

    @GET("qualification/findAllQualification")
    Call<List<Qualification>> findAllQualification();
}
