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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.rest.Data;
import com.example.mayankaggarwal.dcare.utils.Globals;
import com.google.firebase.analytics.FirebaseAnalytics;

public class OtpActivity extends AppCompatActivity {

    ProgressDialog progress;
    ProgressDialog otpProgress;
    EditText mobileedit;
    TextView mobiletext;
    Button getotpbutton;
    String mobileNumber;
    TextView countryCode;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setContentView(R.layout.activity_otp);
        progress = new ProgressDialog(this);
        otpProgress = new ProgressDialog(this);
        progress.setTitle("Booting Up");
        initalize();
    }

    private void initalize() {
        callBootingUp();
        hideKeyboard(findViewById(R.id.linearlayout));
        mobileedit = (EditText) findViewById(R.id.mobileedittext);
        getotpbutton = (Button) findViewById(R.id.getotpbutton);
        mobiletext = (TextView) findViewById(R.id.mobiletext);
        countryCode = (TextView) findViewById(R.id.countrycode);


        if (getIntent() != null) {
            String number = getIntent().getStringExtra("number");
            String code = getIntent().getStringExtra("code");
            if (number != null && code != null) {
                countryCode.setText(Globals.getCountryFlag(code) + " (" + code + ") +" + number + "▾");
            } else {
                countryCode.setText(Globals.getCountryFlag("IN") + " (IN) +91 ▾");
            }
        } else {
            countryCode.setText(Globals.getCountryFlag("IN") + " (IN) +91 ▾");
        }

        countryCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtpActivity.this, SearchCountry.class));
            }
        });

        mobileedit.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (keyboardShown(mobileedit.getRootView())) {
//                    Log.d("keyboard", "keyboard UP");
                    mobiletext.setTextColor(Color.parseColor("#00bcd4"));
                } else {
//                    Log.d("keyboard", "keyboard Down");
                    mobiletext.setTextColor(Color.parseColor("#d2d2d2"));
                }
            }
        });

        mobileedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 10) {
                    getotpbutton.setBackgroundResource(R.drawable.round_shape_border_blue);
                    getotpbutton.setTextColor(Color.parseColor("#00bcd4"));
                    getotpbutton.setEnabled(true);
                    mobileNumber = mobileedit.getText().toString();
                } else {
                    getotpbutton.setTextColor(Color.parseColor("#d2d2d2"));
                    getotpbutton.setBackgroundResource(R.drawable.round_shape_border_grey);
                    getotpbutton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getotpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent() != null) {
                    String code = getIntent().getStringExtra("number");
                    if (code != null) {
                        getOTP(mobileNumber, code);
                    }
                } else {
                    getOTP(mobileNumber, "91");
                }
            }
        });

    }

    private void getOTP(String mobileNumber, String countryCode) {
        Globals.showProgressDialog(otpProgress, "OTP", "Fetching...");
        Data.getOTP(mobileNumber, countryCode, this, new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                Globals.hideProgressDialog(otpProgress);
//                Log.d("tagg","success otp");
                (OtpActivity.this).startActivity(new Intent(OtpActivity.this, VerifyOtp.class));
            }

            @Override
            public void onFailure() {
                Globals.hideProgressDialog(otpProgress);
                Globals.showFailAlert(OtpActivity.this, "Error OTP");
            }
        });
    }

    private void callBootingUp() {
        progress.show();
        Data.bootup(this, new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                progress.hide();
            }

            @Override
            public void onFailure() {
                progress.hide();
                Globals.showFailAlert(OtpActivity.this, "Error Booting");
                finish();
            }
        });
    }

    private void hideKeyboard(View root) {
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    View view = v.getRootView().findFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) OtpActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    return true;
                }
                return true;
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

}
