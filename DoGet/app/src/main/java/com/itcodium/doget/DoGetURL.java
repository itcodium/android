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
        final String url = "http://chatbot-chatbot01.7e14.starter-us-west-2.openshiftapps.com/api/newsreader";
        // String lastSaved=sLocalTime.getValue();
        // long resultadoDiff=sLocalTime.getDateDiff(lastSaved);

        // Toast.makeText(GlobalClass.context, "Call URL dif "+ Long.toString(resultadoDiff), Toast.LENGTH_SHORT).show();

        if(sLocalTime.getDiffFromLastSaved()>=30){
            sLocalTime.saveTime("PENDIENTE");
            com.android.volley.RequestQueue queue = Volley.newRequestQueue(GlobalClass.context);
            JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        sLocalTime.saveTime("OK");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        sLocalTime.saveTime("ERROR");
                    }
                });
            queue.add(request);
        }

    }
}
