package com.example.mayankaggarwal.dcare.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.rest.Data;
import com.example.mayankaggarwal.dcare.utils.Globals;

public class VerifyOtp extends AppCompatActivity {

    TextView otptext;
    EditText otpedit;
    Button verfiy,back;
    CheckBox tc;
    Boolean checked=false;
    CharSequence otp;
    ProgressDialog sendProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        sendProgress=new ProgressDialog(this);
        initalize();
    }

    private void initalize() {
        hideKeyboard(findViewById(R.id.linearlayout));
        otptext=(TextView)findViewById(R.id.otptext);
        otpedit=(EditText)findViewById(R.id.otpedittext);
        verfiy=(Button)findViewById(R.id.verifybutton);

        back=(Button)findViewById(R.id.backbutton);
        back.setBackgroundResource(R.drawable.round_shape_border_grey);
        back.setTextColor(Color.parseColor("#d2d2d2"));

        tc=(CheckBox)findViewById(R.id.tandc);
        verfiy.setBackgroundResource(R.drawable.round_shape_border_grey);
        verfiy.setEnabled(false);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setBackgroundResource(R.drawable.round_shape_border_blue);
                back.setTextColor(Color.parseColor("#00bcd4"));
                (VerifyOtp.this).startActivity(new Intent(VerifyOtp.this, OtpActivity.class));
            }
        });
        
        verfiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tagg","working");
                sendOtp(otp);
            }
        });


        tc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checked=true;
                    if(otp!=null && otp.length()>1){
                        verfiy.setBackgroundResource(R.drawable.round_shape_border_blue);
                        verfiy.setEnabled(true);
                        verfiy.setTextColor(Color.parseColor("#00bcd4"));
                    }else {
                        verfiy.setEnabled(false);
                        verfiy.setBackgroundResource(R.drawable.round_shape_border_grey);
                        verfiy.setTextColor(Color.parseColor("#d2d2d2"));
                    }
                }else {
                    checked=false;
                    verfiy.setEnabled(false);
                    verfiy.setBackgroundResource(R.drawable.round_shape_border_grey);
                    verfiy.setTextColor(Color.parseColor("#d2d2d2"));
                }
            }
        });

        otpedit.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (keyboardShown(otpedit.getRootView())) {
//                    Log.d("keyboard", "keyboard UP");
                    otptext.setTextColor(Color.parseColor("#00bcd4"));
                } else {
//                    Log.d("keyboard", "keyboard Down");
                    otptext.setTextColor(Color.parseColor("#d2d2d2"));
                }
            }
        });

        otpedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                otp=s;
                if(checked){
                    if(otp!=null && otp.length()>1){
                        verfiy.setBackgroundResource(R.drawable.round_shape_border_blue);
                        verfiy.setEnabled(true);
                        verfiy.setTextColor(Color.parseColor("#00bcd4"));
                    }else {
                        verfiy.setEnabled(false);
                        verfiy.setBackgroundResource(R.drawable.round_shape_border_grey);
                        verfiy.setTextColor(Color.parseColor("#d2d2d2"));
                    }
                }else {
                    verfiy.setEnabled(false);
                    verfiy.setBackgroundResource(R.drawable.round_shape_border_grey);
                    verfiy.setTextColor(Color.parseColor("#d2d2d2"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void sendOtp(CharSequence otp) {
        Globals.showProgressDialog(sendProgress, "OTP", "Verfying...");
        Data.sendOTP(otp.toString(), this, new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                Globals.hideProgressDialog(sendProgress);
                finishAffinity();
                startActivity(new Intent(VerifyOtp.this, Details.class));
            }
            @Override
            public void onFailure() {
                Globals.hideProgressDialog(sendProgress);
                Globals.showFailAlert(VerifyOtp.this, "Error Verifying OTP");
            }
        });
    }

    private boolean keyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }

    private void hideKeyboard(View root) {
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    View view = v.getRootView().findFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)VerifyOtp.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    return true;
                }
                return true;
            }
        });
    }


}
