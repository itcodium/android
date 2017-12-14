package com.example.codium.myapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SwApiInterface {

    @GET("people/{id}")
    public Call<Character> getCharacterId(@Path("id") int id);
}
