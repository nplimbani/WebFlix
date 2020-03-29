package com.example.sni.webflixhub.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sni.webflixhub.R;

public class SearchFragment extends Fragment {

    private TabLayout tabLayout;

    public SearchFragment(){

    }

    @SuppressLint("ValidFragment")
    public SearchFragment(Context mContext) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment,container,false);
        tabLayout = (TabLayout)view.findViewById(R.id.tabLayout);
        TabLayout.Tab tab1 = tabLayout.newTab();
        tab1.setText("MEDIA");
        tabLayout.addTab(tab1);
        TabLayout.Tab tab2 = tabLayout.newTab();
        tab2.setText("PEOPLE");
        tabLayout.addTab(tab2);
        return view;
    }
}
