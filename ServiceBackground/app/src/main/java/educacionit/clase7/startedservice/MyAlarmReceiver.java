package educacionit.clase7.startedservice;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "educacionit.clase7.startedservice.MyTestService";

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MyTestService.class);
        i.putExtra("foo", "bar");
        context.startService(i);
    }
}