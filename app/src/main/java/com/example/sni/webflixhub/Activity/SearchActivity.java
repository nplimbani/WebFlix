package com.example.sni.webflixhub.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sni.webflixhub.Adapter.SearchAdapter;
import com.example.sni.webflixhub.AppConstant.AppConstatnt;
import com.example.sni.webflixhub.Class.SearchModelClass;
import com.example.sni.webflixhub.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    Context mContext;
    RecyclerView rv_searchmenu;
    ArrayList<SearchModelClass> arrayList;
    EditText et_search;
    Button btn_search;
    String nameSearch,url;
    private ImageView iv_back_toolbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        callAPI();
    }

    private void callAPI() {
        AppConstatnt.showProgressDialog(mContext);
        RequestQueue requestQueue=Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,AppConstatnt.search_api, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                AppConstatnt.dismissProgressDialog();
                Log.e("Respone ", response.toString());

                try {

                    JSONArray jsonArray=response.getJSONArray("data");
                    Log.e("PopularJasonArray",jsonArray.toString());

                    arrayList=new ArrayList<SearchModelClass>();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String cat_name = object.getString("cat_name");
                        String title = object.getString("title");
                        //String sub_title =object.getString("sub_title");
                        //int view = object.getInt("view");
                        String url = object.getString("url");
                        String thumb = object.getString("thumb");

                        //set list item...
                        SearchModelClass s = new SearchModelClass();
                        s.setId(id);
                        s.setTitle(title);
                        s.setCat_name(cat_name);
                        s.setThumb(thumb);

                        arrayList.add(s);
                        Log.e("id from search all",id+"");
                        Log.e("ID:URL", id + ":" + url);

                    }

                    //set Adapters...
                    setAdapter();


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

    private void initView() {
        rv_searchmenu=(RecyclerView)findViewById(R.id.rv_searchmenu);
        //arrayList=new ArrayList<SearchModelClass>();
        mContext=SearchActivity.this;
        et_search=(EditText)findViewById(R.id.et_search);
        btn_search=(Button)findViewById(R.id.btn_search);
        arrayList = new ArrayList<SearchModelClass>();


        iv_back_toolbar1=(ImageView)findViewById(R.id.iv_back_toolbar1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);


        iv_back_toolbar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameSearch=et_search.getText().toString().trim();
                if(nameSearch.length()==0){
                    callAPI();
                }
                else{

                    url=AppConstatnt.search_api+nameSearch;
                    Log.e("new url",url);
                    CallParticularsearchApi();
                }
            }
        });

    }

    private void CallParticularsearchApi() {
        AppConstatnt.showProgressDialog(mContext);

        RequestQueue requestQueue=Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                AppConstatnt.dismissProgressDialog();
                Log.e("Particul SearchRespone", response.toString());


                try {
                    String search_status = response.getString("status");
                    if(search_status.equals("401")){
                        Toast.makeText(mContext,"Sorry !!! We couldn't find the item.",Toast.LENGTH_SHORT).show();
                    }else{

                        JSONArray jsonArray = response.getJSONArray("data");
                        Log.e("PopularJasonArray", jsonArray.toString());

                    //while item is find we should take new arraylist to store the dATA.....
                        arrayList = new ArrayList<SearchModelClass>();

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String cat_name = object.getString("cat_name");
                            String title = object.getString("title");
                            //String sub_title =object.getString("sub_title");
                            //int view = object.getInt("view");
                            String url = object.getString("url");
                            String thumb = object.getString("thumb");

                            //set list item...
                            SearchModelClass s = new SearchModelClass();
                            s.setId(id);
                            s.setTitle(title);
                            s.setCat_name(cat_name);
                            s.setThumb(thumb);

                            arrayList.add(s);

                            Log.e("ID:URL", id + ":" + url);

                        }
                        //set Adapters...
                        setAdapter();
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

    private void setAdapter() {
        rv_searchmenu.setLayoutManager(new LinearLayoutManager(mContext));
        SearchAdapter searchAdapter = new SearchAdapter(mContext,arrayList);
        rv_searchmenu.setAdapter(searchAdapter);
    }
}
