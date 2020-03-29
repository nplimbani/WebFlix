package com.example.sni.webflixhub.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sni.webflixhub.Adapter.CollectionAdapter;
import com.example.sni.webflixhub.Adapter.WebSeriesAdapter;
import com.example.sni.webflixhub.AppConstant.AppConstatnt;
import com.example.sni.webflixhub.Class.VideoModelClass;
import com.example.sni.webflixhub.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class WebSeriesFragment extends Fragment {

    private Context mContext;
    private RecyclerView webseriesRecycler;
    private ArrayList<VideoModelClass> arrayList;
    private RewardedVideoAd mRewardedVideoAdD;

    public WebSeriesFragment() {

    }

    @SuppressLint("ValidFragment")
    public WebSeriesFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.webseries_fragment, container, false);

        initView(view);
        mRewardedVideoAdD = MobileAds.getRewardedVideoAdInstance(mContext);
        //mRewardedVideoAdD.setRewardedVideoAdListener(mContext);
        mRewardedVideoAdD.loadAd(getString(R.string.adUnitVideo),
                new AdRequest.Builder().build());

        callHandlerForAdShow();


        callAPI();

        return view;

    }

    private void callHandlerForAdShow() {
        //Handler
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            private Handler updateUI = new Handler() {
                @Override
                public void dispatchMessage(Message msg) {
                    super.dispatchMessage(msg);
                    Task();
                }
            };

            private void Task() {
                if (mRewardedVideoAdD.isLoaded()) {
                    mRewardedVideoAdD.show();
                } else {
                    Log.e("RewardAd ", "Not loaded...");
                }
            }


            public void run() {
                try {
                    updateUI.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000 * 60 * 5);
    }


    private void callAPI() {
        AppConstatnt.showProgressDialog(mContext);
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, AppConstatnt.webseries_response_api, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                AppConstatnt.dismissProgressDialog();
                Log.e("Respone ", response.toString());

                try {

                    JSONObject jsonObject = response.getJSONObject("data");
                    JSONArray jsonArray = jsonObject.getJSONArray("popular");
                    //JSONArray jsonArray1=jsonObject.getJSONArray("trending");
                    Log.e("PopularJasonArray", jsonArray.toString());
                    //Log.e("TrendingJasonArray",jsonArray1.toString());


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String cat_name = object.getString("cat_name");
                        String title = object.getString("title");
                        String sub_title = object.getString("sub_title");
                        int view = object.getInt("view");
                        String url = object.getString("url");
                        String thumb = object.getString("thumb");

                        //set list item...
                        VideoModelClass v = new VideoModelClass();
                        v.setThumb(thumb);
                        v.setTitle(title);
                        v.setId(id);
                        v.setView(view);
                        v.setUrl(url);
                        v.setSub_title(sub_title);
                        v.setCat_name(cat_name);

                        arrayList.add(v);

                        Log.e("ID:URL", id + ":" + url);

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
        webseriesRecycler.setLayoutManager(new GridLayoutManager(mContext, 3));
        WebSeriesAdapter webSeriesAdapter=new WebSeriesAdapter(mContext,arrayList);
        webseriesRecycler.setAdapter(webSeriesAdapter);
    }



    private void initView(View view) {
        webseriesRecycler = (RecyclerView)view.findViewById(R.id.webseriesRecycler);
        arrayList=new ArrayList<>();
    }
}
