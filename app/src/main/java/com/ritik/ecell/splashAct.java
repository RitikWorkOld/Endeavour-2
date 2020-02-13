package com.ritik.ecell;

import androidx.appcompat.app.AppCompatActivity;



import android.content.Intent;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.ImageView;
import android.widget.TextView;


public class splashAct extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        final ImageView imageView =  findViewById(R.id.imageView1);
        final TextView textView = findViewById(R.id.text_splash);

        //final Animation animation_1 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.zoom_in);
        final Animation animation_2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.fade);

        imageView.startAnimation(animation_2);
        textView.startAnimation(animation_2);

        animation_2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //imageView.startAnimation(animation_1);
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        /*animation_1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(animation_2);
                finish();
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/




    }
}
