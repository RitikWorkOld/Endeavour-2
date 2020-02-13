package com.ritik.ecell.Events_Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ritik.ecell.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Faq_events_fargment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Faq_model> arrayList;
    private FirebaseRecyclerOptions<Faq_model> options;
    private FirebaseRecyclerAdapter<Faq_model, Faq_viewholder> adapter;
    private DatabaseReference databaseReference;
    private ImageView close_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate( R.layout.faq_events,container,false);

        Bundle bundle = getArguments();
        String faqid = bundle.getString( "faqid" );

        close_btn = view.findViewById(R.id.close_btn);

        recyclerView = view.findViewById(R.id.rv_faq_events);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Faq").child( faqid );
        databaseReference.keepSynced(true);

        arrayList = new ArrayList<Faq_model>();

        options = new FirebaseRecyclerOptions.Builder<Faq_model>().setQuery(databaseReference,Faq_model.class).build();

        adapter = new FirebaseRecyclerAdapter<Faq_model, Faq_viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Faq_viewholder viewholder, int i, @NonNull Faq_model model) {
                viewholder.question.setText( model.getQuestion() );
                viewholder.answer.setText( model.getAnswer() );
            }

            @NonNull
            @Override
            public Faq_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new Faq_viewholder(LayoutInflater.from(view.getContext()).inflate(R.layout.faq_event_layout,parent,false));
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager.popBackStack();
            }
        });

        return view;
    }
}
