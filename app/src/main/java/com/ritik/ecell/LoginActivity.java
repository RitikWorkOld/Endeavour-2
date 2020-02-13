package com.ritik.ecell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.ritik.ecell.Utils.Save;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class LoginActivity extends AppCompatActivity implements TextWatcher,
        CompoundButton.OnCheckedChangeListener{
    boolean session;
    EditText emailId, password;
    TextView textView,textView1;
    LinearLayout layouts;
    View v;

    private CheckBox rem_userpass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";

    private Toast backToast;
    private long backPressedTime;
    private Button signupbtn;
    FirebaseAuth mFirebaseAuth;
    Button btnSignIn;
    private ProgressBar progressBars;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        if(!haveNetworkConnection()){


            Toast.makeText(LoginActivity.this,"No Network Connection",Toast.LENGTH_LONG).show();
        }
        SESSION();

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        rem_userpass = (CheckBox)findViewById(R.id.checkBox);
        if(sharedPreferences.getBoolean(KEY_REMEMBER, false))
            rem_userpass.setChecked(true);
        else
            rem_userpass.setChecked(false);


        textView1=findViewById(R.id.nr1);
        textView=findViewById(R.id.fpass);
        mFirebaseAuth = FirebaseAuth.getInstance();

        emailId = findViewById(R.id.Lemail);

        v=findViewById(android.R.id.content);

        password = findViewById(R.id.Lpass);
        signupbtn=findViewById(R.id.button_signup);
        btnSignIn=findViewById(R.id.signin);
        progressBars = findViewById(R.id.progressBar2);


        emailId.setText(sharedPreferences.getString(KEY_USERNAME,""));
        password.setText(sharedPreferences.getString(KEY_PASS,""));

        emailId.addTextChangedListener(this);
        password.addTextChangedListener(this);
        rem_userpass.setOnCheckedChangeListener(this);

        progressBars.setVisibility(View.GONE);

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegAct.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ForgotPass.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == signupbtn)
                {
                    Intent intent=new Intent(LoginActivity.this,RegAct.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();

                }
            }
        });
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
               if(mFirebaseUser==null){
                   // Toast.makeText(LoginActivity.this,"Please Login",Toast.LENGTH_SHORT).show();*******************************************************************
                }
            }
        };




        btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                progressBars.setVisibility(View.VISIBLE);

                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty()){
                    progressBars.setVisibility(View.GONE);
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }
                else  if(pwd.isEmpty()){
                    progressBars.setVisibility(View.GONE);
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else  if(email.isEmpty() && pwd.isEmpty()){
                    progressBars.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else  if(!(email.isEmpty() && pwd.isEmpty())){
                    //check this runs
                    Log.d("LOOP 1", "status: login ");//ye lga rhndo

                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                progressBars.setVisibility(View.GONE);



                                //Toast.makeText(LoginActivity.this,"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this,Login_Failed.class);
                                startActivity(intent);
                            }
                            else{
                                progressBars.setVisibility(View.GONE);

                                Log.d(">> NOTWORKING 1", "onComplete: + COME IN LOOP ");
                                ////yha bhi aaya run statement...ok

                                //saving session
                                Save.save(getApplicationContext(),"session","true");

                                Intent intToHome = new Intent(getApplicationContext(),Dashboard.class);//not working TEAM.
                                startActivity(intToHome);

                                finish();

                            }
                        }
                    });
                }
                else{
                    progressBars.setVisibility(View.GONE);
                    //Toast.makeText(LoginActivity.this,"Error Occurred!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,Login_Failed.class);
                    startActivity(intent);

                }

            }
        });

    }   //on_create khtm
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        managePrefs();
    }

    private void managePrefs(){
        if(rem_userpass.isChecked()){
            editor.putString(KEY_USERNAME, emailId.getText().toString().trim());
            editor.putString(KEY_PASS, password.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        }else{
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
            editor.remove(KEY_USERNAME);//editor.putString(KEY_USERNAME, "");
            editor.apply();
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }



//see no logs.. let me give you example of testing
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

    public void SESSION(){
        session= Boolean.valueOf(Save.read(getApplicationContext(),"session","false"));
        if(session){
            //here when user first or logout
            //In here,intent to signup for first reg
            showtoast();
           // Toast.makeText(this,"Already Logged In",Toast.LENGTH_LONG).show();
            Intent signup=new Intent(getApplicationContext(),Dashboard.class);
            startActivity(signup);

            finish();
        }
        else{
            //here when user logged in
            //value here is true
            //Toast.makeText(this,"ALREADY LOGGED IN",Toast.LENGTH_SHORT).show();


        }

    }

    private void showtoast() {

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));


        ImageView toastImage = layout.findViewById(R.id.toast_image);


        toastImage.setImageResource(R.drawable.log_in);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.DISPLAY_CLIP_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        toast.show();
    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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