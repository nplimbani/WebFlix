package com.example.sni.webflixhub.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sni.webflixhub.Adapter.MovieAdpater;
import com.example.sni.webflixhub.Adapter.SearchAdapter;
import com.example.sni.webflixhub.AppConstant.AppConstatnt;
import com.example.sni.webflixhub.Class.SearchModelClass;
import com.example.sni.webflixhub.Fragment.FirstFragment;
import com.example.sni.webflixhub.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.startapp.android.publish.adsCommon.AutoInterstitialPreferences;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import java.util.ArrayList;
import java.util.zip.Inflater;

import br.com.mauker.materialsearchview.MaterialSearchView;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView mTextMessage;
    private String TAG = "MainActivity";
    private Context mContext;
    private Fragment mainFragment;
    private MaterialSearchView searchview;
    String feedbackR,mediarq;
    SearchAdapter searchAdapter;
    ArrayList<SearchModelClass> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StartAppSDK.init(mContext,getString(R.string.StartAppadID),false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=MainActivity.this;
        mTextMessage = (TextView)findViewById(R.id.message);
        searchview = (MaterialSearchView)findViewById(R.id.searchview);

        StartAppAd.setAutoInterstitialPreferences(
                new AutoInterstitialPreferences()
                        .setActivitiesBetweenAds(1)
        );


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.container);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedFragment(R.id.home1);

        /*StartAppAd.setAutoInterstitialPreferences(
                new AutoInterstitialPreferences()
                        .setSecondsBetweenAds(30)
        );*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(mContext,SearchActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedFragment(item.getItemId());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.container);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }


    private void displaySelectedFragment(int itemId) {
        mainFragment = null;
        switch (itemId) {
            case R.id.home1:
                Log.e(TAG, "displaySelectedFragment: "+getSupportFragmentManager() );
                mainFragment = new FirstFragment(getSupportFragmentManager(),mContext);
                break;
            case R.id.media_request:
                Log.e(TAG, "displaySelectedFragment: "+getSupportFragmentManager() );
                DIALOG111();
                break;
            case R.id.contac_us:
                Log.e(TAG, "displaySelectedFragment: "+getSupportFragmentManager() );
                Intent intent=new Intent(mContext,ContactUsActivity.class);
                startActivity(intent);
                break;
            case R.id.share_app:
                Log.e(TAG, "displaySelectedFragment: "+getSupportFragmentManager() );
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"You are invited for the app :\n"+"https://play.google.com/store/apps/details?id="+getPackageName());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,"Share app via..."));
                break;
            case R.id.feedback:
                Log.e(TAG, "displaySelectedFragment: "+getSupportFragmentManager() );
                DIALOG();
                break;
        }

        if (mainFragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, mainFragment);
            ft.commit();
        }
    }

    private void DIALOG111() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.mediarq);
        dialog.setCancelable(true);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        final EditText et_mediarq=(EditText)dialog.findViewById(R.id.et_mediarq);
        final Button btn_mediarq=(Button)dialog.findViewById(R.id.btn_mediarq);
        Log.e("media request","names");

        btn_mediarq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediarq = et_mediarq.getText().toString();
                Log.e("msg1",mediarq);

                callMediaRqAPI(mediarq);
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    private void callMediaRqAPI(String mediarq) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("msg",mediarq);

        Log.e("MEDIA REQUEST",mediarq);

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        asyncHttpClient.post(mContext,AppConstatnt.mediarq_api, requestParams, new AsyncHttpResponseHandler() {
            @SuppressLint("WrongConstant")
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.e("response ", response);


                if(statusCode==200){
                    Toast.makeText(mContext,"Request is submitted Successfully...",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mContext,"Something went wrong...!!!",Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(mContext,"Oops...! Request is not submitted...",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void DIALOG() {
        Log.e("dialog","method");
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.feedbackdialog);
        dialog.setCancelable(true);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        final EditText et_feedback=(EditText)dialog.findViewById(R.id.et_feedback);
        final Button btn_feedback=(Button)dialog.findViewById(R.id.btn_feedback);
    Log.e("feddback","feedbackstring");


        btn_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackR = et_feedback.getText().toString();
                callFeedbackAPI(feedbackR);
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    private void callFeedbackAPI(String feedback) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("msg",feedback);

        Log.e("FEEDBACK",feedback);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        asyncHttpClient.post(mContext,AppConstatnt.feedback_api, requestParams, new AsyncHttpResponseHandler() {
            @SuppressLint("WrongConstant")
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.e("response ", response);


                if(statusCode==200){
                    Toast.makeText(mContext,"Feedback is submitted Successfully...",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mContext,"Something went wrong...!!!",Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(mContext,"Oops...! Feedback is not submitted...",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (searchview.isOpen()) {
            searchview.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}
