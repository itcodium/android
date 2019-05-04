package com.itcodium.doget;


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
        final String url = "http://itcodium.tech/copynews.php";
         String lastSaved=sLocalTime.getValue();
          //long resultadoDiff=sLocalTime.getDateDiff(lastSaved);

       //++ Toast.makeText(GlobalClass.context, "doGetUrl (: "+lastSaved+")", Toast.LENGTH_SHORT).show();
         //Toast.makeText(GlobalClass.context, "Call URL dif "+ Long.toString(resultadoDiff), Toast.LENGTH_SHORT).show();
        long vadd=sLocalTime.getDiffFromLastSaved();
       //  Toast.makeText(GlobalClass.context, "Diff Time : "+vadd, Toast.LENGTH_SHORT).show();
        Toast.makeText(GlobalClass.context, "URL GET : ", Toast.LENGTH_LONG).show();
        if(vadd>=2 ){
          //  Toast.makeText(GlobalClass.context, "doGetUrl 3: "+lastSaved, Toast.LENGTH_SHORT).show();
            /*
            sLocalTime.saveTime("PENDIENTE");

            com.android.volley.RequestQueue queue = Volley.newRequestQueue(GlobalClass.context);
            JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Toast.makeText(GlobalClass.context, "doGetUrl: OK", Toast.LENGTH_SHORT).show();
                        sLocalTime.saveTime("OK");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(GlobalClass.context, "doGetUrl: ERROR", Toast.LENGTH_SHORT).show();
                        sLocalTime.saveTime("ERROR");
                    }
                });
            queue.add(request);
            */


        }

    }
}
