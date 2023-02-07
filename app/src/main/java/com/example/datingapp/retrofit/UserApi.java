package com.example.datingapp.retrofit;

import com.example.datingapp.backend.User;
import com.example.datingapp.backend.UserPreferences;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {
    //Retrofit2
    @POST("user/saveUser")
    Call<User> registerUser(@Body User user);

    @POST("user/updateUser")
    Call<User> updateUser(@Body User user);

    //@GET("findUsersByUsername")
    Call<User> getUserByUsername(@Path("username") String username);
}
