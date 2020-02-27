package com.ritik.ecell.Events_Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ritik.ecell.BQuiz.bquiz_intro;
import com.ritik.ecell.R;
import com.ritik.ecell.Voting.VotingAct;
import com.ritik.ecell.Voting.Voting_helper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class events_details extends Fragment {

    TextView Title_dt;
    TextView Descp_dt;
    TextView Desc1_dt;
    TextView Desc2_dt;
    Button Register_dt,registered;
    ImageView Mimg_dt;
    TextView readless;
    Button gotoquiz;
    Button votenow;
    TextView faq;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_events_details,container,false);

        Bundle bundle = getArguments();

        final String Title = bundle.getString("Title");
        String Descp = bundle.getString("Descp");
        String Desc1 = bundle.getString("Desc1");
        String Desc2 = bundle.getString("Desc2");
        String Mimguri = bundle.getString("Mimguri");
        final String Register_uri = bundle.getString("Register_uri");
        final String faqid = bundle.getString( "faqid" );
        final String amount = bundle.getString("amount");
        readless = view.findViewById(R.id.read_less_events);

        Title_dt = view.findViewById(R.id.event_title);
        Descp_dt = view.findViewById(R.id.event_descp);
        Desc1_dt = view.findViewById(R.id.event_descp1);
        Desc2_dt = view.findViewById(R.id.event_descp2);
        Mimg_dt = view.findViewById(R.id.event_main_img1);
        Register_dt = view.findViewById(R.id.register_events);
        gotoquiz = view.findViewById( R.id.gotoquiz );
        votenow = view.findViewById(R.id.votenow);
        faq = view.findViewById( R.id.faq_btn );
        registered = view.findViewById(R.id.registered);

        Title_dt.setText(Title);
        Descp_dt.setText(Descp);
        Desc1_dt.setText(Desc1);
        Desc2_dt.setText(Desc2);
        Picasso.get().load(Mimguri).into(Mimg_dt);

        final DatabaseReference databaseReference12345 = FirebaseDatabase.getInstance().getReference().child("registrations").child(faqid);
        databaseReference12345.keepSynced(true);
        databaseReference12345.orderByChild("userid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null)
                {
                    //Toast.makeText(getActivity().getApplicationContext(), "Already Payed", Toast.LENGTH_SHORT).show();
                    Register_dt.setVisibility(View.GONE);
                    registered.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child( "BquizStatus" ).child( FirebaseAuth.getInstance().getCurrentUser().getUid() );
        databaseReference.keepSynced( true );
        databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                QuizUnlocker quizUnlocker = dataSnapshot.getValue(QuizUnlocker.class);

                //Toast.makeText( getActivity().getApplicationContext(),"status  "+quizUnlocker.getStatus().toString(),Toast.LENGTH_SHORT ).show();

                if (quizUnlocker != null){
                    if (quizUnlocker.getStatus().toString().equals( "open" ) && faqid.equals("4")){
                        gotoquiz.setVisibility( View.VISIBLE );
                        gotoquiz.setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent( getActivity(), bquiz_intro.class );
                                startActivity( intent );
                            }
                        } );
                    }
                    else {
                        gotoquiz.setVisibility( View.GONE );
                    }
                }
                else {
                    gotoquiz.setVisibility( View.GONE );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child( "VotingControl" );
        databaseReference1.keepSynced( true );
        databaseReference1.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                VotingUnlocker votingUnlocker = dataSnapshot.getValue(VotingUnlocker.class);

                if (votingUnlocker != null){
                    if (votingUnlocker.getStatus().toString().equals( "open" ) && faqid.equals("1")){
                        votenow.setVisibility( View.VISIBLE );

                        votenow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("VoteStatus").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                databaseReference.keepSynced(true);
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        Voting_helper voting_helper = dataSnapshot.getValue(Voting_helper.class);

                                        if (voting_helper != null){
                                            if (voting_helper.getVotingstatus().equals("voted")){
                                                Toast.makeText(getContext(),"You have already Voted",Toast.LENGTH_LONG).show();
                                            }
                                            else {
                                                Intent i = new Intent(getContext(), VotingAct.class);
                                                startActivity(i);
                                            }
                                        }
                                        else {
                                            Intent i = new Intent(getContext(), VotingAct.class);
                                            startActivity(i);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        });


                    }
                    else {
                        votenow.setVisibility( View.GONE );
                    }
                }
                else {
                    votenow.setVisibility( View.GONE );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        if (faqid.equals("1") || faqid.equals("2")){

            faq.setVisibility(View.GONE);

        }

        faq.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Bundle bundle = new Bundle();
                bundle.putString( "faqid",faqid);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Faq_events_fargment faq_events_fargment = new Faq_events_fargment();
                faq_events_fargment.setArguments( bundle );

                fragmentTransaction.replace(R.id.events_container,faq_events_fargment);
                fragmentTransaction.addToBackStack("faq");
                fragmentTransaction.commit();
            }
        } );

        Register_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!haveNetworkConnection()){
                    Toast.makeText(getActivity().getApplicationContext(),"NO NETWORK FOUND\nTRY AGAIN LATER", Toast.LENGTH_SHORT).show();
                }

                else {
                    if (faqid.equals("1"))
                    {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Memethon");
                        builder.setIcon(R.drawable.endlogo);
                        builder.setMessage("Registeration is closed")
                                .setCancelable(false)
                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        //------------------------------------------------------------------------------------------------
                        //Toast.makeText(getActivity().getApplicationContext(),"Succesfull",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        databaseReference2.keepSynced(true);
                        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                getphno getphno = dataSnapshot.getValue(com.ritik.ecell.Events_Fragments.getphno.class);
                                String phno = getphno.getContactn();
                                String orderid = phno.concat(faqid);
                                Random random = new Random();
                                String id = String.format("%04d", random.nextInt(10000));
                                String finalid = orderid.concat(id);
                                Intent intent = new Intent(getActivity(), EC_Teams.class);
                                intent.putExtra("orderid",finalid);
                                intent.putExtra("custid",finalid);
                                intent.putExtra("amount",amount);
                                intent.putExtra("faqid",faqid);
                                startActivity(intent);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });

        readless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });
        return view;
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
