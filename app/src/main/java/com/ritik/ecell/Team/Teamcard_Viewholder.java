package com.ritik.ecell.Team;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ritik.ecell.R;

public class Teamcard_Viewholder extends RecyclerView.ViewHolder {

    TextView Name;
    TextView Domain;
    TextView Desig;
    de.hdodenhof.circleimageview.CircleImageView Imguri;
    boolean expand;
    public LinearLayout expandableLayout;
    public RelativeLayout relativeLayout;
    public LinearLayout google_profile;
    public LinearLayout linkedin_profile;
    public LinearLayout facebook_profile;

    public Teamcard_Viewholder(@NonNull View itemView) {
        super(itemView);

        expand = false;
        Name = itemView.findViewById(R.id.tv_team_name);
        Domain = itemView.findViewById(R.id.tv_team_domain);
        Desig = itemView.findViewById(R.id.tv_team_desig);
        Imguri = itemView.findViewById(R.id.image_team);
        expandableLayout = itemView.findViewById(R.id.expandable_layout);
        relativeLayout = itemView.findViewById(R.id.layout_team_card);
        google_profile = itemView.findViewById(R.id.google_profile);
        linkedin_profile = itemView.findViewById(R.id.linkedin_profile);
        facebook_profile = itemView.findViewById(R.id.facebook_profile);
    }
}
