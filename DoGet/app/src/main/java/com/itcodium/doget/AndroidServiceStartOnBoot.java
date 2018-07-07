package com.itcodium.doget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class AndroidServiceStartOnBoot extends Service {
    private WifiBroadcastReceiver receiver=new WifiBroadcastReceiver();
    private IntentFilter wifiFilter= new IntentFilter();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // here you can add whatever you want this service to do
        wifiFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        alarmCall();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver(receiver, wifiFilter);
        Log.v("TEST", "Servicio iniciado " + " StartId " + startId);
        return START_STICKY;
    }


    private void alarmCall(){
        Toast.makeText(this, "DoGet App Created", Toast.LENGTH_LONG).show();
        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AndroidServiceStartOnBoot.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long startTime = System.currentTimeMillis();
        long intervalTime = 60*1000;
        String message = "Start service use repeat alarm. ";
        // Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        // new DoGetURL().doGetUrl();
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, intervalTime, pendingIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        Toast.makeText(this, "destruido", Toast.LENGTH_LONG).show();

    }

}