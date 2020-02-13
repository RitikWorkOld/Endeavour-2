package com.ritik.ecell.Voting;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ritik.ecell.R;

public class Voting_viewholder extends RecyclerView.ViewHolder {

    public TextView team_name,votestatus;
    public ImageView memeimg,votesymbol;

    public Voting_viewholder(@NonNull View itemView) {
        super(itemView);

        memeimg = itemView.findViewById(R.id.memeimg);
        team_name = itemView.findViewById(R.id.team_name_voting);
        votestatus = itemView.findViewById(R.id.upvote);

    }
}
