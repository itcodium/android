package com.example.codium.myapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SwApiClient {

    private static SwApiInterface client;
    private static String BASE_URL = "https://swapi.co/api/";

    public static SwApiInterface getClient() {
        if(client == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            return retrofit.create(SwApiInterface.class);
        } else {
            return client;
        }
    }
}
