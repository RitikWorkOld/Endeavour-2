package com.ritik.ecell.Team;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
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
import com.ritik.ecell.Developer;
import com.ritik.ecell.FABAnimation;
import com.ritik.ecell.R;
import com.ritik.ecell.about_endeavour;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TeamMain extends AppCompatActivity {


    ImageView image;
    private RecyclerView recyclerView;
    private BottomAppBar bottomAppBar;
    FloatingActionButton fab,fab1,fab2;
    boolean isFabOpen=false;

    private ArrayList<Teamcard_model> arrayList;
    private FirebaseRecyclerOptions<Teamcard_model> options;
    private FirebaseRecyclerAdapter<Teamcard_model, Teamcard_Viewholder> adapter;
    private DatabaseReference databaseReference;
    private static FirebaseDatabase firebaseDatabase;
    boolean expanded = false;

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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_team_main);

        setUpBottomAppBar();

        //FAB WORK

        fab=findViewById( R.id.fab );
        fab1=findViewById( R.id.fab1 );
        fab2=findViewById( R.id.fab2 );

        FABAnimation.init( fab1 );
        FABAnimation.init( fab2 );


        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFabMode(v);
                animateFAB();
            }
        } );
        fab1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamMain.this, Developer.class);
                startActivity(intent);
            }
        } );
        fab2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamMain.this, about_endeavour.class);
                startActivity(intent);
            }
        } );


        //FAB END
        image=findViewById(R.id.back);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamMain.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView = findViewById(R.id.recycler_view_teammain);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("team");
        databaseReference.keepSynced(true);


        arrayList = new ArrayList<Teamcard_model>();

        options = new FirebaseRecyclerOptions.Builder<Teamcard_model>().setQuery(databaseReference,Teamcard_model.class).build();

        adapter = new FirebaseRecyclerAdapter<Teamcard_model, Teamcard_Viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final Teamcard_Viewholder teamcard_viewholder, int i, @NonNull final Teamcard_model teamcard_model) {
                teamcard_viewholder.Name.setText(teamcard_model.getName());
                teamcard_viewholder.Desig.setText(teamcard_model.getDesig());
                teamcard_viewholder.Domain.setText(teamcard_model.getDomain());
                Picasso.get().load(teamcard_model.getImguri()).into(teamcard_viewholder.Imguri);

                teamcard_viewholder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (teamcard_viewholder.expand == false){
                            teamcard_viewholder.expandableLayout.setVisibility(View.VISIBLE);
                            teamcard_viewholder.expand = true;
                        }
                        else {
                            teamcard_viewholder.expandableLayout.setVisibility(View.GONE);
                            teamcard_viewholder.expand = false;
                        }
                    }
                });

                teamcard_viewholder.google_profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(teamcard_model.getGoogle_profile());
                        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                });

                teamcard_viewholder.linkedin_profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(teamcard_model.getLinkedin_profile());
                        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                });

                teamcard_viewholder.facebook_profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(teamcard_model.getFacebook_profile());
                        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                });
            }
            @NonNull
            @Override
            public Teamcard_Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new Teamcard_Viewholder(LayoutInflater.from(TeamMain.this).inflate(R.layout.example_item,parent,false));
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void animateFAB(){

        isFabOpen=FABAnimation.rotateFab( fab,!isFabOpen );
        if(isFabOpen){
            FABAnimation.fabopen( fab1 );
            FABAnimation.fabopen( fab2 );
        }
        else{
            FABAnimation.fabclose( fab1 );
            FABAnimation.fabclose( fab2 );

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
        Intent intent = new Intent( TeamMain.this, Dashboard.class);
        startActivity(intent);
        finish();
    }
}
