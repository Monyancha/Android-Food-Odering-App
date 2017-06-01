package com.example.mayankaggarwal.dcare.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by mayankaggarwal on 22/03/17.
 */

public class CalenderSetting {

    public static void setCalendar(Activity activity, EditText editText) {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(activity,
                new DateListener(editText), mYear, mMonth, mDay);
        dialog.show();
    }
}
