package com.edit.boundservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button iniciar;
    private Button obtener;

    private BoundService cronometro;
    private boolean estaConectado;
    private ServiceConnection conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciar = (Button) findViewById(R.id.iniciar);
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cronometro.iniciarCronometro();
            }
        });

        obtener = (Button) findViewById(R.id.obtener);
        obtener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cronometro != null) {
                    int tiempo = (int) (cronometro.obtenerTiempoDesdeInicio()/1000);
                    Toast.makeText(MainActivity.this, "El cronometro inicio hace " + tiempo + " segundos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        conexion = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder service) {
                BoundService.CronometroBinder binder = (BoundService.CronometroBinder) service;
                cronometro = binder.obtenerServicio();
                estaConectado = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                estaConectado = false;
            }
        };
        Intent intent = new Intent(this, BoundService.class);
        getApplicationContext().bindService(intent, conexion, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(estaConectado) {
            unbindService(conexion);
            estaConectado = false;
        }
    }
}
