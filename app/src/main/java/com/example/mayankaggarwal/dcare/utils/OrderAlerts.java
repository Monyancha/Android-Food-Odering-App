package com.example.mayankaggarwal.dcare.utils;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.mayankaggarwal.dcare.R;

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

        final Button deliver, cancel, returned, continueDeliver, continueCancel;
        final LinearLayout deliveryLayout, returnLayout;
        final ImageView starone, startwo, starthree, starfour, starfive;
        final Spinner reasons;
        List<String> reasonList;
        ArrayAdapter<String> reasonAdapter;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final View view = activity.getLayoutInflater().inflate(R.layout.alert_feedback_order, null);
        deliver = (Button) view.findViewById(R.id.delivered);
        cancel = (Button) view.findViewById(R.id.cancelled);
        returned = (Button) view.findViewById(R.id.returned);
        continueCancel = (Button) view.findViewById(R.id.continuedialog2);
        continueDeliver = (Button) view.findViewById(R.id.continuedialog1);
        reasons = (Spinner) view.findViewById(R.id.reasonspinner);
        deliveryLayout = (LinearLayout) view.findViewById(R.id.deliverlayout);
        returnLayout = (LinearLayout) view.findViewById(R.id.cancelretunlayout);
        starone = (ImageView) view.findViewById(R.id.starone);
        startwo = (ImageView) view.findViewById(R.id.startwo);
        starthree = (ImageView) view.findViewById(R.id.starthree);
        starfour = (ImageView) view.findViewById(R.id.starfour);
        starfive = (ImageView) view.findViewById(R.id.starfive);


        //initial setup
        returnLayout.setVisibility(View.GONE);
        deliveryLayout.setVisibility(View.GONE);


        reasonList = new ArrayList<>();
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

        continueCancel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        continueCancel.setBackgroundResource(R.drawable.round_shape_solid_grey);
                        continueCancel.setTextColor(activity.getResources().getColor(R.color.white));
                        return false;
                    case MotionEvent.ACTION_UP:
                        continueCancel.setBackgroundResource(R.drawable.round_shape_border_grey);
                        continueCancel.setTextColor(activity.getResources().getColor(R.color.dark_grey));
                        return false;
                }
                return false;
            }
        });

        continueDeliver.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        continueDeliver.setBackgroundResource(R.drawable.round_shape_solid_grey);
                        continueDeliver.setTextColor(activity.getResources().getColor(R.color.white));
                        return false;
                    case MotionEvent.ACTION_UP:
                        continueDeliver.setBackgroundResource(R.drawable.round_shape_border_grey);
                        continueDeliver.setTextColor(activity.getResources().getColor(R.color.dark_grey));
                        return false;
                }
                return false;
            }
        });

        deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorTab(cancel, returned, deliver, activity, view);
                deliveryLayout.setVisibility(View.VISIBLE);
                returnLayout.setVisibility(View.GONE);

                //setting stars
                setStarPattern(starone, startwo, starthree, starfour, starfive);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorTab(deliver, returned, cancel, activity, view);
                deliveryLayout.setVisibility(View.GONE);
                returnLayout.setVisibility(View.VISIBLE);
            }
        });

        returned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorTab(cancel, deliver, returned, activity, view);
                deliveryLayout.setVisibility(View.GONE);
                returnLayout.setVisibility(View.VISIBLE);
            }
        });

        continueCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueCancel.setBackgroundResource(R.drawable.round_shape_solid_grey);
                continueCancel.setTextColor(activity.getResources().getColor(R.color.white));
                feedbackAlert.dismiss();
            }
        });

        continueDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueDeliver.setBackgroundResource(R.drawable.round_shape_solid_grey);
                continueDeliver.setTextColor(activity.getResources().getColor(R.color.white));
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

    private static void setStarPattern(final ImageView starone, final ImageView startwo, final ImageView starthree, final ImageView starfour, final ImageView starfive) {

        starone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starone.setImageResource(R.drawable.selectstar);
                startwo.setImageResource(R.drawable.notselectstar);
                starthree.setImageResource(R.drawable.notselectstar);
                starfour.setImageResource(R.drawable.notselectstar);
                starfive.setImageResource(R.drawable.notselectstar);
            }
        });
        startwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starone.setImageResource(R.drawable.selectstar);
                startwo.setImageResource(R.drawable.selectstar);
                starthree.setImageResource(R.drawable.notselectstar);
                starfour.setImageResource(R.drawable.notselectstar);
                starfive.setImageResource(R.drawable.notselectstar);
            }
        });
        starthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starone.setImageResource(R.drawable.selectstar);
                startwo.setImageResource(R.drawable.selectstar);
                starthree.setImageResource(R.drawable.selectstar);
                starfour.setImageResource(R.drawable.notselectstar);
                starfive.setImageResource(R.drawable.notselectstar);
            }
        });
        starfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starone.setImageResource(R.drawable.selectstar);
                startwo.setImageResource(R.drawable.selectstar);
                starthree.setImageResource(R.drawable.selectstar);
                starfour.setImageResource(R.drawable.selectstar);
                starfive.setImageResource(R.drawable.notselectstar);
            }
        });
        starfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starone.setImageResource(R.drawable.selectstar);
                startwo.setImageResource(R.drawable.selectstar);
                starthree.setImageResource(R.drawable.selectstar);
                starfour.setImageResource(R.drawable.selectstar);
                starfive.setImageResource(R.drawable.selectstar);
            }
        });
    }


    private static void setColorTab(Button one, Button two, Button three, Activity activity, View view) {

        final String s_one = activity.getResources().getResourceEntryName(one.getId());
        final String s_two = activity.getResources().getResourceEntryName(two.getId());
        final String s_three = activity.getResources().getResourceEntryName(three.getId());

        checkingIDSBack(s_one, activity, view);
        checkingIDSBack(s_two, activity, view);
        checkingIDS(s_three, activity, view);
    }

    private static void checkingIDSBack(String string, Activity activity, View view) {
        final Button deliver, cancel, returned;
        deliver = (Button) view.findViewById(R.id.delivered);
        cancel = (Button) view.findViewById(R.id.cancelled);
        returned = (Button) view.findViewById(R.id.returned);
        if (string.equals("delivered")) {
            deliver.setBackgroundResource(R.drawable.round_shape_border_blue);
            deliver.setTextColor(activity.getResources().getColor(R.color.themeblue));
        } else if (string.equals("cancelled")) {
            cancel.setBackgroundResource(R.drawable.round_shape_border_orange);
            cancel.setTextColor(activity.getResources().getColor(R.color.themered));
        } else if (string.equals("returned")) {
            returned.setBackgroundResource(R.drawable.round_shape_border_grey);
            returned.setTextColor(activity.getResources().getColor(R.color.dark_grey));
        }
    }

    private static void checkingIDS(String string, Activity activity, View view) {
        final Button deliver, cancel, returned;
        deliver = (Button) view.findViewById(R.id.delivered);
        cancel = (Button) view.findViewById(R.id.cancelled);
        returned = (Button) view.findViewById(R.id.returned);
        if (string.equals("delivered")) {
            deliver.setBackgroundResource(R.drawable.round_solid_blue);
            deliver.setTextColor(activity.getResources().getColor(R.color.white));
        } else if (string.equals("cancelled")) {
            cancel.setBackgroundResource(R.drawable.round_shape_solid_invalid);
            cancel.setTextColor(activity.getResources().getColor(R.color.white));
        } else if (string.equals("returned")) {
            returned.setBackgroundResource(R.drawable.round_shape_solid_grey);
            returned.setTextColor(activity.getResources().getColor(R.color.white));
        }
    }

}
