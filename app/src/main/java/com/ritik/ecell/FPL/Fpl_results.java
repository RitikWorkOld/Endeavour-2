package com.ritik.ecell.FPL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ritik.ecell.BQuiz.Score;
import com.ritik.ecell.BQuiz.bquiz_results;
import com.ritik.ecell.Events_Fragments.EventsMain;
import com.ritik.ecell.R;

public class Fpl_results extends AppCompatActivity {

    private TextView total_ques_count,total_score_count,correct_ans_count,wrong_ans_count,skipped_ques_count;
    private Button exitquiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fpl_results);

        total_ques_count = findViewById( R.id.total_ques_count );
        total_score_count = findViewById( R.id.total_score_count );
        correct_ans_count = findViewById( R.id.correct_ans_count );
        wrong_ans_count = findViewById( R.id.wrong_answers_count );
        skipped_ques_count = findViewById( R.id.skipped_ques_count );
        exitquiz = findViewById(R.id.exitquizbtn);

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child( "ResultsFpl" ).child( FirebaseAuth.getInstance().getCurrentUser().getUid() ).child( "Fpl" );
        databaseReference.orderByChild( "status" ).equalTo( "wrong" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildrenCount() != -1){
                    String wrng = String.valueOf( dataSnapshot.getChildrenCount() );
                    wrong_ans_count.setText( wrng );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child( "ResultsFpl" ).child( FirebaseAuth.getInstance().getCurrentUser().getUid() ).child( "Fpl" );
        databaseReference1.orderByChild( "status" ).equalTo( "correct" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildrenCount() != -1){
                    String correct = String.valueOf( dataSnapshot.getChildrenCount() );
                    correct_ans_count.setText( correct );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        final DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child( "ResultsFpl" ).child( FirebaseAuth.getInstance().getCurrentUser().getUid() ).child( "Fpl" );
        databaseReference2.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildrenCount() != -1){
                    String totalques = String.valueOf( dataSnapshot.getChildrenCount() );
                    total_ques_count.setText( totalques );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        final DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference().child( "ResultsFpl" ).child( FirebaseAuth.getInstance().getCurrentUser().getUid() ).child( "Fpl" );
        databaseReference3.orderByChild( "status" ).equalTo( "skipped" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildrenCount() != -1){
                    String skipped = String.valueOf( dataSnapshot.getChildrenCount() );
                    skipped_ques_count.setText( skipped );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        final DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child( "ResultsFpl" ).child( FirebaseAuth.getInstance().getCurrentUser().getUid() ).child( "Fpl" );
        databaseReference4.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int scorecount = 0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Score score = dataSnapshot1.getValue(Score.class);
                    scorecount = scorecount + score.getScore();
                }
                total_score_count.setText( String.valueOf( scorecount ) );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        exitquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fpl_results.this, EventsMain.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Fpl_results.this,EventsMain.class);
        startActivity(intent);
        finish();
    }
}
