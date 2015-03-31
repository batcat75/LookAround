package com.cmu.cpe.se.blarblarblar.lookaround;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class Splash_Screen extends ActionBarActivity {

    Handler myHandler;
    Runnable runnable;
    long delay_time;
    long time = 3000L ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        myHandler = new Handler();

        runnable = new Runnable() {
            public void run() {
                Intent intent = new Intent(Splash_Screen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

    }

    public void onResume() {
        super.onResume();
        delay_time = time;
        myHandler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause() {
        super.onPause();
        myHandler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }

}
