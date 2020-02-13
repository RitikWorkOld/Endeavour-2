package com.ritik.ecell;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ritik.ecell.Events_Fragments.EventsMain;
import com.ritik.ecell.Shedule.Shedule;
import com.ritik.ecell.Speakers.Speakers;
import com.ritik.ecell.Sponsors.Sponsor;

import com.ritik.ecell.Team.TeamMain;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;


public class BottomSheetNavigationFragment extends BottomSheetDialogFragment {


    public static BottomSheetNavigationFragment newInstance() {

        Bundle args = new Bundle();

        BottomSheetNavigationFragment fragment = new BottomSheetNavigationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //Bottom Sheet Callback
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            //check the slide offset and change the visibility of close button
            if (slideOffset > 0.5) {
                closeButton.setVisibility(View.VISIBLE);
            } else {
                closeButton.setVisibility(View.GONE);
            }
        }
    };

    private ImageView closeButton;

        //TextView one;
    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        //Get the content View
        View contentView = View.inflate(getContext(), R.layout.bottom_navigation_drawer, null);
        dialog.setContentView(contentView);

        final TextView name  = (TextView)contentView.findViewById(R.id.tv_name_models);
        final TextView phno = (TextView)contentView.findViewById(R.id.user_phno);
        final TextView referid = (TextView)contentView.findViewById(R.id.user_refer_id);
        final String TAG = "********************";

        NavigationView navigationView = contentView.findViewById(R.id.navigation_view);

        closeButton = (ImageView) contentView.findViewById(R.id.close_image_view);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.keepSynced(true);
        databaseReference.orderByChild("uid").equalTo(FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Bnd_helper bnd_helper = dataSnapshot1.getValue(Bnd_helper.class);

                    String n = bnd_helper.name;
                    String p = bnd_helper.contactn;
                    String r = bnd_helper.refrelid;

                    Log.d(TAG,"---------------------------------"+n+"-----------"+p+"----------"+r);

                    name.setText(n);
                    phno.setText(p);
                    referid.setText(r);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //implement navigation menu item click event
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav01:
                        Intent intent=new Intent(getActivity(),EventsMain.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        dismiss();
                        startActivity( intent );
                        break;
                    case R.id.nav02:
                        Intent intent2=new Intent(getActivity(),Shedule.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        dismiss();
                        startActivity( intent2 );

                        break;
                    case R.id.nav03:
                        Intent intent3=new Intent(getActivity(),Speakers.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        dismiss();
                        startActivity( intent3 );

                        break;
                    case R.id.nav04:
                        Intent intent4=new Intent(getActivity(),Sponsor.class);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        dismiss();
                        startActivity( intent4 );

                        break;

                    case R.id.nav05:
                        Intent intent5=new Intent(getActivity(), TeamMain.class);
                        intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        dismiss();
                        startActivity( intent5 );

                        break;
                    case R.id.nav06:
                        Intent intent6=new Intent(getActivity(),FAQ.class);
                        intent6.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        dismiss();
                        startActivity( intent6 );

                        break;


                }
                return false;
            }
        });


        //Set the coordinator layout behavior
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        //Set callback
        if (behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }

}