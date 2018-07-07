package educacionit.clase7.startedservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class WifiBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG_BOOT_BROADCAST_RECEIVER = "BOOT_BROADCAST_RECEIVER";
    private DoGetURL pUrl=new DoGetURL();

    @Override
    public void onReceive(Context context, Intent intent) {
        initServiceByAlarm(context,intent);

        if(intent.getExtras() == null){
            return;
        }
        NetworkInfo info = intent.getExtras().getParcelable("networkInfo");
        switch (info.getState()){
            case CONNECTED:
                // doToastEvent();
                try{
                    // int mils=(new Random().nextInt(10 + 1)+5)*1000;
                    // Thread.sleep(mils);
                    // pUrl.doGetUrl();
                    // Toast.makeText(getApplicationContext(),"CONNECTED", Toast.LENGTH_SHORT).show();
                }catch (Exception ex){
                }
                break;
            case CONNECTING:
                break;
            case DISCONNECTED:
                // Toast.makeText(GlobalClass.context,"DISCONNECTED", Toast.LENGTH_SHORT).show();
                break;
            case DISCONNECTING:;
                break;
            case SUSPENDED:
                break;
            default:
        }

    }
    private void initServiceByAlarm(Context context, Intent intent){


        if(intent.getAction() != null){
            String action = intent.getAction();
            String message = "BootDeviceReceiver onReceive, action is " + action;
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            if(Intent.ACTION_BOOT_COMPLETED.equals(action)){
                startServiceByAlarm(context);
            }
        }



    }

    private void startServiceByAlarm(Context context)
    {

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, StartedService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long startTime = System.currentTimeMillis();
        long intervalTime = 60*1000;
        String message = "Start service use repeat alarm. ";
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        Log.d(TAG_BOOT_BROADCAST_RECEIVER, message);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, intervalTime, pendingIntent);
    }

}


