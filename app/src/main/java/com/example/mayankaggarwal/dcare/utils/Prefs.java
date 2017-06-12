package com.example.mayankaggarwal.dcare.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mayankaggarwal on 12/02/17.
 */

public class Prefs {

    public static void setPrefs(String key, String value, Context context){
        try{
            SharedPreferences sharedpreferences = context.getSharedPreferences("Dcare", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(key, value);
            editor.apply();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getPrefs(String key, Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("Dcare", Context.MODE_PRIVATE);
        return sharedpreferences.getString(key, "notfound");
    }

    public static void deletePrefs(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("Dcare", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
    }
}
