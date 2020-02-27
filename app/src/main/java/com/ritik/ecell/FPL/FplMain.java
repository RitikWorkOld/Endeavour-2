package com.ritik.ecell.FPL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ritik.ecell.BQuiz.Bquiz;
import com.ritik.ecell.BQuiz.fr_bquiz_ques1;
import com.ritik.ecell.BQuiz.fr_bquiz_timeup;
import com.ritik.ecell.R;

public class FplMain extends AppCompatActivity {

    public TextView timer;

    public CountDownTimer countDownTimer;
    public long totaltime =2*600000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fpl_main);

        timer = (TextView)findViewById(R.id.time_score);

        countDownTimer = new CountDownTimer(totaltime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                totaltime = millisUntilFinished;
                updatetimer();
            }

            @Override
            public void onFinish() {

                /*AlertDialog.Builder builder = new AlertDialog.Builder(getApplication());
                builder.setTitle("Time UP");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Your Time is UP\nProceed to the Results")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Bquiz.this, EventsMain.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();*/

                final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_fpl,new fr_fpl_timeup());
                fragmentTransaction.commit();

                //Toast.makeText(Bquiz.this,"Time up",Toast.LENGTH_SHORT).show();

            }
        }.start();

        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container_fpl,new fr_fpl_ques1());
        fragmentTransaction.commit();
    }

    private void updatetimer() {
        int minutes = (int) totaltime / 60000;
        int seconds = (int) totaltime % 60000 / 1000;

        String timelefttext;
        timelefttext = "" + minutes;
        timelefttext += ":";
        if (seconds < 10) timelefttext += "0";
        timelefttext += seconds;

        timer.setText(timelefttext);
    }

    @Override
    public void onBackPressed() {

        Toast.makeText(FplMain.this,"You Can Not Exit At This Moment",Toast.LENGTH_SHORT).show();

    }
}
