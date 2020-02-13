package com.ritik.ecell.Sponsors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ritik.ecell.BottomSheetNavigationFragment;
import com.ritik.ecell.BottomSheetNavigationFragmentOne;
import com.ritik.ecell.BottomSheetNavigationFragmentTwo;
import com.ritik.ecell.Dashboard;
import com.ritik.ecell.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Sponsor extends AppCompatActivity {
    private BottomAppBar bottomAppBar;

    private RecyclerView recyclerView;
    private ArrayList<Sponsor_Model> arrayList;
    private FirebaseRecyclerOptions<Sponsor_Model> options;
    private FirebaseRecyclerAdapter<Sponsor_Model,Sponsor_Viewholder> adapter;
    private DatabaseReference databaseReference;
    ImageView image;

    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sponsor);

        image=findViewById(R.id.back);
        setUpBottomAppBar();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sponsor.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });
        recyclerView = findViewById(R.id.recyclerview_sponsor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("sponsors");
        databaseReference.keepSynced(true);

        arrayList = new ArrayList<Sponsor_Model>();

        options = new FirebaseRecyclerOptions.Builder<Sponsor_Model>().setQuery(databaseReference,Sponsor_Model.class).build();

        adapter = new FirebaseRecyclerAdapter<Sponsor_Model, Sponsor_Viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Sponsor_Viewholder sponsor_viewholder, int i, @NonNull Sponsor_Model sponsor_model) {
                sponsor_viewholder.sponsort.setText(sponsor_model.getSponsorname());
                sponsor_viewholder.sponsorC.setText(sponsor_model.getSponsorcategory());
                Picasso.get().load(sponsor_model.getImagesponsor()).into(sponsor_viewholder.sponsorImage);
            }

            @NonNull
            @Override
            public Sponsor_Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new Sponsor_Viewholder(LayoutInflater.from(Sponsor.this).inflate(R.layout.sponsor_example_item,parent,false));
            }
        };
        recyclerView.setAdapter(adapter);
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
        Intent intent = new Intent( Sponsor.this, Dashboard.class);
        startActivity(intent);
        finish();
    }
}
