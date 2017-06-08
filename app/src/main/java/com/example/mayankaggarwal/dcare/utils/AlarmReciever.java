package com.example.mayankaggarwal.dcare.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.mayankaggarwal.dcare.fragments.StartedShift;
import com.example.mayankaggarwal.dcare.rest.Data;
import com.google.gson.JsonParser;

/**
 * Created by mayankaggarwal on 12/04/17.
 */

public class AlarmReciever extends BroadcastReceiver {


    private static final String BOOT_COMPLETED =
            "android.intent.action.BOOT_COMPLETED";
    private static final String QUICKBOOT_POWERON =
            "android.intent.action.QUICKBOOT_POWERON";


    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()) ||
                "android.intent.action.QUICKBOOT_POWERON".equals(intent.getAction())) {
            //reset alarm on reboot
            StartedShift.scheduleShiftAlarm(context);
        } else {
            if (!(Prefs.getPrefs("vendor_id_selected", context).equals("notfound")) && !(Prefs.getPrefs("shift_id", context).equals("notfound"))) {
                Data.shiftLive(context, Prefs.getPrefs("vendor_id_selected", context), Prefs.getPrefs("shift_id", context), new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
//                        Log.d("tagg", "success alarm");
                    }

                    @Override
                    public void onFailure() {
                    }
                });
            }
            StartedShift.scheduleShiftAlarm(context);
        }
    }
}
