package com.example.mayankaggarwal.dcare.utils;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.activities.OtpActivity;
import com.example.mayankaggarwal.dcare.rest.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayankaggarwal on 25/02/17.
 */

public class Globals {

    public static String lat;
    public static String lng;
    public static int validatedShift=0;

    public static String appVersion = "1.0.0";
    public static String appOS = "Android";

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String errorRes = "";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static String getCountryFlag(String countryCode) {
        int flagOffset = 0x1F1E6;
        int asciiOffset = 0x41;

//        String country = "US";

        String country = countryCode;

        int firstChar = Character.codePointAt(country, 0) - asciiOffset + flagOffset;
        int secondChar = Character.codePointAt(country, 1) - asciiOffset + flagOffset;

        String flag = new String(Character.toChars(firstChar))
                + new String(Character.toChars(secondChar));
        return flag;
    }

    public static void showProgressDialog(ProgressDialog dialog, String title, String message) {
        dialog.setCancelable(false);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.show();
    }

    public static void hideProgressDialog(ProgressDialog dialog) {
        dialog.hide();
    }

    public static void showFailAlert(Activity activity, String title) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(title);
        alert.setCancelable(false);
        alert.setMessage(Globals.errorRes);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog;
        dialog=alert.create();
        dialog.show();
        if (activity.isDestroyed() || activity.isFinishing()) {
            dialog.dismiss();
        }
    }

    public static List<Integer> threeMedia = new ArrayList<>();

    //    public static int ORDERSTATE_RECIEVED = 1;
    public static int ORDERSTATE_ACCEPTED = 2;
    public static int ORDERSTATE_UNASSIGNED = 3;
    public static int ORDERSTATE_ASSIGNED = 4;
    public static int ORDERSTATE_CREW_AKNOLEDGED = 5;
    public static int ORDERSTATE_PACKED = 6;
    public static int ORDERSTATE_IN_TRANSIT = 7;
    //public static int ORDER_END_STATE = 8;
    public static int ORDERSTATE_END_STATE_DELIVERED = 9;
    public static int ORDERSTATE_END_STATE_CANCELD = 10;
    public static int ORDERSTATE_END_STATE_RETURNED = 11;


}
