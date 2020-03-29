package com.example.sni.webflixhub.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.sni.webflixhub.R;
import com.startapp.android.publish.ads.banner.Banner;
import com.startapp.android.publish.adsCommon.StartAppSDK;

public class FirstFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {


    private Context mContext;
    private FragmentManager supportFragmentManager;
    private Fragment fragment;
    private String TAG = "firstFragment";

    public FirstFragment() {

    }

    @SuppressLint("ValidFragment")
    public FirstFragment(FragmentManager supportFragmentManager, Context mContext) {
        this.supportFragmentManager = supportFragmentManager;
        this.mContext = mContext;
        Log.e(TAG, "firstFragment: " + supportFragmentManager);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StartAppSDK.init(mContext,getString(R.string.StartAppadID),true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        BottomNavigationView navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.Discover);
        return view;
    }

    private void displaySelectedScreen(int id) {
        Log.e(TAG, "displaySelectedScreen: ");
        fragment = null;
        Log.e(TAG, "displaySelectedScreen: " + id);
        switch (id) {
            case R.id.Discover:
                Log.e(TAG, "displaySelectedScreen: " + R.id.Discover);
                fragment = new DiscoverFragment(mContext);
                break;
            case R.id.movies:
                Log.e(TAG, "displaySelectedScreen: " + R.id.movies);
                fragment = new MovieFragment(mContext);
                break;
            case R.id.webseries:
                Log.e(TAG, "displaySelectedScreen: " + R.id.webseries);
                fragment = new WebSeriesFragment(mContext);
                break;
            case R.id.d3:
                Log.e(TAG, "displaySelectedScreen: " + R.id.d3);
                fragment = new D3Fragment(mContext);
                break;
            case R.id.k4:
                Log.e(TAG, "displaySelectedScreen: " + R.id.k4);
                fragment = new K4Fragment(mContext);
                break;
        }

        if (fragment != null) {
            Log.e(TAG, "displaySelectedScreen: "+supportFragmentManager);
            FragmentTransaction ft = supportFragmentManager.beginTransaction();
            ft.replace(R.id.FrameContainer, fragment);
            ft.commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.FrameContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}

