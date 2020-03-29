package com.example.sni.webflixhub.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
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
import com.example.sni.webflixhub.Adapter.MovieAdpater;
import com.example.sni.webflixhub.AppConstant.AppConstatnt;
import com.example.sni.webflixhub.Class.VideoModelClass;
import com.example.sni.webflixhub.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class D3Fragment extends Fragment {

    private Context mContext;
    private RecyclerView moviesRecycler;
    private ArrayList<VideoModelClass> arrayList;


    public D3Fragment() {
    }

    @SuppressLint("ValidFragment")
    public D3Fragment(Context mContext) {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movies_fragment, container, false);


        initView(view);

        callAPI();

        return view;
    }

    private void initView(View view) {
        moviesRecycler = (RecyclerView)view.findViewById(R.id.moviesRecycler);
        arrayList=new ArrayList<VideoModelClass>();
    }

    private void callAPI() {
        AppConstatnt.showProgressDialog(mContext);
        RequestQueue requestQueue=Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,AppConstatnt.d3_k4_api+"d3_video", null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                AppConstatnt.dismissProgressDialog();
                Log.e("Respone ", response.toString());

                try {

                    JSONObject jsonObject = response.getJSONObject("data");
                    JSONArray jsonArray=jsonObject.getJSONArray("popular");
                    //JSONArray jsonArray1=jsonObject.getJSONArray("trending");
                    Log.e("Status",jsonArray.toString());
                    Log.e("PopularJasonArray",jsonArray.toString());
                    Log.e("PopularJasonArray",jsonArray.toString());
                    //Log.e("TrendingJasonArray",jsonArray1.toString());


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
        moviesRecycler.setLayoutManager(new GridLayoutManager(mContext,3));
        MovieAdpater movieAdpater=new MovieAdpater(mContext,arrayList);
        moviesRecycler.setAdapter(movieAdpater);
    }
}
