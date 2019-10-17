package com.example.aymen.androidchat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Belal on 10/2/2017.
 */

public interface Api {

    String BASE_URL = "https://192.168.43.246:3000/messageUser/";

    @GET("marvel")
    Call<List<MessageNet>> getHeroes();
}