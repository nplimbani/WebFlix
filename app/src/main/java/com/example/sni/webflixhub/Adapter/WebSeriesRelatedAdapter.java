package com.example.sni.webflixhub.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sni.webflixhub.Activity.VideoPlayActivity;
import com.example.sni.webflixhub.Class.ParticulaerWebSeriesRelatedModelClass;
import com.example.sni.webflixhub.Class.VideoModelClass;
import com.example.sni.webflixhub.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class WebSeriesRelatedAdapter extends RecyclerView.Adapter<WebSeriesRelatedAdapter.viewHolder> {

    private final Context mContext;
    private final LayoutInflater inflater;
    private final ArrayList<ParticulaerWebSeriesRelatedModelClass> arrayList;
    private String TAG = "MovieAdapter";

    public WebSeriesRelatedAdapter(Context mContext, ArrayList<ParticulaerWebSeriesRelatedModelClass> movie_arrayList) {
        this.mContext = mContext;
        this.arrayList = movie_arrayList;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public WebSeriesRelatedAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.model_movie, parent, false);

        viewHolder vh=new viewHolder(view);

        /*vh.frame_model_movie=(FrameLayout)view.findViewById(R.id.frame_model_movie);
        vh.iv_videopic_model_video=(RoundedImageView)view.findViewById(R.id.iv_videopic_model_video);
        //vh.txt_cat_name_model_video=(TextView)view.findViewById(R.id.txt_cat_name_model_video);
        vh.txt_videotitle_model_video=(TextView)view.findViewById(R.id.txt_videotitle_model_video);*/

        return vh;
    }

    @Override
    public void onBindViewHolder(WebSeriesRelatedAdapter.viewHolder holder, final int position) {

        Glide.with(mContext).load(arrayList.get(position).getThumb()).into(holder.iv_videopic_model_video);
        holder.txt_videotitle_model_video.setText(arrayList.get(position).getTitle());
        //holder.txt_cat_name_model_video.setText(arrayList.get(position).getCat_name());


        holder.iv_videopic_model_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,VideoPlayActivity.class);
                intent.putExtra("id",arrayList.get(position).getId());
                intent.putExtra("cat_name","WEB SERIES");
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {


        FrameLayout frame_model_movie;
        RoundedImageView iv_videopic_model_video;
        TextView txt_videotitle_model_video;
        //TextView txt_cat_name_model_video;

        public viewHolder(View itemView) {
            super(itemView);

            frame_model_movie = (FrameLayout)itemView.findViewById(R.id.frame_model_movie);
            iv_videopic_model_video = (RoundedImageView)itemView.findViewById(R.id.iv_videopic_model_video);
            txt_videotitle_model_video=(TextView)itemView.findViewById(R.id.txt_videotitle_model_video);
        }
    }
}