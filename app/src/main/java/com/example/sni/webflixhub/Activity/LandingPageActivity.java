package com.example.sni.webflixhub.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sni.webflixhub.AppConstant.AppConstatnt;
import com.example.sni.webflixhub.Class.VideoModelClass;
import com.example.sni.webflixhub.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class LandingPageActivity extends AppCompatActivity {

    private ImageView lp_logo;
    private Context mContext;
    ArrayList<VideoModelClass> arrayList,movie_arrayList,comics_arrayList,webseries_arrayList
            ,trend_movie_arrayList,trend_comics_arrayList,trend_webseries_arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        requestQueue=Volley.newRequestQueue(this);

        initView();

        if(AppConstatnt.isNetworkAvailable(mContext)) {

            Intent intent = new Intent(mContext, MainActivity.class);
            callAPI(intent);
        }else{
            Toast.makeText(mContext,"Check your internet connection...",Toast.LENGTH_LONG).show();
        }
    }


    private void initView() {
        lp_logo=(ImageView)findViewById(R.id.lp_logo);
        mContext=LandingPageActivity.this;
    }

    RequestQueue requestQueue;
    private void callAPI(final Intent intent) {
        Log.e("callApi","callapi");
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,AppConstatnt.all_response_api, null,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Respone ", response.toString());
                    intent.putExtra("response",response.toString());
                    startActivity(intent);
                    finish();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    callAPI(intent);
                    error.printStackTrace();
                }
            });

            requestQueue.add(jsonObjectRequest);

    }
}
