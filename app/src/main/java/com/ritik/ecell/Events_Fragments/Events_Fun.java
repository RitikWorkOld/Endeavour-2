package com.ritik.ecell.Events_Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.ritik.ecell.Customised.BucketRecyclerView;
import com.ritik.ecell.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Events_Fun extends Fragment {

    private BucketRecyclerView recyclerView;
    private ArrayList<Events_main_model> arrayList;
    private FirebaseRecyclerOptions<Events_main_model> options;
    private FirebaseRecyclerAdapter<Events_main_model, Events_main_viewholder> adapter;
    private DatabaseReference databaseReference;
    private static FirebaseDatabase firebaseDatabase;
    private View no_new_notifications;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_events__fun,container,false);

        no_new_notifications = view.findViewById(R.id.no_new_events);

        recyclerView = view.findViewById(R.id.rv_events_fun);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.showIfEmpty(no_new_notifications);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("eventsfun");
        databaseReference.keepSynced(true);

        arrayList = new ArrayList<Events_main_model>();

        options = new FirebaseRecyclerOptions.Builder<Events_main_model>().setQuery(databaseReference,Events_main_model.class).build();

        adapter = new FirebaseRecyclerAdapter<Events_main_model, Events_main_viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final Events_main_viewholder viewholder, int i, @NonNull final Events_main_model model) {
                viewholder.Title.setText(model.getTitle());
                viewholder.Descp.setText(model.getDescp());
                Picasso.get().load(model.getMimguri()).into(viewholder.Mimguri);

                String Title = model.getTitle();
                String Descp = model.getDescp();
                String Desc1 = model.getDesc1();
                String Desc2 = model.getDesc2();
                String Mimguri = model.getMimguri();
                String Register_uri = model.getRegister_uri();
                String faqid = model.getFaqid();
                String amount = model.getAmount();

                final Bundle bundle = new Bundle();
                bundle.putString("Title",Title);
                bundle.putString("Descp",Descp);
                bundle.putString("Desc1",Desc1);
                bundle.putString("Desc2",Desc2);
                bundle.putString("Mimguri",Mimguri);
                bundle.putString("Register_uri",Register_uri);
                bundle.putString( "faqid",faqid );
                bundle.putString("amount",amount);

                viewholder.read_more_ebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        events_details events_details = new events_details();
                        events_details.setArguments(bundle);

                        fragmentTransaction.replace(R.id.events_container,events_details);
                        fragmentTransaction.addToBackStack("fun");
                        fragmentTransaction.commit();
                    }
                });
            }

            @NonNull
            @Override
            public Events_main_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new Events_main_viewholder(LayoutInflater.from(view.getContext()).inflate(R.layout.layout_event_card,parent,false));
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

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

                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
                            {
                                //open left side
                                final FragmentTransaction fragmentTransaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction1.replace(R.id.events_container,new Events_Tech());
                                fragmentTransaction1.commit();

                                RadioButton radioButton = getActivity().findViewById(R.id.radio_technical);
                                radioButton.setChecked(true);

                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
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
