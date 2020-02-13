package com.ritik.ecell.Events_Fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ritik.ecell.R;
import com.ritik.ecell.checksum;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EC_Teams extends AppCompatActivity {

    String custid="", orderId="",amt="",faqid="";
    EditText teamname,teamleader,teammember1,teammember2,teammember3;
    Button next_btn;
    Boolean condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ec__teams);

        teamname = (EditText) findViewById(R.id.teamname);
        teamleader = (EditText) findViewById(R.id.teamleader);
        teammember1 = (EditText) findViewById(R.id.teammember1);
        teammember2 = (EditText) findViewById(R.id.teammember2);
        teammember3 = (EditText) findViewById(R.id.teammember3);
        next_btn = (Button) findViewById(R.id.next_btn);

        final Intent intent = getIntent();
        orderId = intent.getExtras().getString("orderid");
        custid = intent.getExtras().getString("custid");
        amt = intent.getExtras().getString("amount");
        faqid = intent.getExtras().getString("faqid");

        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        if (faqid.equals("3") || faqid.equals("5"))//members 1-3
        {
            teammember1.setHint("OPTIONAL (MEMBER 1)");
            teammember2.setHint(" OPTIONAL (MEMBER 2)");
            teammember3.setVisibility(View.GONE);

            next_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean valid = validateIdfor1_3();
                    if (valid){
                        final DatabaseReference databaseReferencel = FirebaseDatabase.getInstance().getReference().child("Users");
                        databaseReferencel.keepSynced(true);
                        databaseReferencel.orderByChild("refrelid").equalTo(teamleader.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() == null){
                                    Toast.makeText(EC_Teams.this, "Id not Valid", Toast.LENGTH_SHORT).show();
                                    teamleader.setError("Invalid ID");
                                    teamleader.requestFocus();
                                }
                                else {

                                    if (!teammember1.getText().toString().isEmpty())
                                    {
                                        final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Users");
                                        databaseReference1.keepSynced(true);
                                        databaseReference1.orderByChild("refrelid").equalTo(teammember1.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.getValue() == null){
                                                    Toast.makeText(EC_Teams.this, "Id not Valid", Toast.LENGTH_SHORT).show();
                                                    teammember1.setError("Invalid ID");
                                                    teammember1.requestFocus();
                                                }
                                                else {

                                                    if (!teammember2.getText().toString().isEmpty())
                                                    {
                                                        final DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Users");
                                                        databaseReference2.keepSynced(true);
                                                        databaseReference2.orderByChild("refrelid").equalTo(teammember2.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                if (dataSnapshot.getValue() == null){
                                                                    Toast.makeText(EC_Teams.this, "Id not Valid", Toast.LENGTH_SHORT).show();
                                                                    teammember2.setError("Invalid ID");
                                                                    teammember2.requestFocus();
                                                                }
                                                                else {
                                                                    Intent intenttl = new Intent(EC_Teams.this, checksum.class);
                                                                    intenttl.putExtra("orderid",orderId);
                                                                    intenttl.putExtra("custid",orderId);
                                                                    intenttl.putExtra("amount",amt);
                                                                    intenttl.putExtra("faqid",faqid);
                                                                    intenttl.putExtra("teamname",teamname.getText().toString());
                                                                    intenttl.putExtra("teamleader",teamleader.getText().toString());
                                                                    intenttl.putExtra("teammember1",teammember1.getText().toString());
                                                                    intenttl.putExtra("teammember2",teammember2.getText().toString());
                                                                    intenttl.putExtra("str","2");
                                                                    startActivity(intenttl);
                                                                    finish();
                                                                    //Toast.makeText(EC_Teams.this,"Success",Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }

                                                    else {
                                                        Intent intenttl = new Intent(EC_Teams.this, checksum.class);
                                                        intenttl.putExtra("orderid",orderId);
                                                        intenttl.putExtra("custid",orderId);
                                                        intenttl.putExtra("amount",amt);
                                                        intenttl.putExtra("faqid",faqid);
                                                        intenttl.putExtra("teamname",teamname.getText().toString());
                                                        intenttl.putExtra("teamleader",teamleader.getText().toString());
                                                        intenttl.putExtra("teammember1",teammember1.getText().toString());
                                                        intenttl.putExtra("str","1");
                                                        startActivity(intenttl);
                                                        finish();
                                                        //Toast.makeText(EC_Teams.this,"Success",Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }

                                    else {
                                        Intent intenttl = new Intent(EC_Teams.this, checksum.class);
                                        intenttl.putExtra("orderid",orderId);
                                        intenttl.putExtra("custid",orderId);
                                        intenttl.putExtra("amount",amt);
                                        intenttl.putExtra("faqid",faqid);
                                        intenttl.putExtra("teamname",teamname.getText().toString());
                                        intenttl.putExtra("teamleader",teamleader.getText().toString());
                                        intenttl.putExtra("str","0");
                                        startActivity(intenttl);
                                        finish();
                                        //Toast.makeText(EC_Teams.this,"Success",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            });
        }

        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


        else if (faqid.equals("4") || faqid.equals("6") || faqid.equals("0"))//members 1-2
        {
            teammember1.setHint("OPTIONAL (MEMBER 1)");
            teammember2.setVisibility(View.GONE);
            teammember3.setVisibility(View.GONE);

            next_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean valid = validateIdfor1_3();



                    if (valid){
                        final DatabaseReference databaseReferencel = FirebaseDatabase.getInstance().getReference().child("Users");
                        databaseReferencel.keepSynced(true);
                        databaseReferencel.orderByChild("refrelid").equalTo(teamleader.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() == null){
                                    Toast.makeText(EC_Teams.this, "Id not Valid", Toast.LENGTH_SHORT).show();
                                    teamleader.setError("Invalid ID");
                                    teamleader.requestFocus();
                                }
                                else {

                                    if (!teammember1.getText().toString().isEmpty())
                                    {
                                        final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Users");
                                        databaseReference1.keepSynced(true);
                                        databaseReference1.orderByChild("refrelid").equalTo(teammember1.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.getValue() == null){
                                                    Toast.makeText(EC_Teams.this, "Id not Valid", Toast.LENGTH_SHORT).show();
                                                    teammember1.setError("Invalid ID");
                                                    teammember1.requestFocus();
                                                }
                                                else {
                                                    Intent intenttl = new Intent(EC_Teams.this, checksum.class);
                                                    intenttl.putExtra("orderid",orderId);
                                                    intenttl.putExtra("custid",orderId);
                                                    intenttl.putExtra("amount",amt);
                                                    intenttl.putExtra("faqid",faqid);
                                                    intenttl.putExtra("teamname",teamname.getText().toString());
                                                    intenttl.putExtra("teamleader",teamleader.getText().toString());
                                                    intenttl.putExtra("teammember1",teammember1.getText().toString());
                                                    intenttl.putExtra("str","1");
                                                    startActivity(intenttl);
                                                    finish();
                                                    //Toast.makeText(EC_Teams.this,"Success",Toast.LENGTH_SHORT).show();

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }

                                    else {
                                        Intent intenttl = new Intent(EC_Teams.this, checksum.class);
                                        intenttl.putExtra("orderid",orderId);
                                        intenttl.putExtra("custid",orderId);
                                        intenttl.putExtra("amount",amt);
                                        intenttl.putExtra("faqid",faqid);
                                        intenttl.putExtra("teamname",teamname.getText().toString());
                                        intenttl.putExtra("teamleader",teamleader.getText().toString());
                                        intenttl.putExtra("str","0");
                                        startActivity(intenttl);
                                        finish();
                                        //Toast.makeText(EC_Teams.this,"Success",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }



                }
            });

        }

        //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


        else if (faqid.equals("2")||faqid.equals("8"))//members 2-4   hackathon
        {

            teammember1.setHint("REQUIRED (MEMBER 1)");
            teammember2.setHint(" OPTIONAL (MEMBER 2)");
            teammember3.setHint("OPTIONAL (MEMBER 3)");

            next_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean valid = validateIdforH();
                    if (valid){
                        final DatabaseReference databaseReferencel = FirebaseDatabase.getInstance().getReference().child("Users");
                        databaseReferencel.keepSynced(true);
                        databaseReferencel.orderByChild("refrelid").equalTo(teamleader.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() == null){
                                    Toast.makeText(EC_Teams.this, "Id not Valid", Toast.LENGTH_SHORT).show();
                                    teamleader.setError("Invalid ID");
                                    teamleader.requestFocus();
                                }
                                else {

                                    if (!teammember1.getText().toString().isEmpty())
                                    {
                                        final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Users");
                                        databaseReference1.keepSynced(true);
                                        databaseReference1.orderByChild("refrelid").equalTo(teammember1.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.getValue() == null){
                                                    Toast.makeText(EC_Teams.this, "Id not Valid", Toast.LENGTH_SHORT).show();
                                                    teammember1.setError("Invalid ID");
                                                    teammember1.requestFocus();
                                                }
                                                else {

                                                    if (!teammember2.getText().toString().isEmpty())
                                                    {
                                                        final DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Users");
                                                        databaseReference2.keepSynced(true);
                                                        databaseReference2.orderByChild("refrelid").equalTo(teammember2.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                if (dataSnapshot.getValue() == null){
                                                                    Toast.makeText(EC_Teams.this, "Id not Valid", Toast.LENGTH_SHORT).show();
                                                                    teammember2.setError("Invalid ID");
                                                                    teammember2.requestFocus();
                                                                }
                                                                else {

                                                                    if (!teammember3.getText().toString().isEmpty()){

                                                                        final DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Users");
                                                                        databaseReference2.keepSynced(true);
                                                                        databaseReference2.orderByChild("refrelid").equalTo(teammember3.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                if (dataSnapshot.getValue() == null) {
                                                                                    Toast.makeText(EC_Teams.this,"Id not Valid",Toast.LENGTH_SHORT).show();
                                                                                    teammember3.setError("Invalid ID");
                                                                                    teammember3.requestFocus();
                                                                                }
                                                                                else {
                                                                                    Intent intenttl = new Intent(EC_Teams.this, checksum.class);
                                                                                    intenttl.putExtra("orderid",orderId);
                                                                                    intenttl.putExtra("custid",orderId);
                                                                                    intenttl.putExtra("amount",amt);
                                                                                    intenttl.putExtra("faqid",faqid);
                                                                                    intenttl.putExtra("teamname",teamname.getText().toString());
                                                                                    intenttl.putExtra("teamleader",teamleader.getText().toString());
                                                                                    intenttl.putExtra("teammember1",teammember1.getText().toString());
                                                                                    intenttl.putExtra("teammember2",teammember2.getText().toString());
                                                                                    intenttl.putExtra("teammember3",teammember3.getText().toString());
                                                                                    intenttl.putExtra("str","3");
                                                                                    startActivity(intenttl);
                                                                                    finish();
                                                                                    //Toast.makeText(EC_Teams.this,"Success",Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }

                                                                            @Override
                                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                            }
                                                                        });

                                                                    }

                                                                }
                                                            }
                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }

                                                    else {
                                                        Intent intenttl = new Intent(EC_Teams.this, checksum.class);
                                                        intenttl.putExtra("orderid",orderId);
                                                        intenttl.putExtra("custid",orderId);
                                                        intenttl.putExtra("amount",amt);
                                                        intenttl.putExtra("faqid",faqid);
                                                        intenttl.putExtra("teamname",teamname.getText().toString());
                                                        intenttl.putExtra("teamleader",teamleader.getText().toString());
                                                        intenttl.putExtra("teammember1",teammember1.getText().toString());
                                                        intenttl.putExtra("teammember2",teammember2.getText().toString());
                                                        intenttl.putExtra("str","2");
                                                        startActivity(intenttl);
                                                        finish();
                                                        //Toast.makeText(EC_Teams.this,"Success",Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }

                                    else {
                                        Intent intenttl = new Intent(EC_Teams.this, checksum.class);
                                        intenttl.putExtra("orderid",orderId);
                                        intenttl.putExtra("custid",orderId);
                                        intenttl.putExtra("amount",amt);
                                        intenttl.putExtra("faqid",faqid);
                                        intenttl.putExtra("teamname",teamname.getText().toString());
                                        intenttl.putExtra("teamleader",teamleader.getText().toString());
                                        intenttl.putExtra("teammember1",teammember1.getText().toString());
                                        intenttl.putExtra("str","1");
                                        startActivity(intenttl);
                                        finish();
                                        //Toast.makeText(EC_Teams.this,"Success",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            });


        }

        //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        else if (faqid.equals("7"))//members 2
        {


            teammember1.setHint("REQUIRED (MEMBER 1)");
            teammember2.setVisibility(View.GONE);
            teammember3.setVisibility(View.GONE);

            next_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean valid = validateIdforH();



                    if (valid){
                        final DatabaseReference databaseReferencel = FirebaseDatabase.getInstance().getReference().child("Users");
                        databaseReferencel.keepSynced(true);
                        databaseReferencel.orderByChild("refrelid").equalTo(teamleader.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() == null){
                                    Toast.makeText(EC_Teams.this, "Id not Valid", Toast.LENGTH_SHORT).show();
                                    teamleader.setError("Invalid ID");
                                    teamleader.requestFocus();
                                }
                                else {

                                    if (!teammember1.getText().toString().isEmpty())
                                    {
                                        final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Users");
                                        databaseReference1.keepSynced(true);
                                        databaseReference1.orderByChild("refrelid").equalTo(teammember1.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.getValue() == null){
                                                    Toast.makeText(EC_Teams.this, "Id not Valid", Toast.LENGTH_SHORT).show();
                                                    teammember1.setError("Invalid ID");
                                                    teammember1.requestFocus();
                                                }
                                                else {
                                                    Intent intenttl = new Intent(EC_Teams.this, checksum.class);
                                                    intenttl.putExtra("orderid",orderId);
                                                    intenttl.putExtra("custid",orderId);
                                                    intenttl.putExtra("amount",amt);
                                                    intenttl.putExtra("faqid",faqid);
                                                    intenttl.putExtra("teamname",teamname.getText().toString());
                                                    intenttl.putExtra("teamleader",teamleader.getText().toString());
                                                    intenttl.putExtra("teammember1",teammember1.getText().toString());
                                                    intenttl.putExtra("str","1");
                                                    startActivity(intenttl);
                                                    finish();
                                                    //Toast.makeText(EC_Teams.this,"Success",Toast.LENGTH_SHORT).show();

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }

                                    else {
                                        //Toast.makeText(EC_Teams.this,"Success",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }



                }
            });


        }
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    private boolean validateIdfor1_3(){
        if (teamname.getText().toString().isEmpty()){
            teamname.setError("Required");
            teamname.requestFocus();
            return false;
        }
        if (teamleader.getText().toString().isEmpty()){
            teamleader.setError("Required");
            teamleader.requestFocus();
            return false;
        }
        else return true;
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    private boolean validateIdforH(){
        if (teamname.getText().toString().isEmpty()){
            teamname.setError("Required");
            teamname.requestFocus();
            return false;
        }
        if (teamleader.getText().toString().isEmpty()){
            teamleader.setError("Required");
            teamleader.requestFocus();
            return false;
        }
        if (teammember1.getText().toString().isEmpty()){
            teammember1.setError("Required");
            teammember1.requestFocus();
            return false;
        }
        else return true;
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}
