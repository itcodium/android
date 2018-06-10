package educacionit.clase7.startedservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
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

import org.json.JSONObject;

import java.io.IOException;


public class StartedService extends Service {

    static final String TAG = "Started Service";
    private int startId;
    private Handler handler;
    int delay = 1000 *60 * 30; // 1000= 1 seg; (1000*60)=1 min; (1000*60)*60 =1h

    private void sendGet(){
        final String url = "http://chatbot-chatbot01.7e14.starter-us-west-2.openshiftapps.com/api/newsreader";
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(this);
                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response) {
                                // display response response.toString()
                                // Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
                                scheduleToastEvent();
                                Log.d(TAG, response.toString());
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Log.d("Error.Response");
                                // Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                                scheduleToastEvent();
                                Log.v(TAG, "Servicio creado");
                            }
                        }
                );
                queue.add(getRequest);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        Log.v(TAG, "Servicio creado");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;
       // Log.v(TAG, "Servicio iniciado " + " StartId " + startId);
       // new Temporizador().execute(startId);
        scheduleToastEvent();
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
        Log.v(TAG, "Servicio destruido");
    }

    private void scheduleToastEvent() {
        clearToastEvent(); // Clears out old instances so we don't end up with multiple toasts piling up


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
        this.sendGet();
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
}
