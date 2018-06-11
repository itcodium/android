package educacionit.clase7.startedservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ignacio on 17/08/15.
 */


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import android.content.IntentFilter;
import org.json.JSONObject;
import java.io.IOException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class StartedService extends Service {
    private WifiBroadcastReceiver receiver=new WifiBroadcastReceiver();
    private IntentFilter wifiFilter= new IntentFilter();

    private DoGetURL pUrl=new DoGetURL();

    static final String TAG = "Started Service";
    private int startId=-1;
    private Handler handler;
    // int delay = 1000 *60 * 30; // 1000= 1 seg; (1000*60)=1 min; (1000*60)*60 =1h
    //int delay = 1000 *60 * 2; // 1000= 1 seg; (1000*60)=1 min; (1000*60)*60 =1h
    int delay = 1000 * 4 ; // 1000= 1 seg; (1000*60)=1 min; (1000*60)*60 =1h


    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        wifiFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        Log.v(TAG, "Servicio creado");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;
        scheduleToastEvent();
        registerReceiver(receiver, wifiFilter);
        //Log.v(TAG, "Servicio iniciado " + " StartId " + startId);
        //Toast.makeText(this, "Init service" + Integer.toString(this.startId), Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.startId = -1;
        Log.v(TAG, "Servicio destruido");
        // scheduleToastEvent();
        unregisterReceiver(receiver);
        clearToastEvent();
        Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
    }

    private void scheduleToastEvent() {
        // clearToastEvent(); // Clears out old instances so we don't end up with multiple toasts piling up

        //Toast.makeText(getApplicationContext(),"Schedule Toast: "+ Integer.toString(this.startId), Toast.LENGTH_SHORT).show();

        handler.postDelayed(toastRunnable, delay);

        // Using an Alarm to restart the Service, which should be reliable in 4.4.1 / 4.4.2.
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), StartedService.class);
        intent.putExtra("callType", "Alarm");
        PendingIntent scheduledIntent = PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2)
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + delay, scheduledIntent);
        else
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + delay, scheduledIntent);
    }

    private void clearToastEvent() {
        // Remove Handler-based events
        handler.removeCallbacks(toastRunnable);

        // Remove Alarm-based events
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), StartedService.class );
        PendingIntent scheduledIntent = PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(scheduledIntent);
    }

    private void doToastEvent() {
        // this.sendGet();
        // Toast.makeText(getApplicationContext(),Integer.toString(this.startId), Toast.LENGTH_SHORT).show();
        //scheduleToastEvent();
        pUrl.doGetUrl();

    }

    private Runnable toastRunnable = new Runnable() {
        @Override
        public void run() {
            doToastEvent();
        }
    };

    private class Temporizador extends AsyncTask<Integer, Void, Integer>{

        @Override
        protected Integer doInBackground(Integer... params) {

            long fin = System.currentTimeMillis() + 5 * 1000;

            while (System.currentTimeMillis() < fin) {
                try {
                    Thread.sleep(fin - System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            Log.v(TAG, "Temporizador finalizado StartedId " + integer);

            //stopSelf(integer);
        }
    }

    public class WifiBroadcastReceiver extends BroadcastReceiver {
        private DoGetURL pUrl=new DoGetURL();

        @Override

        public void onReceive(Context context, Intent intent) {

            if(intent.getExtras() == null){
                return;
            }

            NetworkInfo info = intent.getExtras().getParcelable("networkInfo");

            switch (info.getState()){
                case CONNECTED:
                    // doToastEvent();
                    //pUrl.doGetUrl();
                    // Toast.makeText(getApplicationContext(),"CONNECTED", Toast.LENGTH_SHORT).show();
                    break;
                case CONNECTING:
                    break;
                case DISCONNECTED:
                    Toast.makeText(getApplicationContext(),"DISCONNECTED", Toast.LENGTH_SHORT).show();
                    break;
                case DISCONNECTING:;
                    break;
                case SUSPENDED:
                    break;
                default:
            }

        }
    }
}
