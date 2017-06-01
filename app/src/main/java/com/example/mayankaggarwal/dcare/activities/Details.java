package com.example.mayankaggarwal.dcare.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.MediaCodec;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.utils.CalenderSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Details extends AppCompatActivity {

    EditText fname, email, mobile, nickname, dob, ssn, dmv;
    Spinner sex;
    TextView fnametext, sextext, mobiletext, emailtext, nicknametext, dobtext, ssntext, dmvtext;
    ImageView firstUpload, secondUpload, thirdUpload, datepicker;
    Boolean validEmail = false, validDate = false;
    Button proceed, back;
    String sexSelected=null;
    public List<String> sexList=new ArrayList<>();
    ArrayAdapter<String> sexAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        intialize();
    }

    private void intialize() {

        hideKeyboard(findViewById(R.id.linearlayout));

        fnametext = (TextView) findViewById(R.id.fullnametext);
        sextext = (TextView) findViewById(R.id.sextext);
        emailtext = (TextView) findViewById(R.id.emailtext);
        nicknametext = (TextView) findViewById(R.id.nicknametext);
        dobtext = (TextView) findViewById(R.id.dobtext);
        ssntext = (TextView) findViewById(R.id.ssntext);
        dmvtext = (TextView) findViewById(R.id.dmvtext);
        mobiletext = (TextView) findViewById(R.id.mobiletext);


        fname = (EditText) findViewById(R.id.fullname);
        email = (EditText) findViewById(R.id.email);
        nickname = (EditText) findViewById(R.id.nickname);
        dob = (EditText) findViewById(R.id.dob);
        ssn = (EditText) findViewById(R.id.ssn);
        dmv = (EditText) findViewById(R.id.dmv);
        mobile = (EditText) findViewById(R.id.mobile);

        firstUpload = (ImageView) findViewById(R.id.firstmedia);
        secondUpload = (ImageView) findViewById(R.id.secondmedia);
        thirdUpload = (ImageView) findViewById(R.id.thirdmedia);
        datepicker = (ImageView) findViewById(R.id.datepicker);

        proceed = (Button) findViewById(R.id.proceed);
        back = (Button) findViewById(R.id.backpage);

        sex = (Spinner) findViewById(R.id.sex);

        sexList.add("Male");
        sexList.add("Female");
        sexAdapter = new ArrayAdapter<String>(Details.this, R.layout.support_simple_spinner_dropdown_item, sexList);
        sex.setAdapter(sexAdapter);

        settingLabels();

        settingListener();

    }

    private void settingListener() {

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderSetting.setCalendar(Details.this, dob);
            }
        });

        sex.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    sextext.setTextColor(Color.parseColor("#00bcd4"));
                }
                return false;
            }
        });

        sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sexSelected=sexList.get(position);
                sextext.setTextColor(Color.parseColor("#d2d2d2"));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sexSelected=null;
                sextext.setTextColor(Color.parseColor("#d2d2d2"));
            }
        });
    }

    private void settingLabels() {
        getFocusMode(fname, fnametext);
        getFocusMode(email, emailtext);
        getFocusMode(nickname, nicknametext);
        getFocusMode(dob, dobtext);
        getFocusMode(ssn, ssntext);
        getFocusMode(dmv, dmvtext);
        getFocusMode(mobile, mobiletext);
    }

    private void getFocusMode(final EditText edittext, final TextView textview) {

        // getting id of edittext
        final String s = getResources().getResourceEntryName(edittext.getId());

        edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (s.equals("email")) {
                    if (hasFocus) {
                        textview.setTextColor(Color.parseColor("#00bcd4"));
                    } else {
                        textview.setTextColor(Color.parseColor("#d2d2d2"));

                        //validating email
                        Boolean validornot = validateEmail(email.getText().toString());
                        validEmail = validornot;
                        if (validornot) {
                            edittext.setBackgroundResource(R.drawable.round_shape_solid_grey);
                        } else {
                            edittext.setBackgroundResource(R.drawable.round_shape_solid_invalid);
                        }
                    }
                } else if (s.equals("dob")) {
                    if (hasFocus) {
                        textview.setTextColor(Color.parseColor("#00bcd4"));
                    } else {
                        textview.setTextColor(Color.parseColor("#d2d2d2"));

                        //validating email
                        Boolean validornot = validateDOB(dob.getText().toString());
                        validDate = validornot;
                        if (validornot) {
                            edittext.setBackgroundResource(R.drawable.round_shape_solid_grey);
                        } else {
                            edittext.setBackgroundResource(R.drawable.round_shape_solid_invalid);
                        }
                    }
                } else if (s.equals("mobile")) {
                    if (hasFocus) {
                        textview.setTextColor(Color.parseColor("#00bcd4"));
                    } else {
                        textview.setTextColor(Color.parseColor("#d2d2d2"));

                        //validating email
                        Boolean validornot = validateMobile(mobile.getText().toString());
                        validDate = validornot;
                        if (validornot) {
                            edittext.setBackgroundResource(R.drawable.round_shape_solid_grey);
                        } else {
                            edittext.setBackgroundResource(R.drawable.round_shape_solid_invalid);
                        }
                    }
                }
                else {
                    if (hasFocus) {
                        textview.setTextColor(Color.parseColor("#00bcd4"));
                    } else {
                        textview.setTextColor(Color.parseColor("#d2d2d2"));
                    }
                }

            }
        });
    }

    public Boolean validateEmail(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }


    public Boolean validateDOB(String dob) {
        String ePattern = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(dob);
        return m.matches();
    }

    public Boolean validateMobile(String mobile) {
        if(mobile!=null && mobile.length()==10){
            return true;
        }else {
            return false;
        }
    }

    private void hideKeyboard(View root) {
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    View view = v.getRootView().findFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) Details.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    return true;
                }
                return true;
            }
        });
    }

}
