package com.example.practice.retrofit;

import com.example.practice.model.MultipleResorces;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("users?page=2")
    Call<MultipleResorces.Root> getUsers();


}
