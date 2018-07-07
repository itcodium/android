package com.itcodium.doget;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.widget.Toast;

public class WifiBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG_BOOT_BROADCAST_RECEIVER = "BOOT_BROADCAST_RECEIVER";
    private DoGetURL pUrl=new DoGetURL();

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getExtras() == null){
            return;
        }
        NetworkInfo info = intent.getExtras().getParcelable("networkInfo");
        switch (info.getState()){
            case CONNECTED:
                pUrl.doGetUrl();
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

}

