package com.ritik.ecell.Shedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ritik.ecell.Customised.BucketRecyclerView;
import com.ritik.ecell.Events_Fragments.EventsMain;
import com.ritik.ecell.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Feb_frag extends Fragment {

    private LinearLayout march;
    private NestedScrollView nestedScrollView;

    private BucketRecyclerView recyclerView;
    private ArrayList<Schedule_Model> arrayList;
    private FirebaseRecyclerOptions<Schedule_Model> options;
    private FirebaseRecyclerAdapter<Schedule_Model, Schedule_Viewholder> adapter;
    private DatabaseReference databaseReference;
    private View coming_soon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.feb_frag,container,false);

        march = (LinearLayout)view.findViewById(R.id.march);

        coming_soon = (View)view.findViewById(R.id.coming_soon);

        recyclerView = view.findViewById(R.id.rv_schedule_feb);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.showIfEmpty(coming_soon);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("schedule").child("feb");
        databaseReference.keepSynced(true);

        arrayList = new ArrayList<Schedule_Model>();

        options = new FirebaseRecyclerOptions.Builder<Schedule_Model>().setQuery(databaseReference,Schedule_Model.class).build();

        adapter = new FirebaseRecyclerAdapter<Schedule_Model, Schedule_Viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final Schedule_Viewholder schedule_viewholder, final int i, @NonNull Schedule_Model schedule_model) {
                schedule_viewholder.eventtitle.setText(schedule_model.getEventtitle());
                schedule_viewholder.venue.setText(schedule_model.getVenue());
                schedule_viewholder.time.setText(schedule_model.getTime());
                schedule_viewholder.description.setText(schedule_model.getDesc());
                Picasso.get().load(schedule_model.getImguri()).into(schedule_viewholder.eventimage);



                //SWIPE CODE FOR FRAGMENTS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

                final GestureDetector gesture = new GestureDetector(getActivity(),
                        new GestureDetector.SimpleOnGestureListener() {

                            @Override
                            public boolean onDown(MotionEvent e) {
                                return true;
                            }

                            @Override
                            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
                                final int SWIPE_MIN_DISTANCE = 120;
                                final int SWIPE_MAX_OFF_PATH = 250;
                                final int SWIPE_THRESHOLD_VELOCITY = 200;
                                try {
                                    if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                        return false;
                                    if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
                                    {
                                        //open right side
                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                                        Mar_frag mar_frag = new Mar_frag();

                                        fragmentTransaction.replace(R.id.container_shedule,mar_frag);
                                        fragmentTransaction.commit();

                                    } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
                                    {
                                        //open left side

                                    }
                                } catch (Exception e) {
                                    // nothing
                                }
                                return super.onFling(e1, e2, velocityX, velocityY);
                            }
                        });

                schedule_viewholder.cardView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return gesture.onTouchEvent(event);
                    }
                });

                //SWIPE CODE FOR FRAGMENTS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>





                final String type = schedule_model.getType();

                schedule_viewholder.gotoevents.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), EventsMain.class);
                        intent.putExtra("type",type);
                        startActivity(intent);
                    }
                });

                schedule_viewholder.expander.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (schedule_viewholder.expanded == false){
                            schedule_viewholder.expandable.setVisibility(View.VISIBLE);
                            schedule_viewholder.expanded= true;
                        }
                        else {
                            schedule_viewholder.expandable.setVisibility(View.GONE);
                            schedule_viewholder.expanded = false;
                        }
                    }
                });
            }

            @NonNull
            @Override
            public Schedule_Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new Schedule_Viewholder(LayoutInflater.from(view.getContext()).inflate(R.layout.layout_schedule_main,parent,false));
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

        march.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Mar_frag mar_frag = new Mar_frag();

                fragmentTransaction.replace(R.id.container_shedule,mar_frag);
                fragmentTransaction.commit();
            }
        });

        //SWIPE CODE FOR FRAGMENTS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
                            {
                                //open right side
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                                Mar_frag mar_frag = new Mar_frag();

                                fragmentTransaction.replace(R.id.container_shedule,mar_frag);
                                fragmentTransaction.commit();

                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
                            {
                                //open left side

                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });

        //SWIPE CODE FOR FRAGMENTS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


        return view;
    }
}
