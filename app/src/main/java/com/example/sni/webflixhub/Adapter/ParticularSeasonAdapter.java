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
import com.example.sni.webflixhub.Class.SeasonEpisodeModelClass;
import com.example.sni.webflixhub.Class.VideoModelClass;
import com.example.sni.webflixhub.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class ParticularSeasonAdapter extends RecyclerView.Adapter<ParticularSeasonAdapter.Holder> {

    Context context;
    ArrayList<SeasonEpisodeModelClass> arrayList;
    LayoutInflater layoutInflater;

    public ParticularSeasonAdapter(Context mContext, ArrayList<SeasonEpisodeModelClass> arrayList) {
        this.context=mContext;
        this.arrayList=arrayList;
        layoutInflater=LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ParticularSeasonAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=layoutInflater.inflate(R.layout.model_movie,viewGroup,false);

        return new ParticularSeasonAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticularSeasonAdapter.Holder holder, final int i) {

        Glide.with(context).load(arrayList.get(i).getThumb()).into(holder.iv_videopic_model_video);
        holder.txt_videotitle_model_video.setText(arrayList.get(i).getTitle());
        //holder.txt_cat_name_model_video.setText(arrayList.get(position).getCat_name());

        holder.iv_videopic_model_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,VideoPlayActivity.class);
                intent.putExtra("id",arrayList.get(i).getId());
                intent.putExtra("s_id",arrayList.get(i).getS_id());
                intent.putExtra("v_id",arrayList.get(i).getV_id());
                intent.putExtra("cat_name","WEB SERIES");
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        FrameLayout frame_model_movie;
        RoundedImageView iv_videopic_model_video;
        TextView txt_videotitle_model_video;
        public Holder(@NonNull View itemView) {
            super(itemView);
            frame_model_movie = (FrameLayout)itemView.findViewById(R.id.frame_model_movie);
            iv_videopic_model_video = (RoundedImageView)itemView.findViewById(R.id.iv_videopic_model_video);
            txt_videotitle_model_video=(TextView)itemView.findViewById(R.id.txt_videotitle_model_video);
        }
    }
}

