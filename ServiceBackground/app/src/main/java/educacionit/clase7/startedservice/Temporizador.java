package educacionit.clase7.startedservice;

import android.os.AsyncTask;
import android.util.Log;

public class Temporizador extends AsyncTask<Integer, Void, Integer> {

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
        Log.v("Temporizador", "Temporizador finalizado StartedId " + integer);
        //stopSelf(integer);
    }
}

