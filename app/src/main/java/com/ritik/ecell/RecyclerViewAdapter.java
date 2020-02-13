package com.ritik.ecell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

private Context mContext;
private List<User_1> mData;

    public RecyclerViewAdapter(Context mContext, List<User_1> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from( mContext );
        view = mInflater.inflate( R.layout.item_layout,parent,false );


        return new MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
holder.title.setText( mData.get( position ).getName() );
        holder.thumbnail.setImageResource( mData.get( position ).getImageId() );
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView title;
        ImageView thumbnail;


        public MyViewHolder(View itemView){
            super(itemView);
title=(TextView) itemView.findViewById( R.id.title_id );
thumbnail=(ImageView) itemView.findViewById( R.id.title_pic );
        }
    }


}
