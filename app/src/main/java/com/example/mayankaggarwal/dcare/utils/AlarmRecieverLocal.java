package com.example.mayankaggarwal.dcare.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.mayankaggarwal.dcare.fragments.StartedShift;

/**
 * Created by mayankaggarwal on 09/06/17.
 */

public class AlarmRecieverLocal extends BroadcastReceiver {

    private static final String BOOT_COMPLETED =
            "android.intent.action.BOOT_COMPLETED";
    private static final String QUICKBOOT_POWERON =
            "android.intent.action.QUICKBOOT_POWERON";


    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()) ||
                "android.intent.action.QUICKBOOT_POWERON".equals(intent.getAction())) {
            //reset alarm on reboot
            StartedShift.scheduleLocalShiftAlarm(context);
        } else {
            Prefs.setPrefs("last_loacal_timestamp",String.valueOf((int) System.currentTimeMillis() / 1000),context);
            StartedShift.scheduleLocalShiftAlarm(context);
        }
    }

}
