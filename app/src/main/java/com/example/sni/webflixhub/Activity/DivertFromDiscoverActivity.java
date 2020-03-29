package com.example.sni.webflixhub.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.sni.webflixhub.Adapter.ComicsAdapter;
import com.example.sni.webflixhub.Adapter.MovieAdpater;
import com.example.sni.webflixhub.Adapter.WebSeriesAdapter;
import com.example.sni.webflixhub.AppConstant.AppConstatnt;
import com.example.sni.webflixhub.Class.VideoModelClass;
import com.example.sni.webflixhub.Class.WebSeriesSeasonModelClass;
import com.example.sni.webflixhub.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DivertFromDiscoverActivity extends AppCompatActivity {

    Context mContext;
    String label;
    RecyclerView rv_dfda;
    ArrayList<VideoModelClass> arrayList,movie_arrayList,webseries_arrayList
            ,trend_movie_arrayList,trend_webseries_arrayList,d3_arrayList,k4_arrayList
            ,trend_d3_arrayList,trend_k4_arrayList,english_movie_arrayList,gujarati_movie_arrayList
            ,hindi_movie_arrayList;
    ImageView iv_back_toolbar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divert_from_discover);

        label=getIntent().getStringExtra("LABEL");

        initView();
        callAPI();
    }

    private void callAPI() {
        AppConstatnt.showProgressDialog(mContext);
        RequestQueue requestQueue=Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,AppConstatnt.all_response_api, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                AppConstatnt.dismissProgressDialog();
                Log.e("Respone ", response.toString());

                try {

                    JSONObject jsonObject = response.getJSONObject("data");
                    JSONArray jsonArray=jsonObject.getJSONArray("popular");
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
                    if (label.equals("Hindi_Movie")){
                        callHindiMovieAPI();
                    }
                    if (label.equals("Gujarati_Movie")){
                        callGujaratiMovieAPI();
                    }
                    if (label.equals("English_Movie")){
                        callEnglishMovieAPI();
                    }
                    if (label.equals("Trend_Movie")){
                        setTrendMovieAdapter();
                    }
                    if (label.equals("Trend_WebSeries")){
                        setTrendWebSeriesAdpater();
                    }
                    if (label.equals("Trend_3D")){
                        setTrendD3Adapter();
                    }
                    if (label.equals("Trend_4K")){
                        setTrendK4Adapter();
                    }
                    if (label.equals("Movie_Popular")){

                    }
                    if (label.equals("WebSeries_Popular")){
                        setPopularWebSeriesAdpater();
                    }
                    if (label.equals("3D_Popular")){
                        setPopularD3Adapter();
                    }
                    if (label.equals("4K_Popular")){
                        setPopularK4Adapter();
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            private void setTrendK4Adapter() {
                rv_dfda.setLayoutManager(new GridLayoutManager(mContext, 3));
                ComicsAdapter comicsAdapter = new ComicsAdapter(mContext,trend_k4_arrayList);
                rv_dfda.setAdapter(comicsAdapter);
            }

            private void setTrendD3Adapter() {
                rv_dfda.setLayoutManager(new GridLayoutManager(mContext, 3));
                ComicsAdapter comicsAdapter = new ComicsAdapter(mContext,trend_d3_arrayList);
                rv_dfda.setAdapter(comicsAdapter);
            }

            private void setTrendMovieAdapter() {
                rv_dfda.setLayoutManager(new GridLayoutManager(mContext, 3));
                MovieAdpater movieAdpater = new MovieAdpater(mContext,trend_movie_arrayList);
                rv_dfda.setAdapter(movieAdpater);
            }

            private void setTrendWebSeriesAdpater() {
                rv_dfda.setLayoutManager(new GridLayoutManager(mContext, 3));
                WebSeriesAdapter webSeriesAdapter = new WebSeriesAdapter(mContext,trend_webseries_arrayList);
                rv_dfda.setAdapter(webSeriesAdapter);
            }

            private void setPopularK4Adapter() {
                rv_dfda.setLayoutManager(new GridLayoutManager(mContext, 3));
                ComicsAdapter comicsAdapter = new ComicsAdapter(mContext,k4_arrayList);
                rv_dfda.setAdapter(comicsAdapter);
            }

            private void setPopularD3Adapter() {
                rv_dfda.setLayoutManager(new GridLayoutManager(mContext, 3));
                ComicsAdapter comicsAdapter = new ComicsAdapter(mContext,d3_arrayList);
                rv_dfda.setAdapter(comicsAdapter);
            }

            private void setPopularWebSeriesAdpater() {
                rv_dfda.setLayoutManager(new GridLayoutManager(mContext, 3));
                WebSeriesAdapter webSeriesAdapter = new WebSeriesAdapter(mContext,webseries_arrayList);
                rv_dfda.setAdapter(webSeriesAdapter);
            }

            private void setPopularMovieAdapter() {
                rv_dfda.setLayoutManager(new GridLayoutManager(mContext, 3));
                MovieAdpater movieAdpater = new MovieAdpater(mContext,movie_arrayList);
                rv_dfda.setAdapter(movieAdpater);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
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

                private void setHindiMovieAdapter() {
                        rv_dfda.setLayoutManager(new GridLayoutManager(mContext, 3));
                        MovieAdpater movieAdpater = new MovieAdpater(mContext,hindi_movie_arrayList);
                        rv_dfda.setAdapter(movieAdpater);
                        //AppConstatnt.dismissProgressDialog();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            requestQueue.add(jsonObjectRequest);

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

                private void setGujaratiMovieAdapter() {
                    rv_dfda.setLayoutManager(new GridLayoutManager(mContext, 3));
                    MovieAdpater movieAdpater = new MovieAdpater(mContext,gujarati_movie_arrayList);
                    rv_dfda.setAdapter(movieAdpater);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            requestQueue.add(jsonObjectRequest);

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

                private void setEnglishMovieAdapter() {
                    rv_dfda.setLayoutManager(new GridLayoutManager(mContext, 3));
                    MovieAdpater movieAdpater = new MovieAdpater(mContext,english_movie_arrayList);
                    rv_dfda.setAdapter(movieAdpater);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            requestQueue.add(jsonObjectRequest);

    }

    private void initView() {
        mContext=DivertFromDiscoverActivity.this;
        rv_dfda=(RecyclerView)findViewById(R.id.rv_dfda);
        iv_back_toolbar2=(ImageView)findViewById(R.id.iv_back_toolbar2);

        arrayList=new ArrayList<VideoModelClass>();

        hindi_movie_arrayList=new ArrayList<VideoModelClass>();
        gujarati_movie_arrayList=new ArrayList<VideoModelClass>();
        english_movie_arrayList=new ArrayList<VideoModelClass>();
        webseries_arrayList=new ArrayList<VideoModelClass>();
        movie_arrayList=new ArrayList<VideoModelClass>();
        d3_arrayList=new ArrayList<VideoModelClass>();
        k4_arrayList=new ArrayList<VideoModelClass>();
        trend_movie_arrayList=new ArrayList<VideoModelClass>();
        trend_webseries_arrayList=new ArrayList<VideoModelClass>();
        trend_d3_arrayList=new ArrayList<VideoModelClass>();
        trend_k4_arrayList=new ArrayList<VideoModelClass>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);


        iv_back_toolbar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
