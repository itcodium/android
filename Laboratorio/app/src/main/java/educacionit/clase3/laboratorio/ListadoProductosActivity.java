package educacionit.clase3.laboratorio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ListadoProductosActivity extends AppCompatActivity
        implements ListadoProductosFragment.Callbacks {

    private boolean esDoblePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_list);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

        if (findViewById(R.id.producto_detail_container) != null) {
           esDoblePanel = true;
        }

        startService(new Intent(this, SincronizacionService.class));
    }

    @Override
    public void onProductoSelected(Producto producto) {
        if (esDoblePanel) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.producto_detail_container, DetalleProductoFragment.newInstance(producto.getId()))
                    .commit();
        } else {
            Intent detailIntent = new Intent(this, DetalleProductoActivity.class);
            detailIntent.putExtra(DetalleProductoFragment.ID, producto.getId());

            startActivity(detailIntent);
        }
    }
}
