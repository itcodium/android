package com.edit.boundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class BoundService extends Service {

    static final String TAG = "BoundService";
    private IBinder binder;
    private long inicio;

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new CronometroBinder();
        Log.v(TAG, "Servicio creado");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(TAG, "Servicio desconectado");
        return super.onUnbind(intent);
    }

    public void iniciarCronometro() {
        Log.v(TAG, "Cronometro iniciado");
        inicio = System.currentTimeMillis();
    }

    public long obtenerTiempoDesdeInicio() {
        return System.currentTimeMillis() - inicio;
    }

    public class CronometroBinder extends Binder {
        public BoundService obtenerServicio() {
            return BoundService.this;
        }
    }
}
