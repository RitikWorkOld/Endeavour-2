package com.ritik.ecell;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class glipm_detail extends AppCompatActivity {

    public ImageView glimpdetail;
    public int img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_glipm_detail);

        img = getIntent().getIntExtra("imguri",img);

        glimpdetail = findViewById(R.id.glimpdetail);

        glimpdetail.setImageDrawable(getApplicationContext().getResources().getDrawable(img));

    }
}
