package com.example.mayankaggarwal.dcare.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.content.res.AppCompatResources;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.activities.Details;
import com.example.mayankaggarwal.dcare.activities.OtpActivity;
import com.example.mayankaggarwal.dcare.utils.CalenderSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    EditText fname, email, mobile, nickname, dob, ssn, dmv;
    Spinner sex;
    TextView fnametext, sextext, mobiletext, emailtext, nicknametext, dobtext, ssntext, dmvtext;
    ImageView firstUpload, secondUpload, thirdUpload, datepicker,uploadsymbolOne,uploadsymbolTwo;
    Boolean validEmail = false, validDate = false,validMobile=false;
    Button proceed;
    String sexSelected=null;
    LinearLayout dateincorrect,emailincorrect,mobileincorrect;
    public List<String> sexList=new ArrayList<>();
    ArrayAdapter<String> sexAdapter;

    public static EditProfileFragment newInstance() {
        EditProfileFragment fragment = new EditProfileFragment();
        return fragment;
    }
    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.fragment_edit_profile, container, false);
        intialize(view);
        return view;
    }


    private void intialize(View view) {

        hideKeyboard(view.findViewById(R.id.linearlayout));

        fnametext = (TextView) view.findViewById(R.id.fullnametext);
        sextext = (TextView) view.findViewById(R.id.sextext);
        emailtext = (TextView) view.findViewById(R.id.emailtext);
        nicknametext = (TextView) view.findViewById(R.id.nicknametext);
        dobtext = (TextView) view.findViewById(R.id.dobtext);
        ssntext = (TextView) view.findViewById(R.id.ssntext);
        dmvtext = (TextView) view.findViewById(R.id.dmvtext);
        mobiletext = (TextView) view.findViewById(R.id.mobiletext);


        fname = (EditText) view.findViewById(R.id.fullname);
        email = (EditText) view.findViewById(R.id.email);
        nickname = (EditText) view.findViewById(R.id.nickname);
        dob = (EditText) view.findViewById(R.id.dob);
        ssn = (EditText) view.findViewById(R.id.ssn);
        dmv = (EditText) view.findViewById(R.id.dmv);
        mobile = (EditText) view.findViewById(R.id.mobile);

        firstUpload = (ImageView) view.findViewById(R.id.firstmedia);
        secondUpload = (ImageView) view.findViewById(R.id.secondmedia);
        thirdUpload = (ImageView) view.findViewById(R.id.thirdmedia);
        datepicker = (ImageView) view.findViewById(R.id.datepicker);
        uploadsymbolOne=(ImageView)view.findViewById(R.id.uploadsymbolone);
        uploadsymbolTwo=(ImageView)view.findViewById(R.id.uploadsymboltwo);

        proceed = (Button) view.findViewById(R.id.proceed);
//        back = (Button) view.findViewById(R.id.backpage);
//        back.setBackgroundResource(R.drawable.round_shape_border_grey);
//        back.setTextColor(Color.parseColor("#d2d2d2"));

        dateincorrect=(LinearLayout)view.findViewById(R.id.dateincorrect);
        emailincorrect=(LinearLayout)view.findViewById(R.id.emailincorrect);
        mobileincorrect=(LinearLayout)view.findViewById(R.id.mobileincorrect);

        sex = (Spinner) view.findViewById(R.id.sex);

        sexList.add("Male");
        sexList.add("Female");
        sexAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, sexList);
        sex.setAdapter(sexAdapter);

        settingLabels();

        settingListener();

    }

    private void settingListener() {

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setting proceed button
                Boolean allFieldsValidity=validAllFields();

                if(allFieldsValidity){
                    proceed.setBackgroundResource(R.drawable.round_shape_border_blue);
                    proceed.setTextColor(getResources().getColor(R.color.themeblue));
                    sendDetails();

                }else {
                    proceed.setBackgroundResource(R.drawable.round_shape_border_grey);
                    proceed.setTextColor(getResources().getColor(R.color.themegrey));
                }
            }
        });

//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                back.setBackgroundResource(R.drawable.round_shape_border_blue);
//                back.setTextColor(Color.parseColor("#00bcd4"));
//                startActivity(new Intent(getContext(), OtpActivity.class));
//            }
//        });

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderSetting.setCalendar(getActivity(), dob);
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
                sextext.setTextColor(Color.parseColor("#00bcd4"));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sexSelected=null;
                sextext.setTextColor(Color.parseColor("#d2d2d2"));
            }
        });
    }

    private void sendDetails() {
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

                sextext.setTextColor(Color.parseColor("#d2d2d2"));

                //setting proceed button
                Boolean allFieldsValidity=validAllFields();

                if(allFieldsValidity){
                    proceed.setBackgroundResource(R.drawable.round_shape_border_blue);
                    proceed.setTextColor(getResources().getColor(R.color.themeblue));
                }else {
                    proceed.setBackgroundResource(R.drawable.round_shape_border_grey);
                    proceed.setTextColor(getResources().getColor(R.color.themegrey));
                }
                //setting focus validity on other fields
                if (s.equals("email")) {
                    if (hasFocus) {
                        textview.setTextColor(Color.parseColor("#00bcd4"));
                    } else {
                        textview.setTextColor(Color.parseColor("#d2d2d2"));

                        //validating email
                        Boolean validornot = validateEmail(email.getText().toString());
                        validEmail = validornot;
                        if (validornot) {
                            emailincorrect.setVisibility(View.GONE);
//                            edittext.setBackgroundResource(R.drawable.round_shape_solid_grey);
//                            edittext.setTextColor(Color.parseColor("#000000"));
                        } else {
                            emailincorrect.setVisibility(View.VISIBLE);
                            edittext.setBackgroundResource(R.drawable.round_shape_solid_invalid);
                            edittext.setTextColor(Color.parseColor("#ffffff"));
                        }
                    }
                } else if (s.equals("dob")) {
                    if (hasFocus) {
                        textview.setTextColor(Color.parseColor("#00bcd4"));
                        setBlueTint(datepicker);
                    } else {
                        setGreyTint(datepicker);
                        textview.setTextColor(Color.parseColor("#d2d2d2"));
                        //validating email
                        Boolean validornot = validateDOB(dob.getText().toString());
                        validDate = validornot;
                        if (validornot) {
                            dateincorrect.setVisibility(View.GONE);
//                            edittext.setBackgroundResource(R.drawable.round_shape_solid_grey);
//                            edittext.setTextColor(Color.parseColor("#000000"));

                        } else {
                            dateincorrect.setVisibility(View.VISIBLE);
                            edittext.setBackgroundResource(R.drawable.round_shape_solid_invalid);
                            edittext.setTextColor(Color.parseColor("#ffffff"));
                        }
                    }
                } else if (s.equals("mobile")) {
                    if (hasFocus) {
                        textview.setTextColor(Color.parseColor("#00bcd4"));
                    } else {
                        textview.setTextColor(Color.parseColor("#d2d2d2"));

                        //validating email
                        Boolean validornot = validateMobile(mobile.getText().toString());
                        validMobile = validornot;
                        if (validornot) {
                            mobileincorrect.setVisibility(View.GONE);
//                            edittext.setBackgroundResource(R.drawable.round_shape_solid_grey);
//                            edittext.setTextColor(Color.parseColor("#000000"));
                        } else {
                            mobileincorrect.setVisibility(View.VISIBLE);
                            edittext.setBackgroundResource(R.drawable.round_shape_solid_invalid);
                            edittext.setTextColor(Color.parseColor("#ffffff"));
                        }
                    }
                }else if(s.equals("ssn")){
                    if (hasFocus) {
                        setBlueTint(uploadsymbolOne);
                        textview.setTextColor(Color.parseColor("#00bcd4"));
                    } else {
                        setGreyTint(uploadsymbolOne);
                        textview.setTextColor(Color.parseColor("#d2d2d2"));
                    }
                }else if(s.equals("dmv")){
                    if (hasFocus) {
                        setBlueTint(uploadsymbolTwo);
                        textview.setTextColor(Color.parseColor("#00bcd4"));
                    } else {
                        setGreyTint(uploadsymbolTwo);
                        textview.setTextColor(Color.parseColor("#d2d2d2"));
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

    private void setBlueTint(ImageView imageView) {
        ColorStateList csl = AppCompatResources.getColorStateList(getContext(), R.color.themeblue);
        Drawable drawable = DrawableCompat.wrap(imageView.getDrawable());
        DrawableCompat.setTintList(drawable, csl);
        imageView.setImageDrawable(drawable);
    }

    private void setGreyTint(ImageView imageView) {
        ColorStateList csl = AppCompatResources.getColorStateList(getContext(), R.color.themegrey);
        Drawable drawable = DrawableCompat.wrap(imageView.getDrawable());
        DrawableCompat.setTintList(drawable, csl);
        imageView.setImageDrawable(drawable);
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

    public Boolean validAllFields(){
        if(fname.getText().toString()!=null && fname.getText().toString().trim().length()>1){
            if(validEmail && validDate && validMobile ){
                if(nickname.getText().toString()!=null && nickname.getText().toString().trim().length()>1){
                    if(ssn.getText().toString()!=null && ssn.getText().toString().trim().length()>1){
                        if(dmv.getText().toString()!=null && dmv.getText().toString().trim().length()>1){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void hideKeyboard(View root) {
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    View view = v.getRootView().findFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    return true;
                }
                return true;
            }
        });
    }

}
