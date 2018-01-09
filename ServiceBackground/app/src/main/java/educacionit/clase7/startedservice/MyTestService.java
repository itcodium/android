package educacionit.clase7.startedservice;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by pablo.haddad on 09/01/2018.
 */
import android.util.Log;

public class MyTestService extends IntentService {
    public MyTestService() {
        super("MyTestService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Do the task here
        Log.i("MyTestService", "Service running");
    }
}