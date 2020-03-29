package com.example.sni.webflixhub.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.sni.webflixhub.Class.CardViewModelClass;
import com.example.sni.webflixhub.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.Holder> {
    private final LayoutInflater inflater;
    private Context mContext;
    private ArrayList<CardViewModelClass> arrayList;

    public CardAdapter(Context mContext, ArrayList<CardViewModelClass> arrayListcard) {
        this.mContext = mContext;
        this.arrayList = arrayListcard;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.model_cardview,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Glide.with(mContext).load(arrayList.get(position).getUrl()).into(holder.roundedCard);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class Holder extends RecyclerView.ViewHolder {

        RoundedImageView roundedCard;

        public Holder(@NonNull View itemView) {
            super(itemView);
            roundedCard = (RoundedImageView)itemView.findViewById(R.id.roundedCard);
        }
    }
}
