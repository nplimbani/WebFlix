package com.example.sni.webflixhub.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sni.webflixhub.Adapter.WebSeriesSeasonAdapter;
import com.example.sni.webflixhub.AppConstant.AppConstatnt;
import com.example.sni.webflixhub.Class.WebSeriesSeasonModelClass;
import com.example.sni.webflixhub.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WebSeriesSeasonActivity extends AppCompatActivity {

    ///change in API with ID...............

    Context mContext;
    String ID,CAT_NAME;
    RecyclerView webseries_SeasonRecycler;
    ArrayList<WebSeriesSeasonModelClass> arrayList;
    ImageView iv_back_toolbar1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_series_season);

        ID = getIntent().getStringExtra("id");
        CAT_NAME=getIntent().getStringExtra("cat_name");

        initView();
        callAPI();

    }

    private void callAPI() {
            AppConstatnt.showProgressDialog(mContext);
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, AppConstatnt.particular_webseries_season_api+ID, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    AppConstatnt.dismissProgressDialog();
                    Log.e("Respone ", response.toString());

                    try {
                        //JSONObject jsonObject = response.getJSONObject("data");
                        JSONArray jsonArray = response.getJSONArray("data");
                        Log.e("PopularJasonArray", jsonArray.toString());
                        //Log.e("TrendingJasonArray",jsonArray1.toString());


                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id = jsonObject.getString("id");
                            String v_id = jsonObject.getString("v_id");
                            String title = jsonObject.getString("title");
                            String sub_title = jsonObject.getString("sub_title");
                            String thumb = jsonObject.getString("thumb");

                            //set list item...
                            WebSeriesSeasonModelClass v = new WebSeriesSeasonModelClass();
                            v.setId(id);
                            v.setV_id(v_id);
                            v.setTitle(title);
                            v.setSub_title(sub_title);
                            v.setThumb(thumb);

                            arrayList.add(v);

                            Log.e("ID:V_ID", id + ":" + v_id);
                        }


                    //set Adapters...
                        setAdpater();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            requestQueue.add(jsonObjectRequest);


    }

    private void setAdpater() {
        webseries_SeasonRecycler.setLayoutManager(new GridLayoutManager(mContext, 3));
        WebSeriesSeasonAdapter webSeriesSeasonAdapter=new WebSeriesSeasonAdapter(mContext,arrayList);
        webseries_SeasonRecycler.setAdapter(webSeriesSeasonAdapter);
    }


    private void initView() {
        mContext=WebSeriesSeasonActivity.this;
        webseries_SeasonRecycler=(RecyclerView)findViewById(R.id.webseries_SeasonRecycler);
        arrayList=new ArrayList<WebSeriesSeasonModelClass>();
        iv_back_toolbar1=(ImageView)findViewById(R.id.iv_back_toolbar1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);


        iv_back_toolbar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
