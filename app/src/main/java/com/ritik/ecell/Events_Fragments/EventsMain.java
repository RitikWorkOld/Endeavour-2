package com.ritik.ecell.Events_Fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.ritik.ecell.BottomSheetNavigationFragment;
import com.ritik.ecell.BottomSheetNavigationFragmentOne;
import com.ritik.ecell.BottomSheetNavigationFragmentTwo;
import com.ritik.ecell.Dashboard;
import com.ritik.ecell.Pay_Success;
import com.ritik.ecell.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class EventsMain extends AppCompatActivity {
    private BottomAppBar bottomAppBar;
    RadioButton radioButton_tech, radioButton_fun, radioButton_corp;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_events_main);

        setUpBottomAppBar();

        image=findViewById(R.id.back);

        String amount = getIntent().getStringExtra("amount");
        if (amount != null)
        {
            Intent intent = new Intent(EventsMain.this, Pay_Success.class);
            intent.putExtra("amount",amount);
            startActivity(intent);
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventsMain.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        radioButton_tech = (RadioButton)findViewById(R.id.radio_technical);
        radioButton_fun = (RadioButton)findViewById(R.id.radio_fun);
        radioButton_corp = (RadioButton)findViewById(R.id.radio_corporate);

        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.events_container,new Events_Corp());
        fragmentTransaction.commit();

        if (getIntent().getStringExtra("type") != null)
        {
            if (getIntent().getStringExtra("type").equals("TECH"))
            {
                final FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.events_container,new Events_Tech());
                fragmentTransaction1.commit();
                radioButton_tech.setChecked(true);
            }

            else if (getIntent().getStringExtra("type").equals("CORP"))
            {
                final FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.events_container,new Events_Corp());
                fragmentTransaction3.commit();
                radioButton_corp.setChecked(true);
            }
            else {
                final FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.events_container,new Events_Fun());
                fragmentTransaction3.commit();
                radioButton_fun.setChecked(true);
            }
        }

        radioButton_tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.events_container,new Events_Tech());
                fragmentTransaction1.commit();
            }
        });
        radioButton_fun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.events_container,new Events_Fun());
                fragmentTransaction2.commit();
            }
        });
        radioButton_corp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.events_container,new Events_Corp());
                fragmentTransaction3.commit();

            }
        });
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
        Intent intent = new Intent( EventsMain.this, Dashboard.class);
        startActivity(intent);
        finish();
    }

}
