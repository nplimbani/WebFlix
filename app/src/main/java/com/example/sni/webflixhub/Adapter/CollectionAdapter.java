package com.example.sni.webflixhub.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.sni.webflixhub.Activity.VideoPlayActivity;
import com.example.sni.webflixhub.Class.VideoModelClass;
import com.example.sni.webflixhub.R;

import java.util.ArrayList;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.viewHolder>{

private final Context mContext;
private final LayoutInflater inflater;
private int[] image = new int[]{R.mipmap.avenger,R.mipmap.hunted,R.mipmap.toptenmovie};
private String TAG = "collectionAdapter";

public CollectionAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        }


    @Override
public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.model_discover,parent,false);


        return new viewHolder(view);
        }

@Override
public void onBindViewHolder(viewHolder holder, final int position) {
        holder.disImage.setImageResource(image[position]);
        holder.frame.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Intent intent = new Intent(mContext, VideoPlayActivity.class);
        Log.e(TAG, "onClick: "+image[position]);
        intent.putExtra("img",image[position]);
        mContext.startActivity(intent);
        }
        });
        }

@Override
public int getItemCount() {
        return image.length;
        }

class viewHolder extends RecyclerView.ViewHolder{
    private final ImageView disImage;
    private final FrameLayout frame;

    public viewHolder(View itemView) {
        super(itemView);
        disImage = (ImageView)itemView.findViewById(R.id.disImage);
        frame = (FrameLayout)itemView.findViewById(R.id.frame);
    }
}
}
