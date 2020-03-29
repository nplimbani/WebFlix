package com.example.sni.webflixhub.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sni.webflixhub.Activity.DivertFromDiscoverActivity;
import com.example.sni.webflixhub.Adapter.CardAdapter;
import com.example.sni.webflixhub.Adapter.CollectionAdapter;
import com.example.sni.webflixhub.Adapter.ComicsAdapter;
import com.example.sni.webflixhub.Adapter.MovieAdpater;
import com.example.sni.webflixhub.Adapter.WebSeriesAdapter;
import com.example.sni.webflixhub.AppConstant.AppConstatnt;
import com.example.sni.webflixhub.Class.CardViewModelClass;
import com.example.sni.webflixhub.Class.VideoModelClass;
import com.example.sni.webflixhub.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import com.startapp.android.publish.ads.banner.Banner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DiscoverFragment extends Fragment {
    private Context mContext;
    private RecyclerView card_recycler,movie_recycler,
            webseries_recycler,trending_movie_recycler,trending_webseries_recycler,hindi_movie_recycler,
            gujarati_movie_recycler,english_movie_recycler,trending_d3_recycler
            ,trending_k4_recycler,d3_recycler,k4_recycler;
    private LinearLayout discover_ll_moviep,discover_ll_webseriesp,discover_ll_comicsp
            ,discover_ll_movie_hindi,discover_ll_movie_gujarati,discover_ll_movie_english
            ,discover_ll_movie_trend,discover_ll_webseries_trend
            ,discover_ll_3d_trend,discover_ll_4k_trend,discover_ll_movie_popular
            ,discover_ll_weseries_popular,discover_ll_3d_popular,discover_ll_4k_popular;
    ArrayList<VideoModelClass> arrayList,movie_arrayList,webseries_arrayList
            ,trend_movie_arrayList,trend_webseries_arrayList,hindi_movie_arrayList
            ,gujarati_movie_arrayList,english_movie_arrayList,d3_arrayList,k4_arrayList
            ,trend_d3_arrayList,trend_k4_arrayList;
    private CollectionAdapter adapter1;
    private AdView adView1,adView2,adView3,adView4,adView5,adView6,adView7,adView8,adView9,adView10,adView11,adView12;
    private ArrayList<CardViewModelClass> arrayListcard;
    private SliderLayout sliderLayout;
    private RewardedVideoAd mRewardedVideoAdD;
    private Banner ad_banner3,ad_banner4,ad_banner5,ad_banner6,ad_banner7,ad_banner8,ad_banner9
            ,ad_banner10,ad_banner11,ad_banner12,ad_banner13,ad_banner14;

    public DiscoverFragment(){

    }

    @SuppressLint("ValidFragment")
    public DiscoverFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discover_fragment,container,false);
        Log.e("Discover","Opened");

        initView(view);
        onClickListner();


        mRewardedVideoAdD = MobileAds.getRewardedVideoAdInstance(mContext);
        //mRewardedVideoAdD.setRewardedVideoAdListener(mContext);
        mRewardedVideoAdD.loadAd(getString(R.string.adUnitVideo),
                new AdRequest.Builder().build());

        callHandlerForAdShow();

        String response = getActivity().getIntent().getStringExtra("response");

        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(3); //set scroll delay in seconds :
        setSliderViews();

        //callCardViewImageAPI();

        callAPI(response);
        callHindiMovieAPI();
        callGujaratiMovieAPI();
        callEnglishMovieAPI();

        return view;
    }

    private void onClickListner() {
        discover_ll_movie_hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DivertFromDiscoverActivity.class);
                intent.putExtra("LABEL","Hindi_Movie");
                startActivity(intent);
            }
        });

        discover_ll_movie_gujarati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DivertFromDiscoverActivity.class);
                intent.putExtra("LABEL","Gujarati_Movie");
                startActivity(intent);
            }
        });
        discover_ll_movie_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DivertFromDiscoverActivity.class);
                intent.putExtra("LABEL","English_Movie");
                startActivity(intent);
            }
        });
        discover_ll_movie_trend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DivertFromDiscoverActivity.class);
                intent.putExtra("LABEL","Trend_Movie");
                startActivity(intent);
            }
        });
        discover_ll_webseries_trend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DivertFromDiscoverActivity.class);
                intent.putExtra("LABEL","Trend_WebSeries");
                startActivity(intent);
            }
        });
        discover_ll_3d_trend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DivertFromDiscoverActivity.class);
                intent.putExtra("LABEL","Trend_3D");
                startActivity(intent);
            }
        });
        discover_ll_4k_trend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DivertFromDiscoverActivity.class);
                intent.putExtra("LABEL","Trend_4K");
                startActivity(intent);
            }
        });
        discover_ll_movie_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DivertFromDiscoverActivity.class);
                intent.putExtra("LABEL","Movie_Popular");
                startActivity(intent);
            }
        });
        discover_ll_weseries_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DivertFromDiscoverActivity.class);
                intent.putExtra("LABEL","WebSeries_Popular");
                startActivity(intent);
            }
        });
        discover_ll_3d_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DivertFromDiscoverActivity.class);
                intent.putExtra("LABEL","3D_Popular");
                startActivity(intent);
            }
        });
        discover_ll_4k_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DivertFromDiscoverActivity.class);
                intent.putExtra("LABEL","4K_Popular");
                startActivity(intent);
            }
        });
    }

    private void callEnglishMovieAPI() {
            //AppConstatnt.showProgressDialog(mContext);
            RequestQueue requestQueue=Volley.newRequestQueue(mContext);
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,AppConstatnt.movie_language_api+"English", null,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    AppConstatnt.dismissProgressDialog();
                    Log.e("Respone ", response.toString());

                    try {

                        //JSONObject jsonObject = response.getJSONObject("data");
                        JSONArray  jsonArray=response.getJSONArray("data");
                        Log.e("DATA ENGLISH",jsonArray.toString());

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String cat_name = object.getString("cat_name");
                            String title = object.getString("title");
                            String sub_title =object.getString("sub_title");
                            int view = object.getInt("view");
                            String url = object.getString("url");
                            String thumb = object.getString("thumb");

                            //set list item...
                            VideoModelClass v=new VideoModelClass();
                            v.setThumb(thumb);
                            v.setTitle(title);
                            v.setId(id);
                            v.setView(view);
                            v.setUrl(url);
                            v.setSub_title(sub_title);
                            v.setCat_name(cat_name);


                            english_movie_arrayList.add(v);

                            Log.e("ID:URL", id + ":" + url);

                        }


                        //set Adapters...
                        //AppConstatnt.dismissProgressDialog();
                        setEnglishMovieAdapter();


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

    private void setEnglishMovieAdapter() {
        english_movie_recycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        MovieAdpater movieAdpater = new MovieAdpater(mContext,english_movie_arrayList);
        english_movie_recycler.setAdapter(movieAdpater);
        //AppConstatnt.dismissProgressDialog();
    }

    private void callGujaratiMovieAPI() {
        //AppConstatnt.showProgressDialog(mContext);
        RequestQueue requestQueue=Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,AppConstatnt.movie_language_api+"Gujarati", null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                AppConstatnt.dismissProgressDialog();
                Log.e("Respone ", response.toString());

                try {

                    //JSONObject jsonObject = response.getJSONObject("data");
                    JSONArray  jsonArray=response.getJSONArray("data");
                    Log.e("DATA GUJARATI",jsonArray.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String cat_name = object.getString("cat_name");
                        String title = object.getString("title");
                        String sub_title =object.getString("sub_title");
                        int view = object.getInt("view");
                        String url = object.getString("url");
                        String thumb = object.getString("thumb");

                        //set list item...
                        VideoModelClass v=new VideoModelClass();
                        v.setThumb(thumb);
                        v.setTitle(title);
                        v.setId(id);
                        v.setView(view);
                        v.setUrl(url);
                        v.setSub_title(sub_title);
                        v.setCat_name(cat_name);


                        gujarati_movie_arrayList.add(v);

                        Log.e("ID:URL", id + ":" + url);

                    }


                    //set Adapters...
                    //AppConstatnt.dismissProgressDialog();
                    setGujaratiMovieAdapter();


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

    private void setGujaratiMovieAdapter() {
        gujarati_movie_recycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        MovieAdpater movieAdpater = new MovieAdpater(mContext,gujarati_movie_arrayList);
        gujarati_movie_recycler.setAdapter(movieAdpater);
        //AppConstatnt.dismissProgressDialog();
    }

    private void callHindiMovieAPI() {
        //AppConstatnt.showProgressDialog(mContext);
        RequestQueue requestQueue=Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,AppConstatnt.movie_language_api+"Hindi", null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                AppConstatnt.dismissProgressDialog();
                Log.e("Respone ", response.toString());

                try {

                    //JSONObject jsonObject = response.getJSONObject("data");
                    JSONArray  jsonArray=response.getJSONArray("data");
                    Log.e("DATA HINDI",jsonArray.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String cat_name = object.getString("cat_name");
                        String title = object.getString("title");
                        String sub_title =object.getString("sub_title");
                        int view = object.getInt("view");
                        String url = object.getString("url");
                        String thumb = object.getString("thumb");

                        //set list item...
                        VideoModelClass v=new VideoModelClass();
                        v.setThumb(thumb);
                        v.setTitle(title);
                        v.setId(id);
                        v.setView(view);
                        v.setUrl(url);
                        v.setSub_title(sub_title);
                        v.setCat_name(cat_name);


                        hindi_movie_arrayList.add(v);

                        Log.e("ID:URL", id + ":" + url);

                    }


                    //set Adapters...
                    //AppConstatnt.dismissProgressDialog();
                    setHindiMovieAdapter();


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


    private void setHindiMovieAdapter() {
        hindi_movie_recycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        MovieAdpater movieAdpater = new MovieAdpater(mContext,hindi_movie_arrayList);
        hindi_movie_recycler.setAdapter(movieAdpater);
        //AppConstatnt.dismissProgressDialog();
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


    private void setSliderViews() {
        RequestQueue requestQueue=Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,AppConstatnt.cardview_image_api, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //AppConstatnt.dismissProgressDialog();
                Log.e("Respone ", response.toString());

                try {

                    JSONArray jsonArray = response.getJSONArray("data");


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String url = object.getString("url");

                        Log.e("CARDVIEW ----- ID:URL", id + ":" + url);

                        DefaultSliderView sliderView = new DefaultSliderView(mContext);

                        sliderView.setImageUrl(url);

                        sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                        /*sliderView.setDescription("The quick brown fox jumps over the lazy dog.\n" +
                                "Jackdaws love my big sphinx of quartz. " + (i + 1));*/
                        final int finalI = i;

                        sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                            @Override
                            public void onSliderClick(SliderView sliderView) {
                                //Toast.makeText(mContext, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();

                            }
                        });

                        //at last add this view in your layout :
                        sliderLayout.addSliderView(sliderView);

                    }
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

    private void callCardViewImageAPI() {
        //AppConstatnt.showProgressDialog(mContext);
        RequestQueue requestQueue=Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,AppConstatnt.cardview_image_api, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //AppConstatnt.dismissProgressDialog();
                Log.e("Respone ", response.toString());

                try {

                    JSONArray jsonArray = response.getJSONArray("data");


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String url = object.getString("url");

                        //set list item...
                        CardViewModelClass c = new CardViewModelClass();
                        c.setId(id);
                        c.setUrl(url);

                        arrayListcard.add(c);

                        Log.e("CARDVIEW ----- ID:URL", id + ":" + url);

                    }

                    setCardAdapter();

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

    private void setCardAdapter() {
        card_recycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        CardAdapter adapter= new CardAdapter(mContext,arrayListcard);
        card_recycler.setAdapter(adapter);
        //adapter1 = new CollectionAdapter(mContext);
    }

    private void initView(View view) {
        //card_recycler = (RecyclerView)view.findViewById(R.id.card_recycler);
        arrayListcard= new ArrayList<CardViewModelClass>();
        movie_recycler=(RecyclerView)view.findViewById(R.id.movie_recycler);
        webseries_recycler=(RecyclerView)view.findViewById(R.id.webseries_recycler);
        trending_movie_recycler=(RecyclerView)view.findViewById(R.id.trending_movie_recycler);
        trending_webseries_recycler=(RecyclerView)view.findViewById(R.id.trending_webseries_recycler);
        trending_d3_recycler=(RecyclerView)view.findViewById(R.id.trending_d3_recycler);
        trending_k4_recycler=(RecyclerView)view.findViewById(R.id.trending_k4_recycler);
        d3_recycler=(RecyclerView)view.findViewById(R.id.d3_recycler);
        k4_recycler=(RecyclerView)view.findViewById(R.id.k4_recycler);
        hindi_movie_recycler=(RecyclerView)view.findViewById(R.id.hindi_movie_recycler);
        gujarati_movie_recycler=(RecyclerView)view.findViewById(R.id.gujarati_movie_recycler);
        english_movie_recycler=(RecyclerView)view.findViewById(R.id.english_movie_recycler);
        discover_ll_moviep=(LinearLayout)view.findViewById(R.id.discover_ll_moviep);
        discover_ll_webseriesp=(LinearLayout)view.findViewById(R.id.discover_ll_webseriesp);
        discover_ll_comicsp=(LinearLayout)view.findViewById(R.id.discover_ll_comicsp);
        sliderLayout=(SliderLayout)view.findViewById(R.id.sliderLayout);
        arrayList=new ArrayList<VideoModelClass>();
        movie_arrayList=new ArrayList<VideoModelClass>();
        d3_arrayList=new ArrayList<VideoModelClass>();
        k4_arrayList=new ArrayList<VideoModelClass>();
        trend_d3_arrayList=new ArrayList<VideoModelClass>();
        trend_k4_arrayList=new ArrayList<VideoModelClass>();
        webseries_arrayList=new ArrayList<VideoModelClass>();
        trend_movie_arrayList=new ArrayList<VideoModelClass>();
        trend_webseries_arrayList=new ArrayList<VideoModelClass>();
        hindi_movie_arrayList=new ArrayList<VideoModelClass>();
        gujarati_movie_arrayList=new ArrayList<VideoModelClass>();
        english_movie_arrayList=new ArrayList<VideoModelClass>();

        discover_ll_movie_hindi=(LinearLayout)view.findViewById(R.id.discover_ll_movie_hindi);
        discover_ll_movie_gujarati=(LinearLayout)view.findViewById(R.id.discover_ll_movie_gujarati);
        discover_ll_movie_english=(LinearLayout)view.findViewById(R.id.discover_ll_movie_english);
        discover_ll_movie_trend=(LinearLayout)view.findViewById(R.id.discover_ll_movie_trend);
        discover_ll_webseries_trend=(LinearLayout)view.findViewById(R.id.discover_ll_webseries_trend);
        discover_ll_3d_trend=(LinearLayout)view.findViewById(R.id.discover_ll_3d_trend);
        discover_ll_4k_trend=(LinearLayout)view.findViewById(R.id.discover_ll_4k_trend);
        discover_ll_movie_popular=(LinearLayout)view.findViewById(R.id.discover_ll_movie_popular);
        discover_ll_weseries_popular=(LinearLayout)view.findViewById(R.id.discover_ll_weseries_popular);
        discover_ll_3d_popular=(LinearLayout)view.findViewById(R.id.discover_ll_3d_popular);
        discover_ll_4k_popular=(LinearLayout)view.findViewById(R.id.discover_ll_4k_popular);

        ad_banner3=(Banner)view.findViewById(R.id.ad_banner3);
        ad_banner4=(Banner)view.findViewById(R.id.ad_banner4);
        ad_banner5=(Banner)view.findViewById(R.id.ad_banner5);
        ad_banner6=(Banner)view.findViewById(R.id.ad_banner6);
        ad_banner7=(Banner)view.findViewById(R.id.ad_banner7);
        ad_banner8=(Banner)view.findViewById(R.id.ad_banner8);
        ad_banner9=(Banner)view.findViewById(R.id.ad_banner9);
        ad_banner10=(Banner)view.findViewById(R.id.ad_banner10);
        ad_banner11=(Banner)view.findViewById(R.id.ad_banner11);
        ad_banner12=(Banner)view.findViewById(R.id.ad_banner12);
        ad_banner13=(Banner)view.findViewById(R.id.ad_banner13);
        ad_banner14=(Banner)view.findViewById(R.id.ad_banner14);


        /*AdView adView = new AdView(mContext);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(getString(R.string.adUnitBanner));

        adView1 =(AdView)view.findViewById(R.id.adView1);
        adView2 =(AdView)view.findViewById(R.id.adView2);
        adView3 =(AdView)view.findViewById(R.id.adView3);
        adView4 =(AdView)view.findViewById(R.id.adView4);
        adView5 =(AdView)view.findViewById(R.id.adView5);
        adView6 =(AdView)view.findViewById(R.id.adView6);
        adView7 =(AdView)view.findViewById(R.id.adView7);
        adView8 =(AdView)view.findViewById(R.id.adView8);
        adView9 =(AdView)view.findViewById(R.id.adView9);
        adView10 =(AdView)view.findViewById(R.id.adView10);
        adView11 =(AdView)view.findViewById(R.id.adView11);
        adView12 =(AdView)view.findViewById(R.id.adView12);
        MobileAds.initialize(mContext, getString(R.string.appID));
        //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView1.loadAd(adRequest);
        adView2.loadAd(adRequest);
        adView3.loadAd(adRequest);
        adView4.loadAd(adRequest);
        adView5.loadAd(adRequest);
        adView6.loadAd(adRequest);
        adView7.loadAd(adRequest);
        adView8.loadAd(adRequest);
        adView9.loadAd(adRequest);
        adView10.loadAd(adRequest);
        adView11.loadAd(adRequest);
        adView12.loadAd(adRequest);*/


    }

    private void callAPI(String response) {
        AppConstatnt.showProgressDialog(mContext);
        RequestQueue requestQueue=Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,AppConstatnt.all_response_api, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                AppConstatnt.dismissProgressDialog();
                Log.e("Respone ", response.toString());

                try {

                    JSONObject jsonObject = response.getJSONObject("data");
                    JSONArray  jsonArray=jsonObject.getJSONArray("popular");
                    JSONArray jsonArray1=jsonObject.getJSONArray("trending");
                    Log.e("PopularJasonArray",jsonArray.toString());
                    Log.e("TrendingJasonArray",jsonArray1.toString());


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String cat_name = object.getString("cat_name");
                        String title = object.getString("title");
                        String sub_title =object.getString("sub_title");
                        int view = object.getInt("view");
                        String url = object.getString("url");
                        String thumb = object.getString("thumb");

                        //set list item...
                        VideoModelClass v=new VideoModelClass();
                        v.setThumb(thumb);
                        v.setTitle(title);
                        v.setId(id);
                        v.setView(view);
                        v.setUrl(url);
                        v.setSub_title(sub_title);
                        v.setCat_name(cat_name);


                        if(cat_name.equals("Movies")){
                            movie_arrayList.add(v);
                        }
                        else if(cat_name.equals("Web Series")){
                            webseries_arrayList.add(v);
                        }
                        else if(cat_name.equals("k4_video")){
                            k4_arrayList.add(v);
                        }
                        else if(cat_name.equals("d3_video")){
                            d3_arrayList.add(v);
                        }

                        //arrayList.add(v);

                        Log.e("ID:URL", id + ":" + url);

                    }

                    for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject object = jsonArray1.getJSONObject(i);
                        String id = object.getString("id");
                        String cat_name = object.getString("cat_name");
                        String title = object.getString("title");
                        String sub_title =object.getString("sub_title");
                        int view = object.getInt("view");
                        String url = object.getString("url");
                        String thumb = object.getString("thumb");

                        //set list item...
                        VideoModelClass v=new VideoModelClass();
                        v.setThumb(thumb);
                        v.setTitle(title);
                        v.setId(id);
                        v.setView(view);
                        v.setUrl(url);
                        v.setSub_title(sub_title);
                        v.setCat_name(cat_name);

                        if(cat_name.equals("Movies")){
                            trend_movie_arrayList.add(v);
                        }
                        else if(cat_name.equals("Web Series")){
                            trend_webseries_arrayList.add(v);
                        }
                        else if(cat_name.equals("k4_video")){
                            trend_k4_arrayList.add(v);

                        }
                        else if(cat_name.equals("d3_video")){
                            trend_d3_arrayList.add(v);

                        }

                        Log.e("ID:URL", id + ":" + url);

                    }

                    //set Adapters...
                    AppConstatnt.dismissProgressDialog();
                    setPopularMovieAdapter();
                    setPopularWebSeriesAdpater();
                    setPopularD3Adapter();
                    setPopularK4Adapter();
                    //setWebSeriesAdapter();
                    //setComicsAdapter();
                    setTrendMovieAdapter();
                    setTrendWebSeriesAdpater();
                    setTrendD3Adapter();
                    setTrendK4Adapter();

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

    private void setTrendK4Adapter() {
        trending_k4_recycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        ComicsAdapter comicsAdapter = new ComicsAdapter(mContext,trend_k4_arrayList);
        trending_k4_recycler.setAdapter(comicsAdapter);
        //AppConstatnt.dismissProgressDialog();
    }

    private void setPopularK4Adapter() {
        k4_recycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        ComicsAdapter comicsAdapter = new ComicsAdapter(mContext,k4_arrayList);
        k4_recycler.setAdapter(comicsAdapter);
        //AppConstatnt.dismissProgressDialog();
    }

    private void setPopularD3Adapter() {
        d3_recycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        ComicsAdapter comicsAdapter = new ComicsAdapter(mContext,d3_arrayList);
        d3_recycler.setAdapter(comicsAdapter);
        //AppConstatnt.dismissProgressDialog();
    }

    private void setPopularWebSeriesAdpater() {
        webseries_recycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        WebSeriesAdapter webSeriesAdapter = new WebSeriesAdapter(mContext,webseries_arrayList);
        webseries_recycler.setAdapter(webSeriesAdapter);
        //AppConstatnt.dismissProgressDialog();
    }

    private void setPopularMovieAdapter() {
        movie_recycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        MovieAdpater movieAdpater = new MovieAdpater(mContext,movie_arrayList);
        movie_recycler.setAdapter(movieAdpater);
        //AppConstatnt.dismissProgressDialog();
    }

    private void setTrendD3Adapter() {
        trending_d3_recycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        ComicsAdapter comicsAdapter = new ComicsAdapter(mContext,trend_d3_arrayList);
        trending_d3_recycler.setAdapter(comicsAdapter);
        //AppConstatnt.dismissProgressDialog();
    }

    private void setTrendWebSeriesAdpater() {
        trending_webseries_recycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        WebSeriesAdapter webSeriesAdapter = new WebSeriesAdapter(mContext,trend_webseries_arrayList);
        trending_webseries_recycler.setAdapter(webSeriesAdapter);
        AppConstatnt.dismissProgressDialog();
    }

    private void setTrendMovieAdapter() {
        trending_movie_recycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        MovieAdpater movieAdpater = new MovieAdpater(mContext,trend_movie_arrayList);
        trending_movie_recycler.setAdapter(movieAdpater);
        //AppConstatnt.dismissProgressDialog();
    }

}

