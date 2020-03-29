package com.example.sni.webflixhub.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sni.webflixhub.Activity.VideoPlayActivity;
import com.example.sni.webflixhub.Class.SearchModelClass;
import com.example.sni.webflixhub.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Holder> {

    private final LayoutInflater inflater;
    private ArrayList<SearchModelClass> list;
    private Context mContext;

    public SearchAdapter(Context mContext, ArrayList<SearchModelClass> list){
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.model_search_view,viewGroup,false);

        Holder holder=new Holder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int i) {
        Log.e("titleTv",list.get(i).getTitle());
        Log.e("subtiitle",list.get(i).getCat_name());
        Log.e("thumb",list.get(i).getThumb());
        holder.titleTV.setText(list.get(i).getTitle());
        holder.subtitleTV.setText(list.get(i).getCat_name());
        Glide.with(mContext).load(list.get(i).getThumb()).into(holder.image);

        holder.ll_search_view_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,VideoPlayActivity.class);
                intent.putExtra("id",list.get(i).getId());
                Log.e("ID of search ",list.get(i).getId()+"" );
                intent.putExtra("cat_name",list.get(i).getCat_name());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void filter(ArrayList<SearchModelClass> newList) {
        list = new ArrayList<>();
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView subtitleTV;
         TextView titleTV;
        ImageView image;
        LinearLayout ll_search_view_main;

        public Holder(View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.image);
            titleTV = (TextView)itemView.findViewById(R.id.titleTV);
            subtitleTV = (TextView)itemView.findViewById(R.id.subtitleTV);
            ll_search_view_main = (LinearLayout) itemView.findViewById(R.id.ll_search_view_main);
        }
    }
}

