package com.grobocop.aac;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.ReplacementSpan;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public String Url = "http://192.168.0.132";
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imgView = findViewById(R.id.mainImageView);
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String imageUrl;
                        try {
                            imageUrl = response.getString("url");

                            Glide
                                    .with(imgView.getContext())
                                    .load(Url+imageUrl)
                                    .into(imgView);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },null
        );

        request.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);


        //ImageView imgView = findViewById(R.id.mainImageView);

        /*Glide
                .with(this)
                .load(Url + 	"/static/lena.jpg")
                .into(imgView);*/


    }
}
