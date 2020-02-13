package com.ritik.ecell.BQuiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ritik.ecell.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class bquiz_intro extends AppCompatActivity {

    public Button get_started;
    public TextView rules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bquiz_intro);

        get_started = findViewById( R.id.btn_get_started );
        rules = findViewById(R.id.text_rules);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("BquizIntro");
        databaseReference.keepSynced(true);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    Rules_Helper rules_helper = dataSnapshot.getValue(Rules_Helper.class);
                    rules.setText(rules_helper.getRules());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        get_started.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bquiz_intro.this,Bquiz.class);
                startActivity(intent);
                finish();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("BquizStatus").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                databaseReference.keepSynced(true);
                databaseReference.child("status").setValue("closed");
            }
        } );
    }
}
