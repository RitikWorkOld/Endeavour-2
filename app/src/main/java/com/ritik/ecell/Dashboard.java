package com.ritik.ecell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ritik.ecell.Events_Fragments.EventsMain;
import com.ritik.ecell.Notifications.Notifications;
import com.ritik.ecell.Notifications.Notifications_Dots;
import com.ritik.ecell.Shedule.Shedule;
import com.ritik.ecell.Speakers.Speakers;
import com.ritik.ecell.Sponsors.Sponsor;
import com.ritik.ecell.Team.TeamMain;
import com.ritik.ecell.Utils.Save;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity  {

    LinearLayout layoutOurteam;
    LinearLayout layoutevents;
    FirebaseAuth firebaseAuth;
    LinearLayout layoutsponsors;
    LinearLayout layoutspeakers;
    LinearLayout layoutshedule;
    LinearLayout layoutfaq;
    private ImageView notification_badge;

    private Toast backToast;
    ImageView notification_btn, image_power;
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);
        image_power = (ImageView) findViewById(R.id.image_power);
        notification_btn = (ImageView) findViewById(R.id.iv_notification_btn);

        if(!haveNetworkConnection()){
            Toast.makeText(Dashboard.this,"No Network Connection",Toast.LENGTH_LONG).show();
        }

        notification_badge = (ImageView)findViewById(R.id.notificationbadge);

        notification_badge.setVisibility(View.GONE);

        DatabaseReference databaseReferencenot = FirebaseDatabase.getInstance().getReference().child("NotificationDots")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReferencenot.keepSynced(true);
        databaseReferencenot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Notifications_Dots notifications_dots = dataSnapshot.getValue(Notifications_Dots.class);
                if (notifications_dots != null)
                {
                    if (notifications_dots.getDotstatus().equals("yes")){
                        notification_badge.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (ContextCompat.checkSelfPermission
                ( Dashboard.this, Manifest.permission.READ_SMS ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions
                    ( Dashboard.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101 );

        }

        if(isFirstTime()){
            /*TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.image_power), "Log Out Button", "Use this to signout from you account")
                    .tintTarget(false));*/
            new TapTargetSequence(this).targets(
                    TapTarget.forView(findViewById(R.id.image_power), "Log Out Button", "Use this to signout from your account \n (Tap on button to Cancel)")
                            .tintTarget(false)
                            .targetCircleColor(R.color.colorPrimaryDark)
                            .cancelable(false)
                            .id(1),
                    TapTarget.forView(findViewById(R.id.iv_notification_btn), "Notification Button", "Tap here to see notifications\n (Tap on button to Cancel)")
                            .tintTarget(false)
                            .cancelable(false)
                            .targetCircleColor(R.color.colorPrimaryDark)
                            .id(2)
            ).listener(new TapTargetSequence.Listener() {
                @Override
                public void onSequenceFinish() {
                }

                @Override
                public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                }

                @Override
                public void onSequenceCanceled(TapTarget lastTarget) {

                }
            }).start();
        }
        layoutfaq = (LinearLayout) findViewById(R.id.layout_faq);
        layoutevents = (LinearLayout) findViewById(R.id.layout_events);
        layoutOurteam = (LinearLayout) findViewById(R.id.layout_ourteam);
        layoutsponsors = (LinearLayout) findViewById(R.id.layout_sponsors);
        layoutspeakers = (LinearLayout) findViewById(R.id.layout_speakers);
        layoutshedule = (LinearLayout) findViewById(R.id.layout_shedule);

        firebaseAuth = firebaseAuth.getInstance();

        layoutevents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_events = new Intent(Dashboard.this, EventsMain.class);
                startActivity(intent_events);
                finish();
            }
        });
        layoutfaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_events = new Intent(Dashboard.this, FAQ.class);
                startActivity(intent_events);
                finish();
            }
        });
        layoutsponsors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_sponsors = new Intent(Dashboard.this, Sponsor.class);
                startActivity(intent_sponsors);
                finish();
            }
        });
        layoutspeakers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_speakers = new Intent(Dashboard.this, Speakers.class);
                startActivity(intent_speakers);
                finish();
            }
        });
        layoutshedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_shedule = new Intent(Dashboard.this, Shedule.class);
                startActivity(intent_shedule);
                finish();
            }
        });
        image_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.drawable.endlogo);
                builder.setMessage("Do you want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                firebaseAuth.getInstance().signOut();
                                //saving session
                                Save.save(getApplicationContext(), "session", "false");
                                Intent intent = new Intent(Dashboard.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        layoutOurteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == layoutOurteam) {
                    Intent intent_ourteam = new Intent(Dashboard.this, TeamMain.class);
                    startActivity(intent_ourteam);
                    finish();
                }
            }
        });
        notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReferencenotup = FirebaseDatabase.getInstance().getReference().child("NotificationDots")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                databaseReferencenotup.child("dotstatus").setValue("no");
                databaseReferencenotup.keepSynced(true);

                Intent intent = new Intent(Dashboard.this, Notifications.class);
                startActivity(intent);
                finish();
            }
        });
    }
//---------------------------------------------------------------------------------------
    private boolean isFirstTime() {

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }
//------------------------------------------------------------------------------------------
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
