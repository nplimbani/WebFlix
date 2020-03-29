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
import com.example.sni.webflixhub.Adapter.ParticularSeasonAdapter;
import com.example.sni.webflixhub.AppConstant.AppConstatnt;
import com.example.sni.webflixhub.Class.SeasonEpisodeModelClass;
import com.example.sni.webflixhub.R;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParticularSeasonActivity extends AppCompatActivity {


    Context mContext;
    String ID,V_ID,CAT_NAME;
    RecyclerView particular_SeasonRecycler;
    ArrayList<SeasonEpisodeModelClass> arrayList;
    ImageView iv_back_toolbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_season);

        ID = getIntent().getStringExtra("id");
        V_ID = getIntent().getStringExtra("v_id");
        CAT_NAME=getIntent().getStringExtra("cat_name");

        initView();
        callAPI();
    }

    private void callAPI() {
        AppConstatnt.showProgressDialog(mContext);
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, AppConstatnt.season_episode_api+ID, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                AppConstatnt.dismissProgressDialog();
                Log.e("Respone ", response.toString());


                try {
                    //JSONObject jsonObject = response.getJSONObject("data");
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        String v_id = jsonObject.getString("v_id");
                        String s_id = jsonObject.getString("s_id");
                        String title = jsonObject.getString("title");
                        String sub_title = jsonObject.getString("sub_title");
                        String url = jsonObject.getString("url");
                        String thumb = jsonObject.getString("thumb");

                        //set list item...
                        SeasonEpisodeModelClass s = new SeasonEpisodeModelClass();
                        s.setId(id);
                        s.setS_id(s_id);
                        s.setV_id(v_id);
                        s.setTitle(title);
                        s.setSub_title(sub_title);
                        s.setUrl(url);
                        s.setThumb(thumb);

                        arrayList.add(s);

                        Log.e("ID:V_ID", id + ":" + v_id);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //JSONArray jsonArray = jsonObject.getJSONArray("popular");
                    //JSONArray jsonArray1=jsonObject.getJSONArray("trending");
                    //Log.e("PopularJasonArray", j.toString());
                    //Log.e("TrendingJasonArray",jsonArray1.toString());



                    //set Adapters...
                    setAdpater();

                }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);


    }

    private void setAdpater() {
        particular_SeasonRecycler.setLayoutManager(new GridLayoutManager(mContext, 3));
        ParticularSeasonAdapter particularSeasonAdapter = new ParticularSeasonAdapter(mContext,arrayList);
        particular_SeasonRecycler.setAdapter(particularSeasonAdapter);
    }


    private void initView() {
        mContext=ParticularSeasonActivity.this;
        particular_SeasonRecycler=(RecyclerView)findViewById(R.id.particular_SeasonRecycler);
        arrayList=new ArrayList<SeasonEpisodeModelClass>();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);


        iv_back_toolbar1=(ImageView)findViewById(R.id.iv_back_toolbar1);



        iv_back_toolbar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
