package com.ritik.ecell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FAQ extends AppCompatActivity {
    private BottomAppBar bottomAppBar;
Button btn,btn_submit;
EditText input_layout_subject,input_layout_subject1,input_layout_subject2;
    RadioButton radioButton_tech, radioButton_fun, radioButton_corp;
    private ProgressBar progressBar;


    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView( R.layout.activity_faq );
        setUpBottomAppBar();

        image=findViewById(R.id.back);

        input_layout_subject=findViewById(R.id.input_layout_subject);
        input_layout_subject1=findViewById(R.id.input_layout_subject1);     //email
        input_layout_subject2=findViewById(R.id.input_layout_subject2);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( FAQ.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        radioButton_tech = (RadioButton)findViewById(R.id.radio_technical);
        radioButton_fun = (RadioButton)findViewById(R.id.radio_fun);
        radioButton_corp = (RadioButton)findViewById(R.id.radio_corporate);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        btn=(Button) findViewById( R.id.rate_btn );
        btn_submit=findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Book book = new Book();
                book.setSubject( input_layout_subject.getText().toString() );     //subject

                book.setEmail( input_layout_subject1.getText().toString() );    //email_id
                book.setMessage( input_layout_subject2.getText().toString() );        //message

                boolean valid = checking();
                if (valid) {
                    progressBar.setVisibility( View.VISIBLE );
                    new FirebaseDatabaseHelper().addBook( book, new FirebaseDatabaseHelper.DataStatus() {
                        @Override
                        public void DataIsInserted() {
                            Toast.makeText( FAQ.this, "THANKS", Toast.LENGTH_LONG ).show();
                            progressBar.setVisibility( View.GONE );
                        }
                    } );


                }

            }
        } );
        final EditText email = (EditText) findViewById(R.id.input_layout_subject1);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.keepSynced(true);
        databaseReference.orderByChild("uid").equalTo( FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Bnd_helper bnd_helper = dataSnapshot1.getValue(Bnd_helper.class);

                    String n = bnd_helper.email;
                    email.setText(n);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


btn.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        rateApp();
    }
} );


    }
    /**
     * set up Bottom Bar
     */
    private void setUpBottomAppBar() {
        //find id
        bottomAppBar = findViewById(R.id.bar);

        //set bottom bar to Action bar as it is similar like Toolbar
        setSupportActionBar(bottomAppBar);

        //click event over Bottom bar menu item
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_glimpses:
                        BottomSheetDialogFragment bottomSheetDialogFragmentTwo = BottomSheetNavigationFragmentTwo.newInstance();
                        bottomSheetDialogFragmentTwo.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment Two");
                        break;
                    case R.id.menu_about:
                        BottomSheetDialogFragment bottomSheetDialogFragment = BottomSheetNavigationFragmentOne.newInstance();
                        bottomSheetDialogFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment One");
                        break;
                }
                return false;
            }
        });
        //click event over navigation menu like back arrow or hamburger icon
        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open bottom sheet
                BottomSheetDialogFragment bottomSheetDialogFragment = BottomSheetNavigationFragment.newInstance();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
            }
        });
    }

    //Inflate menu to bottom bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_glimpses:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * Start with rating the app
     * Determine if the Play Store is installed on the device by RITIK
     *
     * */
    public void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("rateapp");
            databaseReference.keepSynced(true);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue()!= null){
                        Rateapp rateapp = dataSnapshot.getValue(Rateapp.class);
                        String rateappid = rateapp.getVlaue();

                        //Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
                        Intent rateIntent = rateIntentForUrl(rateappid);
                        startActivity(rateIntent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

    private boolean checking() {

        final String subject = input_layout_subject.getText().toString().trim();
        final String email = input_layout_subject1.getText().toString().trim();
        final String message = input_layout_subject2.getText().toString().trim();

        if (email.isEmpty()) {
            input_layout_subject1.setError( getString( R.string.input_error_email ) );
            input_layout_subject1.requestFocus();
            return false;

        }
        else if (subject.isEmpty()) {
            input_layout_subject.setError( "EMPTY" );
            input_layout_subject.requestFocus();
            return false;

        }
        else if (message.isEmpty()) {
            input_layout_subject2.setError( "EMPTY" );
            input_layout_subject2.requestFocus();
            return false;

        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_layout_subject1.setError(getString(R.string.input_error_email_invalid));
            input_layout_subject1.requestFocus();
            return false;
        }


        else
        {
            return true;
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent( FAQ.this, Dashboard.class);
        startActivity(intent);
        finish();
    }
}
