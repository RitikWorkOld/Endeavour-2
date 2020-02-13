package com.ritik.ecell;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Pay_Success extends AppCompatActivity {

    public Button nextbtn;
    public TextView amount;
    public ImageView crossiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay__success);

        nextbtn = (Button) findViewById(R.id.next_btn_ps);
        amount = (TextView) findViewById(R.id.amttext);
        crossiv = (ImageView) findViewById(R.id.cross_btn_ps);

        String amt = getIntent().getStringExtra("amount");

        amount.setText("Amount : Rs."+amt);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.8));

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        crossiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
