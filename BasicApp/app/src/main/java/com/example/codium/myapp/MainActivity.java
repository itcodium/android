package com.example.codium.myapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SwApiClient.getClient().getCharacterId(1).enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                Character character = response.body();
                character.setName("Andres");
                character.setBirthYear("12-10-87");
                Log.i("Character", new Gson().toJson(character));
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        String tramontinaUrl = "https://www.bazarprofesional.com.ar/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/c/u/cuchillo.jpg";
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) findViewById(R.id.drawee);
        simpleDraweeView.setImageURI(Uri.parse(tramontinaUrl));
    }
}
