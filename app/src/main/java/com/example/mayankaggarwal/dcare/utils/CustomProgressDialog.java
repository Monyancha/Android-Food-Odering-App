package com.example.mayankaggarwal.dcare.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mayankaggarwal.dcare.R;

import java.util.Random;

/**
 * Created by mayankaggarwal on 16/03/17.
 */

public class CustomProgressDialog {

    private static AlertDialog alert;

    private static boolean showingAlert=false;


    public static void showProgress(final Activity activity) {

        showingAlert=false;

        final ImageView one,two,three,four,five;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomDialog);
        View view = activity.getLayoutInflater().inflate(R.layout.activity_progress_bar, null);
        one=(ImageView)view.findViewById(R.id.progresstwo);
        two=(ImageView)view.findViewById(R.id.progressthree);
        three=(ImageView)view.findViewById(R.id.progressfour);
        four=(ImageView)view.findViewById(R.id.progressfive);
        five=(ImageView)view.findViewById(R.id.progresssix);


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
                one.setBackgroundResource(R.drawable.progress_notselected);
                two.setBackgroundResource(R.drawable.progress_notselected);
                three.setBackgroundResource(R.drawable.progress_notselected);
                four.setBackgroundResource(R.drawable.progress_notselected);
                five.setBackgroundResource(R.drawable.progress_notselected);
                showProgress(activity);
            }

        };
        countDown.start();


        builder.setView(view);
        alert = builder.create();
        alert.setCancelable(false);
        alert.show();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        showingAlert=true;

        if(activity.isDestroyed() || activity.isFinishing()){
            alert.dismiss();
        }

    }


    public static void hideProgress() {
        if (showingAlert == true) {
            alert.dismiss();
        }
    }

    public static void setBlueResource(ImageView img) {
        img.setBackgroundResource(R.drawable.progress_selected);
    }

}
