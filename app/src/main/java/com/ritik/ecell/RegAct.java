package com.ritik.ecell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class RegAct extends AppCompatActivity implements View.OnClickListener {

    //These are the objects needed
    //It is the verification id that will be sent to the user
    private String mVerificationId;

    //The edittext to input the code
    private EditText editTextCode;

    //firebase auth object
    private FirebaseAuth mAuth;
    private String endvr = "ENDVR";



    private EditText emailId,password,fname1,branch1,year1,cid1,number1,cname1,ambd_id;
    private Button loginbtn;
    private Toast backToast;
    private long backPressedTime;
    private ProgressBar progressBar;
    int code=91;

    FirebaseAuth mFirebaseAuth;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reg);
        mFirebaseAuth = FirebaseAuth.getInstance();
        if(!haveNetworkConnection()){


            Toast.makeText(RegAct.this,"No Network Connection",Toast.LENGTH_LONG).show();


        }


        //initializing objects
        mAuth = FirebaseAuth.getInstance();         //added

        emailId = findViewById(R.id.email);
        password = findViewById(R.id.password);
        fname1 = findViewById(R.id.fname);
        branch1 = findViewById(R.id.branch);
        year1 = findViewById(R.id.year);
        cid1 = findViewById(R.id.campus);
        number1 = findViewById(R.id.cnumber);
        cname1 = findViewById(R.id.cname);
        ambd_id = findViewById(R.id.ambd_id);

        loginbtn=(Button) findViewById(R.id.button_login);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        findViewById(R.id.btnrequestotp).setOnClickListener(this);//**************************



        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == loginbtn)
                {
                    Intent intent=new Intent(RegAct.this,LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();

                }
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mFirebaseAuth.getCurrentUser() != null) {
            //handle the already login user
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnrequestotp:
                progressBar.setVisibility(View.VISIBLE);
                findViewById(R.id.btnrequestotp).setVisibility(View.GONE);
                boolean valid = validateUser();
                if (valid){
                    final String number=number1.getText().toString().trim();
                    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Users");
                    dbref.keepSynced(true);
                    dbref.orderByChild("contactn").equalTo(number).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                progressBar.setVisibility(View.GONE);
                                findViewById(R.id.btnrequestotp).setVisibility(View.VISIBLE);
                                Toast.makeText(RegAct.this,"User on this phone Number Already Exists",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                final String email=emailId.getText().toString().trim();
                                final String pwd=password.getText().toString().trim();
                                final String fname=fname1.getText().toString().trim();
                                final String branch=branch1.getText().toString().trim();
                                final String year=year1.getText().toString().trim();
                                final String cid=cid1.getText().toString().trim();
                                final String number=number1.getText().toString().trim();
                                final String cname=cname1.getText().toString().trim();
                                final String ambdid=ambd_id.getText().toString().toUpperCase().trim();

                                Intent intent = new Intent(RegAct.this,RequestOtp.class);
                                intent.putExtra("name",fname);
                                intent.putExtra("email",email);
                                intent.putExtra("password",pwd);
                                intent.putExtra("branch",branch);
                                intent.putExtra("year",year);
                                intent.putExtra("campusid",cid);
                                intent.putExtra("number",number);
                                intent.putExtra("cname",cname);
                                intent.putExtra("ambdid",ambdid);
                                startActivity(intent);

                                progressBar.setVisibility(View.GONE);
                                findViewById(R.id.btnrequestotp).setVisibility(View.VISIBLE);
                                //Toast.makeText(RegAct.this,"NO user found",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    findViewById(R.id.btnrequestotp).setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private boolean validateUser() {
        final String email=emailId.getText().toString().trim();
        final String pwd=password.getText().toString().trim();
        final String fname=fname1.getText().toString().trim();
        final String branch=branch1.getText().toString().trim();
        final String year=year1.getText().toString().trim();
        final String cid=cid1.getText().toString().trim();
        final String number=number1.getText().toString().trim();
        final String cname=cname1.getText().toString().trim();

        if(fname.isEmpty()){
            fname1.setError(getString(R.string.input_error_name));
            fname1.requestFocus();
            return false;
        }
        else if(email.isEmpty()){
            emailId.setError(getString(R.string.input_error_email));
            emailId.requestFocus();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailId.setError(getString(R.string.input_error_email_invalid));
            emailId.requestFocus();
            return false;
        }
        else if(pwd.isEmpty()){
            password.setError(getString(R.string.input_error_password));
            password.requestFocus();
            return false;
        }
        else if (pwd.length() < 6 ) {
            password.setError(getString(R.string.input_error_password_length));
            password.requestFocus();
            return false;
        }
        else if(!PASSWORD_PATTERN.matcher(pwd).matches()){

            password.setError("1 Digit? \n 1 LowerCase? \n 1 UpperCase? \n 1 Special Character? \n atleast 6 character?");
            password.requestFocus();
            return false;
        }

        else if(branch.isEmpty()){
            branch1.setError("Please Enter Branch");
            branch1.requestFocus();
            return false;
        }

        else if(year.isEmpty()){
            year1.setError("Please Enter Year");
            year1.requestFocus();
            return false;
        }
        else if(cid.isEmpty()){
            cid1.setError("Please Enter CampusID");
            cid1.requestFocus();
            return false;
        }
        else if(number.isEmpty()){
            number1.setError("Please Enter Your Number");
            number1.requestFocus();
            return false;
        }

        else if (number.length() != 10) {
            number1.setError(getString(R.string.input_error_phone_invalid));
            number1.requestFocus();
            return false;
        }
        else if(cname.isEmpty()){
            cname1.setError("Please Enter College Name");
            cname1.requestFocus();
            return false;
        }

        else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {


        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            finish();
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();


        }
        backPressedTime = System.currentTimeMillis();

    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
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




