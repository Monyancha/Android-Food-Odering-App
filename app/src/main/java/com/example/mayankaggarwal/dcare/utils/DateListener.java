package com.example.mayankaggarwal.dcare.utils;

import android.app.DatePickerDialog;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mayankaggarwal.dcare.rest.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mayankaggarwal on 22/03/17.
 */

public class DateListener implements DatePickerDialog.OnDateSetListener {
    EditText editText;

    public DateListener(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String myDate=getDayInFormat(year,month+1,dayOfMonth);

        if(myDate!=null){
            editText.setText(myDate);
        }
    }

    public static String getDayInFormat(int year, int month, int dayOfMonth) {
        String dateString = String.format("%d-%d-%d", year, month, dayOfMonth);
        Date date;
        String newdate="";
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            newdate=format1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newdate;
    }
}
