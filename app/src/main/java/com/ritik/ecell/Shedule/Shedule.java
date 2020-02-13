package com.ritik.ecell.Shedule;

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

import com.ritik.ecell.BottomSheetNavigationFragment;
import com.ritik.ecell.BottomSheetNavigationFragmentOne;
import com.ritik.ecell.BottomSheetNavigationFragmentTwo;
import com.ritik.ecell.Dashboard;
import com.ritik.ecell.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class Shedule extends AppCompatActivity {
    private BottomAppBar bottomAppBar;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_shedule);
        setUpBottomAppBar();

        imageView = (ImageView)findViewById(R.id.back_btn);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Shedule.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container_shedule,new Feb_frag());
        fragmentTransaction.commit();

        if (getIntent().getStringExtra("type") != null)
        {
            if (getIntent().getStringExtra("type").equals("FEB"))
            {
                final FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.container_shedule,new Feb_frag());
                fragmentTransaction1.commit();
            }

            else if (getIntent().getStringExtra("type").equals("MAR"))
            {
                final FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.container_shedule,new Mar_frag());
                fragmentTransaction3.commit();
            }
        }
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
        Intent intent = new Intent( Shedule.this, Dashboard.class);
        startActivity(intent);
        finish();
    }
}
