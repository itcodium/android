package educacionit.clase3.laboratorio;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by ignacio on 27/10/15.
 */
public class SincronizacionService extends Service implements Response.Listener<Producto[]>, Response.ErrorListener{

    static final String URL = "http://www.webkathon.com/pruebasit/products.php";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        GsonRequest gsonRequest = new GsonRequest<>(URL, Producto[].class, null, this, this);

        Volley.newRequestQueue(this).add(gsonRequest);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();

        stopSelf();
    }

    @Override
    public void onResponse(Producto[] response) {

        try {
            ProductoFactory.getInstance(this).borrarTodosLosProductos();

            ProductoFactory.getInstance(this).guardarProductos(Arrays.asList(response));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, R.string.succes_message, Toast.LENGTH_SHORT).show();

        stopSelf();
    }

}
