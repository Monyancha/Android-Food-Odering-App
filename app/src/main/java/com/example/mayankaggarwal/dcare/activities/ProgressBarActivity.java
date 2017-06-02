package com.example.mayankaggarwal.dcare.activities;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.mayankaggarwal.dcare.R;

public class ProgressBarActivity extends AppCompatActivity {

    ImageView one,two,three,four,five;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);

        one=(ImageView)findViewById(R.id.progresstwo);
        two=(ImageView)findViewById(R.id.progressthree);
        three=(ImageView)findViewById(R.id.progressfour);
        four=(ImageView)findViewById(R.id.progressfive);
        five=(ImageView)findViewById(R.id.progresssix);

        setCustomProgress();

    }

    private void setCustomProgress() {
        CountDownTimer countDown=new CountDownTimer(6000, 1000) {
            public void onTick(long millisUntilFinished) {
//                Log.d("tagg","seconds remaining: " + millisUntilFinished / 1000);
                int sec= (int) (millisUntilFinished/1000);
                if(sec==5){
                    setBlueResource(one);
                }else if(sec==4) {
                    setBlueResource(two);
                }else if(sec==3) {
                    setBlueResource(three);
                }else if(sec==2) {
                    setBlueResource(four);
                }else if(sec==1) {
                    setBlueResource(five);
                }
            }

            public void onFinish() {
//                Log.d("tagg","finish");
                setGreyResource();
                setCustomProgress();
            }

        };
        countDown.start();
    }

    private void setBlueResource(ImageView img) {
        img.setBackgroundResource(R.drawable.progress_selected);
    }

    private void setGreyResource() {
        one.setBackgroundResource(R.drawable.progress_notselected);
        two.setBackgroundResource(R.drawable.progress_notselected);
        three.setBackgroundResource(R.drawable.progress_notselected);
        four.setBackgroundResource(R.drawable.progress_notselected);
        five.setBackgroundResource(R.drawable.progress_notselected);
    }

}
