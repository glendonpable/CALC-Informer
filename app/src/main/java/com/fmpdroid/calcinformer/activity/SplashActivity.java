package com.fmpdroid.calcinformer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sagara213@gmail.com on 12/3/2016.
 */
public class SplashActivity extends AppCompatActivity {

    Handler handler;
    private long timeDelay = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = new Intent(this, MainActivity.class);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(intent);
                finish();
            }
        }, timeDelay);
    }
}
