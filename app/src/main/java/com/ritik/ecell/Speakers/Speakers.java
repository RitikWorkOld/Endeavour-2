package com.ritik.ecell.Speakers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ritik.ecell.BottomSheetNavigationFragment;
import com.ritik.ecell.BottomSheetNavigationFragmentOne;
import com.ritik.ecell.BottomSheetNavigationFragmentTwo;
import com.ritik.ecell.Dashboard;
import com.ritik.ecell.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Speakers extends AppCompatActivity {
    private BottomAppBar bottomAppBar;
    ImageView image;
    String TAG = "BLOCK *****";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_speakers);
        setUpBottomAppBar();

        image=findViewById(R.id.back_btn);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Speakers.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("speakers");
        databaseReference.keepSynced(true);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null){
                    long j = dataSnapshot.getChildrenCount();
                    Log.d(TAG,"VALUE for one only ----------------------------------------------------- "+j);
                    int i = (int)j;
                    final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
                    StartProcess(viewPager,i);
                }
                else {
                    findViewById(R.id.viewPager).setVisibility(View.GONE);
                    findViewById(R.id.comingsoon_layout).setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void StartProcess(ViewPager viewPager,int i){
        Log.d(TAG,"HERE ie the part *******************************************************");

        CardFragmentPagerAdapter pagerAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(), dpToPixels(2, this),i);
        ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
        fragmentCardShadowTransformer.enableScaling(true);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
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
    /**
     * method to toggle fab mode
     *
     * @param view
     */
    public void toggleFabMode(View view) {
        //check the fab alignment mode and toggle accordingly
        if (bottomAppBar.getFabAlignmentMode() == BottomAppBar.FAB_ALIGNMENT_MODE_END) {
            bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        } else {
            bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent( Speakers.this, Dashboard.class);
        startActivity(intent);
        finish();
    }
}
