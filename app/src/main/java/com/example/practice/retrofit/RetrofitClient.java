package com.example.practice.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private static ApiService apiService;

    private RetrofitClient(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://reqres.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public static synchronized RetrofitClient getInstance(){
        if (null == instance){
            instance = new RetrofitClient();
        }
        return instance;
    }
    public ApiService apiService(){
        return apiService;
    }
}
