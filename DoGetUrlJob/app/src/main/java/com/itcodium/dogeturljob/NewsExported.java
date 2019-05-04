package com.itcodium.dogeturljob;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class NewsExported {
    private static final String TAG = "ExampleJobService";
    final String url = "http://v0011129.ferozo.com/NewsReader.asmx/NewsGetExported";
    public void getNews(){


        com.android.volley.RequestQueue queue = Volley.newRequestQueue(GlobalClass.context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {
                            Log.d(TAG, "Response OK "+jsonArray.getJSONObject(0).toString()  );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, "Response Error");
                        // Toast.makeText(GlobalClass.context, "doGetUrl: ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(request);

    }
}
