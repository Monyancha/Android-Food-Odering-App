package com.example.mayankaggarwal.dcare.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.utils.Prefs;

public class SplashScreen extends AppCompatActivity {


    private final Handler waitHandler = new Handler();
    private final Runnable waitCallback = new Runnable() {
        @Override
        public void run() {

            if (!(Prefs.getPrefs("details_completed", SplashScreen.this).equals("notfound"))) {
                if((Prefs.getPrefs("details_completed", SplashScreen.this)).equals("1")){
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashScreen.this, OtpActivity.class);
                    startActivity(intent);
                    finish();
                }
            }else {
                Intent intent = new Intent(SplashScreen.this, OtpActivity.class);
                startActivity(intent);
                finish();
            }
        }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        waitHandler.postDelayed(waitCallback, 2000);
    }

    @Override
    protected void onDestroy() {
        waitHandler.removeCallbacks(waitCallback);
        super.onDestroy();
    }

}
