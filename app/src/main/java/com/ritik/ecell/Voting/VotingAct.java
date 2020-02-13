package com.ritik.ecell.Voting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.ritik.ecell.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VotingAct extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Voting_model> arrayList;
    private FirebaseRecyclerOptions<Voting_model> options;
    private FirebaseRecyclerAdapter<Voting_model, Voting_viewholder> adapter;
    private DatabaseReference databaseReference;

    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_voting);

        recyclerView = findViewById(R.id.rv_voting);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("VotingPortal");
        databaseReference.keepSynced(true);

        arrayList = new ArrayList<Voting_model>();

        options = new FirebaseRecyclerOptions.Builder<Voting_model>().setQuery(databaseReference,Voting_model.class).build();

        adapter = new FirebaseRecyclerAdapter<Voting_model, Voting_viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final Voting_viewholder voting_viewholder, int i, @NonNull final Voting_model voting_model) {
                voting_viewholder.team_name.setText(voting_model.getTeamname());
                Picasso.get().load(voting_model.memeimg).into(voting_viewholder.memeimg);

                voting_viewholder.votestatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(VotingAct.this);
                        builder.setTitle("Voting");
                        builder.setIcon(R.drawable.endlogo);
                        builder.setMessage("Please Confirm Your Vote\nYou can not change your vote once confirmed")
                                .setCancelable(false)
                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("VoteStatus").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        databaseReference.child("votingstatus").setValue("voted");
                                        databaseReference.keepSynced(true);
                                        databaseReference.child("voteid").setValue(voting_model.getVoteid()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(VotingAct.this,"Vote Added Succesfully",Toast.LENGTH_LONG).show();
                                            }
                                        });
                                        int i = voting_model.getTotalvotes() +1;
                                        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("VotingPortal");
                                        databaseReference1.keepSynced(true);
                                        databaseReference1.child(voting_model.getVoteid()).child("totalvotes").setValue(i);
                                        onBackPressed();
                                    }
                                })
                                .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
            }

            @NonNull
            @Override
            public Voting_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new Voting_viewholder(LayoutInflater.from(VotingAct.this).inflate(R.layout.card_for_voting,parent,false));
            }
        };
        recyclerView.setAdapter(adapter);
    }
}
