package com.example.mayankaggarwal.dcare.utils;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.activities.Details;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayankaggarwal on 03/06/17.
 */

public class OrderAlerts {

    private static AlertDialog declineAlert;
    private static AlertDialog feedbackAlert;

    public static void showDeclineOrderAlert(final Activity activity) {

        final Button yes, no;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.alert_decline_order, null);
        yes = (Button) view.findViewById(R.id.yesdialog);
        no = (Button) view.findViewById(R.id.nodialog);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                declineAlert.dismiss();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                declineAlert.dismiss();
            }
        });

        yes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        yes.setBackgroundResource(R.drawable.round_solid_blue);
                        yes.setTextColor(activity.getResources().getColor(R.color.white));
                        return false;
                    case MotionEvent.ACTION_UP:
                        yes.setBackgroundResource(R.drawable.round_shape_border_blue);
                        yes.setTextColor(activity.getResources().getColor(R.color.themeblue));
                        return false;
                }
                return false;
            }
        });

        no.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        no.setBackgroundResource(R.drawable.round_shape_solid_invalid);
                        no.setTextColor(activity.getResources().getColor(R.color.white));
                        return false;
                    case MotionEvent.ACTION_UP:
                        no.setBackgroundResource(R.drawable.round_shape_border_orange);
                        no.setTextColor(activity.getResources().getColor(R.color.themered));
                        return false;
                }
                return false;
            }
        });

        builder.setView(view);
        declineAlert = builder.create();
        declineAlert.setCancelable(false);
        declineAlert.show();
        if (activity.isDestroyed() || activity.isFinishing()) {
            declineAlert.dismiss();
        }
    }

    public static void showfeedbackAlert(final Activity activity) {

        final Button deliver, cancel, returned;
        final Spinner reasons;
        List<String> reasonList;
        ArrayAdapter<String> reasonAdapter;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.alert_feedback_order, null);
        deliver = (Button) view.findViewById(R.id.delivered);
        cancel = (Button) view.findViewById(R.id.cancelled);
        returned = (Button) view.findViewById(R.id.returned);
        reasons=(Spinner) view.findViewById(R.id.reasonspinner);

        reasonList=new ArrayList<>();
        reasonList.add("Reason 1");
        reasonList.add("Reason 2");
        reasonAdapter = new ArrayAdapter<String>(activity, R.layout.support_simple_spinner_dropdown_item, reasonList);
        reasons.setAdapter(reasonAdapter);


        deliver.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        deliver.setBackgroundResource(R.drawable.round_solid_blue);
                        deliver.setTextColor(activity.getResources().getColor(R.color.white));
                        return false;
                    case MotionEvent.ACTION_UP:
                        deliver.setBackgroundResource(R.drawable.round_shape_border_blue);
                        deliver.setTextColor(activity.getResources().getColor(R.color.themeblue));
                        return false;
                }
                return false;
            }
        });


        cancel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        cancel.setBackgroundResource(R.drawable.round_shape_solid_invalid);
                        cancel.setTextColor(activity.getResources().getColor(R.color.white));
                        return false;
                    case MotionEvent.ACTION_UP:
                        cancel.setBackgroundResource(R.drawable.round_shape_border_orange);
                        cancel.setTextColor(activity.getResources().getColor(R.color.themered));
                        return false;
                }
                return false;
            }
        });


        returned.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        returned.setBackgroundResource(R.drawable.round_shape_solid_grey);
                        returned.setTextColor(activity.getResources().getColor(R.color.white));
                        return false;
                    case MotionEvent.ACTION_UP:
                        returned.setBackgroundResource(R.drawable.round_shape_border_grey);
                        returned.setTextColor(activity.getResources().getColor(R.color.dark_grey));
                        return false;
                }
                return false;
            }
        });

        deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackAlert.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackAlert.dismiss();
            }
        });

        returned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackAlert.dismiss();
            }
        });

        builder.setView(view);
        feedbackAlert = builder.create();
        feedbackAlert.setCancelable(false);
        feedbackAlert.show();
        if (activity.isDestroyed() || activity.isFinishing()) {
            feedbackAlert.dismiss();
        }
    }

}
