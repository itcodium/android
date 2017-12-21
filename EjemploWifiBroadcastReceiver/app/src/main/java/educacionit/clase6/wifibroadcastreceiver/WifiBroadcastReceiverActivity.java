package educacionit.clase6.wifibroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class WifiBroadcastReceiverActivity extends AppCompatActivity {

    private WifiBroadcastReceiver receiver;

    private IntentFilter wifiFilter;

    private TextView estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_broadcast_receiver);

        estado = (TextView) findViewById(R.id.estado);

        receiver = new WifiBroadcastReceiver();

        wifiFilter = new IntentFilter();
        wifiFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(receiver, wifiFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    public class WifiBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getExtras() == null){
                return;
            }

            NetworkInfo info = intent.getExtras().getParcelable("networkInfo");

            switch (info.getState()){
                case CONNECTED:
                    estado.setText("Conectado");
                    break;
                case CONNECTING:
                    estado.setText("Conectando");
                    break;
                case DISCONNECTED:
                    estado.setText("Desconectado");
                    break;
                case DISCONNECTING:;
                    estado.setText("Desconectando");
                    break;
                case SUSPENDED:
                    estado.setText("Suspendido");
                    break;
                default:
                    estado.setText("Desconocido");
            }

        }
    }
}
