package com.example.sni.webflixhub.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sni.webflixhub.Activity.VideoPlayActivity;
import com.example.sni.webflixhub.Activity.WebSeriesSeasonActivity;
import com.example.sni.webflixhub.Class.VideoModelClass;
import com.example.sni.webflixhub.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class WebSeriesAdapter extends RecyclerView.Adapter<WebSeriesAdapter.Holder> {
    private final Context mContext;
    private final LayoutInflater inflater;
    private final ArrayList<VideoModelClass> arrayList;

    public WebSeriesAdapter(Context mContext, ArrayList<VideoModelClass> webseries_arrayList) {
        this.mContext = mContext;
        this.arrayList = webseries_arrayList;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public WebSeriesAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.model_movie, viewGroup, false);


        Holder holder=new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WebSeriesAdapter.Holder holder, final int i) {
        Glide.with(mContext).load(arrayList.get(i).getThumb()).into(holder.iv_videopic_model_video);
        holder.txt_videotitle_model_video.setText(arrayList.get(i).getTitle());
        //holder.txt_cat_name_model_video.setText(arrayList.get(position).getCat_name());

        holder.iv_videopic_model_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,WebSeriesSeasonActivity.class);
                intent.putExtra("id",arrayList.get(i).getId());
                intent.putExtra("cat_name",arrayList.get(i).getCat_name());
                //intent.putExtra("In_search","no");
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        FrameLayout frame_model_movie;
        RoundedImageView iv_videopic_model_video;
        TextView txt_videotitle_model_video;
        //TextView txt_cat_name_model_video;

        public Holder(@NonNull View itemView) {
            super(itemView);
            frame_model_movie = (FrameLayout)itemView.findViewById(R.id.frame_model_movie);
            iv_videopic_model_video = (RoundedImageView)itemView.findViewById(R.id.iv_videopic_model_video);
            txt_videotitle_model_video=(TextView)itemView.findViewById(R.id.txt_videotitle_model_video);
            //txt_cat_name_model_video=(TextView)itemView.findViewById(R.id.txt_cat_name_model_video);
        }
    }
}
