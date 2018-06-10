package educacionit.clase7.startedservice;

import android.content.Context;
import android.content.Intent;
import android.net.http.RequestQueue;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.PendingIntent;
import android.app.AlarmManager;

// ------------------------------------

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class StartServiceActivity extends AppCompatActivity {

    private Button iniciar;
    private Button parar;

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_service);

       // -----------------------------------------------



        // ---------------------------------------------------


        serviceIntent = new Intent(this, StartedService.class);

        iniciar = (Button) findViewById(R.id.iniciar);
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(serviceIntent);
            }
        });

        parar = (Button) findViewById(R.id.parar);
        parar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(serviceIntent);
            }
        });
        // scheduleAlarm();
        startService(serviceIntent);
    }

    public void scheduleAlarm() {
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every every half hour from this point onwards
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                AlarmManager.INTERVAL_HALF_HOUR, pIntent);
    }

    public void cancelAlarm() {
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }

}
