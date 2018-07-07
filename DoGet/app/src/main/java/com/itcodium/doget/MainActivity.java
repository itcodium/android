package com.itcodium.doget;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    private Intent serviceIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // serviceIntent = new Intent(this, AndroidServiceStartOnBoot.class);
        // startService(serviceIntent);

    }
}
