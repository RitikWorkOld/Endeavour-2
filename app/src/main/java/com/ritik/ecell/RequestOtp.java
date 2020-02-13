package com.ritik.ecell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ritik.ecell.Utils.Save;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class RequestOtp extends AppCompatActivity {

    private String mVerificationId;
    PhoneAuthProvider.ForceResendingToken token;
    private EditText editTextCode;
    private Button verify;

    private FirebaseAuth mAuth;


    private FirebaseAuth mFirebaseAuth;
    int code=91;
    private String endvr = "ENDVR";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_otp);

        mAuth = FirebaseAuth.getInstance();


        editTextCode = findViewById(R.id.otp_et);


        verify = findViewById(R.id.verify_btn);


        mFirebaseAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");
        final String pwd = intent.getStringExtra("password");
        final String fname = intent.getStringExtra("name");
        final String branch = intent.getStringExtra("branch");
        final String year = intent.getStringExtra("year");
        final String cid = intent.getStringExtra("campusid");
        final String number = intent.getStringExtra("number");
        final String cname = intent.getStringExtra("cname");

        final String mobile = "+"+code+number;
        sendVerificationCode(mobile);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editTextCode.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    editTextCode.setError("Enter valid code");
                    editTextCode.requestFocus();
                    return;
                }

                //verifying the code entered manually
                verifyVerificationCode(code);
            }
        });
    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,
                120,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                editTextCode.setText(code);

                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(RequestOtp.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
            token=forceResendingToken;

        }
    };

    private void verifyVerificationCode(String code) {
        //creating the credential
        try {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            mAuth.signOut();
            mFirebaseAuth.signOut();
            //signing the user

            signInWithPhoneAuthCredential(credential);
        }catch (Exception e){ //ss
            Toast toast = Toast.makeText(getApplicationContext(), "Verification Code is wrong, try again", Toast.LENGTH_SHORT);
            toast.setGravity( Gravity.CENTER,0,0);
            toast.show();
        }

    }

   private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(RequestOtp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    //verification successful we will start the profile activity

                    registeruser();

                } else {

                    //verification unsuccessful.. display an error message

                    String message = "Somthing is wrong, we will fix it soon...";

                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        message = "Invalid code entered...";
                    }

                    Toast.makeText(RequestOtp.this,message, Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    private void registeruser() {
        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");
        final String pwd = intent.getStringExtra("password");
        final String fname = intent.getStringExtra("name");
        final String branch = intent.getStringExtra("branch");
        final String year = intent.getStringExtra("year");
        final String cid = intent.getStringExtra("campusid");
        final String number = intent.getStringExtra("number");
        final String cname = intent.getStringExtra("cname");
        final String ambdid = intent.getStringExtra("ambdid");

        progressBar.setVisibility(View.VISIBLE);
        verify.setVisibility(View.GONE);
        mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final String refrelid = endvr.concat(number);
                    String uid = FirebaseAuth.getInstance().getUid();
                    User user=new User(fname,email,branch,year,cid,number,cname,uid,refrelid,pwd,ambdid);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            //progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {




                                //saving session
                                Save.save(getApplicationContext(),"session","false");


                                //Toast.makeText(RegAct.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RequestOtp.this,Reg_Sucess.class);
                                intent.putExtra("referid",refrelid);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    // The verification code entered was invalid
                                    Intent intent = new Intent(RequestOtp.this,Reg_Fail.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                        }
                    });

                }
                else {
                    //progressBar.setVisibility(View.GONE);
                    //Toast.makeText(RegAct.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RequestOtp.this,Reg_Fail.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
