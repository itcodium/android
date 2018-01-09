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
public class StartedService extends Service {

    static final String TAG = "Started Service";
    private int startId;
    private Handler handler;

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
        int delay = 4000; // 4 seconds

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
        Toast.makeText(getApplicationContext(), "ding", Toast.LENGTH_SHORT).show();
        scheduleToastEvent();
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
