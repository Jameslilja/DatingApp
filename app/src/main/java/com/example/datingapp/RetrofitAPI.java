package com.example.datingapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @POST("users")

    //@GET("users")

    Call<DataModal> createPost(@Body DataModal dataModal);

    //Call<DataModal> createGet(@Body DataModal dataModal);
}
