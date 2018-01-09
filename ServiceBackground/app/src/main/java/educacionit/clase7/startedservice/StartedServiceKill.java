package educacionit.clase7.startedservice;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ignacio on 17/08/15.
 */
public class StartedServiceKill extends Service{

    static final String TAG = "Started Service";
    private List<AsyncTask<Integer, Void, Integer>> hijos;

    @Override
    public void onCreate() {
        super.onCreate();
        hijos = new ArrayList<>();
        Log.v(TAG, "Servicio creado");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.v(TAG, "Servicio iniciado " + " StartId " + startId);

        hijos.add(new Temporizador().execute(startId));

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!hijos.isEmpty()) {
            for(AsyncTask<Integer, Void, Integer> hijo : hijos) {
                hijo.cancel(true);
            }
        }
        Log.v(TAG, "Servicio destruido");
    }

    private class Temporizador extends AsyncTask<Integer, Void, Integer>{

        @Override
        protected Integer doInBackground(Integer... params) {

            long fin = System.currentTimeMillis() + 5 * 2000;

            while (System.currentTimeMillis() < fin) {
                try {
                    Thread.sleep(fin - System.currentTimeMillis());
                } catch (InterruptedException e) {
                    Log.v(TAG, "Temporizador " + params[0] + " interrumpido");
                }
            }
            return params[0];

        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            hijos.remove(this);
            Log.v(TAG, "Temporizador finalizado StartedId " + integer);

            //stopSelf(integer);
        }
    }
}
