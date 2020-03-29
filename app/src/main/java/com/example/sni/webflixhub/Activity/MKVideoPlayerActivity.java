package com.example.sni.webflixhub.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.sni.webflixhub.AppConstant.AppConstatnt;
import com.example.sni.webflixhub.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.khizar1556.mkvideoplayer.MKPlayer;

import java.util.Timer;
import java.util.TimerTask;


public class MKVideoPlayerActivity extends AppCompatActivity implements RewardedVideoAdListener {

    private MKPlayer mkPlayer;
    private String url, d3, k4;
    private RewardedVideoAd mRewardedVideoAd;
    private WebView webView;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mkvideo_player);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        webView = (WebView) findViewById(R.id.webview);

        url = getIntent().getStringExtra("URL");
        /*d3 = getIntent().getStringExtra("3D");
        k4 = getIntent().getStringExtra("4K");*/


        MobileAds.initialize(this, getString(R.string.appID));
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        //mkPlayerShow();

//        webView.setWebViewClient(new MyBrowser());
//        webView.getSettings().setLoadsImagesAutomatically(true);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new MyBrowser());
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setEnabled(true);
                webView.reload();
            }
        });



/*
        if(mkPlayer.isPlaying()){
            callHandlerForAdShow();
        }*/
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(getString(R.string.adUnitVideo),
                new AdRequest.Builder().build());
    }


    private void mkPlayerShow() {
        mkPlayer = new MKPlayer(MKVideoPlayerActivity.this);
        mkPlayer.play(url);

        mkPlayer.setPlayerCallbacks(new MKPlayer.playerCallbacks() {
            @Override
            public void onNextClick() {
                //It is the method for next song.It is called when you pressed the next icon
                //Do according to your requirement
            }

            @Override
            public void onPreviousClick() {
                //It is the method for previous song.It is called when you pressed the previous icon
                //Do according to your requirement
            }
        });

    }


    @Override
    protected void onPause() {
        if (mkPlayer != null) {
            mkPlayer.onPause();
            if (mRewardedVideoAd.isLoaded()) {
                mRewardedVideoAd.show();
                Log.e("TAG", "The videoAd when PAUSE.");
            } else {
                Log.e("TAG", "The videoAd wasn't loaded yet ::PAUSE.");
            }
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mkPlayer != null) {
            callHandlerForAdShow();

            /*if (mRewardedVideoAd.isLoaded()) {
                mRewardedVideoAd.show();
                Log.e("TAG", "The videoAd when RESUME.");
            } else {
                mkPlayer.onResume();
                Log.e("TAG", "The videoAd wasn't loaded yet ::RESUME.");
            }*/
        }
        super.onResume();
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
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                } else {
                    mkPlayer.onResume();
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
        }, 0, 1000 * 60 * 16);
    }

    @Override
    protected void onDestroy() {
        if (mkPlayer != null) {
            mkPlayer.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mkPlayer != null) {
            Log.e("MKVideoPlayer", "onConfigurationChanged: ");

            mkPlayer.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (mkPlayer != null && mkPlayer.onBackPressed()) {
            finish();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {
        mkPlayer.pause();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
        mkPlayer.onResume();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            swipeRefreshLayout.setEnabled(false);
        }
    }

}
