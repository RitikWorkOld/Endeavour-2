package com.ritik.ecell;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Glimpses_Viewholder extends RecyclerView.ViewHolder {

    public ImageView imageView;

    public Glimpses_Viewholder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.glimp_image);
    }
}
