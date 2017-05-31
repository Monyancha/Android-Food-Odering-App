package com.example.mayankaggarwal.dcare.utils;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.mayankaggarwal.dcare.activities.OtpActivity;
import com.example.mayankaggarwal.dcare.rest.Data;

/**
 * Created by mayankaggarwal on 25/02/17.
 */

public class Globals {

    public static String appVersion = "1.0.0";
    public static String appOS = "Android";

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String errorRes="";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static String getCountryFlag(String countryCode){
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

    public static void showProgressDialog(ProgressDialog dialog, String title, String message){
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.show();
    }

    public static void hideProgressDialog(ProgressDialog dialog){
        dialog.hide();
    }

    public static void showFailAlert(Activity activity, String title){
        AlertDialog dialog=new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(Globals.errorRes).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

}
