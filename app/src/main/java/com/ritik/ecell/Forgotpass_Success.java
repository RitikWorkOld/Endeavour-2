package com.ritik.ecell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Forgotpass_Success extends AppCompatActivity {

    Button loginbtn;
    ImageView crossiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass__success);

        loginbtn = (Button)findViewById(R.id.login_btn_fps);
        crossiv = (ImageView) findViewById(R.id.cross_btn_fps);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.7));

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forgotpass_Success.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        crossiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forgotpass_Success.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
