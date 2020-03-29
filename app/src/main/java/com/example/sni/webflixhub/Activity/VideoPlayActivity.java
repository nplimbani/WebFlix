package com.example.sni.webflixhub.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.sni.webflixhub.Adapter.MovieAdpater;
import com.example.sni.webflixhub.Adapter.WebSeriesRelatedAdapter;
import com.example.sni.webflixhub.AppConstant.AppConstatnt;
import com.example.sni.webflixhub.Class.ParticulaerWebSeriesRelatedModelClass;
import com.example.sni.webflixhub.Class.VideoModelClass;
import com.example.sni.webflixhub.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.startapp.android.publish.ads.banner.Banner;
import com.startapp.android.publish.adsCommon.AutoInterstitialPreferences;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class VideoPlayActivity extends AppCompatActivity {

    private Context mContext;
    private String id, title, url, S_ID=null, V_ID=null, CAT_NAME,d3,k4,pd3,pk4;
    private ImageView movieImg, iv_backward, iv_info, iv_play,iv_share;
    private CheckBox cb_heart;
    private TextView txt_info_like_no, txt_info_view_no,discription_info,txt_related,txt_title;
    private RecyclerView related_recycler;
    private ArrayList<VideoModelClass> arrayList;
    private ArrayList<ParticulaerWebSeriesRelatedModelClass> arrayList11;
    private Button btn_d3,btn_k4,btn_720;
    private Banner ad_banner1,ad_banner2;
    private boolean isAppInstalled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        initView();

        StartAppAd.showAd(mContext);
        id = getIntent().getStringExtra("id");
        S_ID = getIntent().getStringExtra("s_id");
        V_ID = getIntent().getStringExtra("v_id");
        CAT_NAME = getIntent().getStringExtra("cat_name");

        Log.e("ID && V_ID :::",id+"&&"+V_ID);


        if (CAT_NAME.equals("WEB SERIES")) {
            callWEBSERIESAPI();
        } else {
            callAPI();
        }
        //callHandlerForAdShow();
        onClick();

    }


    private void callWEBSERIESAPI() {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, AppConstatnt.particulat_webseries_api + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response ", response.toString());

                JSONArray jsonArray = null;

                try {

                    JSONObject object = response.getJSONObject("data");
                    JSONArray jsonArray1 = response.getJSONArray("related");
                    Log.e("data log", object.toString());
                    Log.e("related log", jsonArray1.toString());
                    // jsonArray = response.getJSONArray("data");
                    //Log.e("onResponse: ","GET array:::" +jsonArray.toString());
                    //JSONObject object = jsonArray.getJSONObject(0);

                    Log.e("onResponse: ", "GET object :::" + object.toString());

                    String id = object.getString("id");
                    String s_id = object.getString("s_id");
                    String v_id = object.getString("v_id");
                    title = object.getString("title");
                    String sub_title = object.getString("sub_title");
                    String view = object.getString("view");
                    url = object.getString("url");
                    String video_like = object.getString("video_like");
                    String thumb = object.getString("thumb");
                    String description = object.getString("description");


                    discription_info.setText(description);
                    txt_info_like_no.setText(video_like+"");
                    txt_info_view_no.setText(view+"");
                    txt_title.setText(title);
                    Glide.with(mContext).load(thumb).into(movieImg);


                    for (int i = 0; i < jsonArray1.length(); i++) {
                        JSONObject jsonObject = jsonArray1.getJSONObject(i);
                        String id1 = jsonObject.getString("id");
                        String s_id1 = jsonObject.getString("s_id");
                        String v_id1 = jsonObject.getString("v_id");
                        String title1 = jsonObject.getString("title");
                        String sub_title1 = jsonObject.getString("sub_title");
                        //int view1 = jsonObject.getInt("view");
                        String url1 = jsonObject.getString("url");
                        //int video_like1 = jsonObject.getInt("video_like");
                        String thumb1 = jsonObject.getString("thumb");

                        ParticulaerWebSeriesRelatedModelClass s = new ParticulaerWebSeriesRelatedModelClass();
                        s.setId(id1);
                        s.setS_id(s_id1);
                        s.setV_id(v_id1);
                        s.setTitle(title1);
                        s.setSub_title(sub_title1);
                        s.setUrl(url1);
                        s.setThumb(thumb1);

                        arrayList11.add(s);
                    }

                    setAdapterWEB();

                    Log.e("ID:URL", id + ":" + url);

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void setAdapterWEB() {
        related_recycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        WebSeriesRelatedAdapter webSeriesRelatedAdapter = new WebSeriesRelatedAdapter(mContext, arrayList11);
        related_recycler.setAdapter(webSeriesRelatedAdapter);
    }


    private void initView() {
        mContext = VideoPlayActivity.this;
        movieImg = (ImageView) findViewById(R.id.movieImg);
        iv_backward = (ImageView) findViewById(R.id.iv_backward);
        iv_info = (ImageView) findViewById(R.id.iv_info);
        iv_play = (ImageView) findViewById(R.id.iv_play);
        cb_heart = (CheckBox) findViewById(R.id.cb_heart);
        /*txt_info_text = (TextView) findViewById(R.id.txt_info_text);
        txt_info_title = (TextView) findViewById(R.id.txt_info_title);
        txt_info_cat_name = (TextView) findViewById(R.id.txt_info_cat_name);
        txt_info_sub_title = (TextView) findViewById(R.id.txt_info_sub_title);
        txt_info_url = (TextView) findViewById(R.id.txt_info_url);*/
        discription_info = (TextView)findViewById(R.id.discription);
        txt_info_view_no = (TextView) findViewById(R.id.txt_info_view_no);
        txt_info_like_no = (TextView) findViewById(R.id.txt_info_like_no);
        related_recycler = (RecyclerView) findViewById(R.id.related_recycler);
        txt_title =(TextView)findViewById(R.id.txt_title);
        arrayList = new ArrayList<>();
        arrayList11 = new ArrayList<>();
        btn_d3=(Button)findViewById(R.id.btn_d3);
        btn_k4=(Button)findViewById(R.id.btn_k4);
        btn_720=(Button)findViewById(R.id.btn_720);
        txt_related=(TextView)findViewById(R.id.txt_related);
        iv_share=(ImageView)findViewById(R.id.iv_share);
        ad_banner1=(Banner)findViewById(R.id.ad_banner1);
        ad_banner2=(Banner)findViewById(R.id.ad_banner2);
        isAppInstalled = appInstalledOrNot("com.utorrent.client");

        //BANNER AD...
        /*adView11 =(AdView)findViewById(R.id.adView11);
        adView12 =(AdView)findViewById(R.id.adView12);
        MobileAds.initialize(mContext, getString(R.string.appID));
        AdRequest adRequest = new AdRequest.Builder().build();
        adView11.loadAd(adRequest);
        adView12.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId(getString(R.string.adUnitInterstitial));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());*/

    }

    private boolean appInstalledOrNot(String s) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(s, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }


    private void callAPI() {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, AppConstatnt.dynm_response_api +id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response ", response.toString());

                JSONArray jsonArray = null;

                try {

                    JSONObject object = response.getJSONObject("data");
                    JSONArray jsonArray1 = response.getJSONArray("related");
                    Log.e("data log", object.toString());
                    Log.e("related log", jsonArray1.toString());
                    // jsonArray = response.getJSONArray("data");
                    //Log.e("onResponse: ","GET array:::" +jsonArray.toString());
                    //JSONObject object = jsonArray.getJSONObject(0);

                    Log.e("onResponse: ", "GET object :::" + object.toString());

                    String id = object.getString("id");
                    String cat_name = object.getString("cat_name");
                    title = object.getString("title");
                    String sub_title = object.getString("sub_title");
                    int view = object.getInt("view");
                    url = object.getString("url");
                    int video_like = object.getInt("video_like");
                    String thumb = object.getString("thumb");
                    String description = object.getString("description");
                    pd3 = object.getString("d3");
                    pk4 = object.getString("k4");

                    Log.e("d3 and k4 ....:::",pd3+"  AND  "+pk4);
                    if(pd3.equals("null")){
                        Log.e("d3 and k4 ....:::",pd3+"  AND  "+pk4);

                        btn_d3.setVisibility(View.GONE);
                    }
                    if(pk4.equals("null")){
                        Log.e("d3 and k4 ....:::",pd3+"  AND  "+pk4);

                        btn_k4.setVisibility(View.GONE);
                    }


                    discription_info.setText(description);
                    txt_info_like_no.setText(video_like + "");
                    txt_info_view_no.setText(view + "");
                    txt_title.setText(title);
                    Glide.with(mContext).load(thumb).into(movieImg);


                    for (int i = 0; i < jsonArray1.length(); i++) {
                        JSONObject jsonObject = jsonArray1.getJSONObject(i);
                        String id1 = jsonObject.getString("id");
                        String cat_name1 = jsonObject.getString("cat_name");
                        String title1 = jsonObject.getString("title");
                        String sub_title1 = jsonObject.getString("sub_title");
                        int view1 = jsonObject.getInt("view");
                        String url1 = jsonObject.getString("url");
                        int video_like1 = jsonObject.getInt("video_like");
                        String thumb1 = jsonObject.getString("thumb");
                        d3 = jsonObject.getString("d3");
                        k4 = jsonObject.getString("k4");

                        /*Log.e("d3 and k4 ....:::",d3+"  AND  "+k4);
                        if(d3==null){
                            btn_d3.setVisibility(View.GONE);
                        }
                        if(k4==null){
                            btn_k4.setVisibility(View.GONE);
                        }*/


                        VideoModelClass v = new VideoModelClass();
                        v.setId(id1);
                        v.setThumb(thumb1);
                        v.setTitle(title1);
                        v.setView(view1);
                        v.setVideo_like(video_like1);
                        v.setUrl(url1);
                        v.setSub_title(sub_title1);
                        v.setCat_name(cat_name1);
                        v.setD3(d3);
                        v.setK4(k4);

                        arrayList.add(v);
                    }

                    setAdapter();

                    Log.e("ID:URL", id + ":" + url);

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void setAdapter() {
        related_recycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        MovieAdpater movieAdpater = new MovieAdpater(mContext, arrayList);
        related_recycler.setAdapter(movieAdpater);
    }

    private void onClick() {
        iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isAppInstalled){
                    Log.e("PLAY btn","Clicked.............");
                    Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                    callViewAPI();
                    StartAppAd.showAd(mContext);
                    startActivity(intent);
                }else{
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.utorrent.client")));
                }
            }
        });

        iv_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cb_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CAT_NAME.equals("WEB SERIES")) {
                    id=V_ID;
                }
                    RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, AppConstatnt.like_response_api +id, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("Response ", response.toString());

                            JSONArray jsonArray = null;


                            try {

                                jsonArray = response.getJSONArray("data");
                                JSONObject object = jsonArray.getJSONObject(0);

                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    requestQueue.add(jsonObjectRequest);
                }


        });


        btn_d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isAppInstalled) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(d3));
                    callViewAPI();
                    StartAppAd.showAd(mContext);
                    startActivity(intent);
                }else{
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.utorrent.client")));
                }
            }
        });

        btn_k4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isAppInstalled) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(k4));
                    callViewAPI();
                    StartAppAd.showAd(mContext);
                    startActivity(intent);
                }else{
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.utorrent.client")));
                }
            }
        });

        btn_720.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isAppInstalled) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    callViewAPI();
                    StartAppAd.showAd(mContext);
                    startActivity(intent);
                }else{
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.utorrent.client")));
                }
            }
        });

        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"You are invited to watch the item from here :\n"+url);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,"Share via..."));
            }
        });
    }

    private void callViewAPI() {
        if (CAT_NAME.equals("WEB SERIES")) {
            id=V_ID;
        }
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, AppConstatnt.view_response_api + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response ", response.toString());

                JSONArray jsonArray = null;


                try {

                    jsonArray = response.getJSONArray("data");
                    JSONObject object = jsonArray.getJSONObject(0);

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}

