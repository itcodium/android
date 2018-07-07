package educacionit.clase7.startedservice;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class DoGetURL {
    SaveDataLocal sLocalTime=new SaveDataLocal();

    public DoGetURL (){
    }
    public void doGetUrl(){
        final String url = "http://chatbot-chatbot01.7e14.starter-us-west-2.openshiftapps.com/api/newsreader";
        //Toast.makeText(GlobalClass.context, sLocalTime.getValue(), Toast.LENGTH_SHORT).show();
        try{
            int mils=(new Random().nextInt(10 + 1)+10)*1000;
          //  Thread.sleep(mils);
        }catch (Exception Ex){

        }


        String lastSaved=sLocalTime.getValue();

        long resultadoDiff=sLocalTime.getDateDiff(lastSaved);

       // if(resultadoDiff>=59){
            com.android.volley.RequestQueue queue = Volley.newRequestQueue(GlobalClass.context);
            //Toast.makeText(GlobalClass.context, url , Toast.LENGTH_SHORT).show();
            JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                         //Toast.makeText(GlobalClass.context, "OK", Toast.LENGTH_SHORT).show();

                        Log.d("TEST 111", jsonArray.toString());
                        /*for(int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                            }
                            catch(JSONException e) {
                                Log.d("TEST 111", e.getLocalizedMessage());
                            }
                        }*/
                        sLocalTime.saveTime();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("TEST 111", "Error");
                       // Toast.makeText(GlobalClass.context, "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            queue.add(request);
        //}

    }
}
