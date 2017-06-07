package com.example.mayankaggarwal.dcare.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.rest.Data;
import com.example.mayankaggarwal.dcare.utils.CalenderSetting;
import com.example.mayankaggarwal.dcare.utils.Globals;
import com.example.mayankaggarwal.dcare.utils.ImagePath;
import com.example.mayankaggarwal.dcare.utils.Prefs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Details extends AppCompatActivity {

    EditText fname, email, mobile, nickname, dob, ssn, dmv;
    Spinner sex;
    TextView fnametext, sextext, mobiletext, emailtext, nicknametext, dobtext, ssntext, dmvtext;
    ImageView datepicker, uploadsymbolOne, uploadsymbolTwo;
    de.hdodenhof.circleimageview.CircleImageView firstUpload, secondUpload, thirdUpload;
    Boolean validEmail = false, validDate = false, validMobile = false;
    Button proceed, back;
    String sexSelected = null;
    String latitude = null;
    String longitude = null;
    LinearLayout dateincorrect, emailincorrect, mobileincorrect;
    public List<String> sexList = new ArrayList<>();
    ArrayAdapter<String> sexAdapter;
    ProgressBar profileprogress, ssnprogress, dmvprogress;

    private static final int REQUEST_PERMISSION = 1;
    private static final int img = 1;
    private static final int cap = 2;
    static Uri capturedImageUri = null;
    File file;
    ImageView alertImageview = null;
    public static boolean stopMediaUpload = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        intialize();
    }

    private void intialize() {

        hideKeyboard(findViewById(R.id.linearlayout));

        profileprogress = (ProgressBar) findViewById(R.id.profileprogress);
        ssnprogress = (ProgressBar) findViewById(R.id.ssnprogress);
        dmvprogress = (ProgressBar) findViewById(R.id.dmvprogress);

        profileprogress.setVisibility(View.GONE);
        ssnprogress.setVisibility(View.GONE);
        dmvprogress.setVisibility(View.GONE);

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

        firstUpload = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.firstmedia);
        secondUpload = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.secondmedia);
        thirdUpload = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.thirdmedia);

        datepicker = (ImageView) findViewById(R.id.datepicker);
        uploadsymbolOne = (ImageView) findViewById(R.id.uploadsymbolone);
        uploadsymbolTwo = (ImageView) findViewById(R.id.uploadsymboltwo);

        proceed = (Button) findViewById(R.id.proceed);
        back = (Button) findViewById(R.id.backpage);
        back.setBackgroundResource(R.drawable.round_shape_border_grey);
        back.setTextColor(Color.parseColor("#d2d2d2"));

        dateincorrect = (LinearLayout) findViewById(R.id.dateincorrect);
        emailincorrect = (LinearLayout) findViewById(R.id.emailincorrect);
        mobileincorrect = (LinearLayout) findViewById(R.id.mobileincorrect);

        sex = (Spinner) findViewById(R.id.sex);

        sexList.add("Male");
        sexList.add("Female");
        sexAdapter = new ArrayAdapter<String>(Details.this, R.layout.support_simple_spinner_dropdown_item, sexList);
        sex.setAdapter(sexAdapter);

        settingLabels();

        settingListener();

    }

    private void settingListener() {

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    sendDetails(Details.this);
            }
        });

        proceed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        proceed.setBackgroundResource(R.drawable.round_solid_blue);
                        proceed.setTextColor(getResources().getColor(R.color.white));
                        return false;
                    case MotionEvent.ACTION_UP:
                        proceed.setBackgroundResource(R.drawable.round_shape_border_blue);
                        proceed.setTextColor(getResources().getColor(R.color.themeblue));
                        return false;
                }
                return false;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setBackgroundResource(R.drawable.round_shape_border_blue);
                back.setTextColor(Color.parseColor("#00bcd4"));
                startActivity(new Intent(Details.this, OtpActivity.class));
            }
        });

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderSetting.setCalendar(Details.this, dob);
            }
        });

        sex.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sextext.setTextColor(Color.parseColor("#00bcd4"));
                }
                return false;
            }
        });

        sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sexSelected = sexList.get(position);
                sextext.setTextColor(Color.parseColor("#00bcd4"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sexSelected = null;
                sextext.setTextColor(Color.parseColor("#d2d2d2"));
            }
        });

        firstUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCameraAlert(Details.this, firstUpload);
            }
        });

        secondUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCameraAlert(Details.this, secondUpload);
            }
        });

        thirdUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCameraAlert(Details.this, thirdUpload);
            }
        });

        profileprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMediaUpload = true;
            }
        });

        ssnprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMediaUpload = true;
            }
        });

        dmvprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMediaUpload = true;
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

                sextext.setTextColor(Color.parseColor("#d2d2d2"));

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
                            edittext.setBackgroundResource(R.drawable.round_shape_solid_grey);
                            edittext.setTextColor(Color.parseColor("#000000"));
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
                            edittext.setBackgroundResource(R.drawable.round_shape_solid_grey);
                            edittext.setTextColor(Color.parseColor("#000000"));

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
                            edittext.setBackgroundResource(R.drawable.round_shape_solid_grey);
                            edittext.setTextColor(Color.parseColor("#000000"));
                        } else {
                            mobileincorrect.setVisibility(View.VISIBLE);
                            edittext.setBackgroundResource(R.drawable.round_shape_solid_invalid);
                            edittext.setTextColor(Color.parseColor("#ffffff"));
                        }
                    }
                } else if (s.equals("ssn")) {
                    if (hasFocus) {
                        setBlueTint(uploadsymbolOne);
                        textview.setTextColor(Color.parseColor("#00bcd4"));
                    } else {
                        setGreyTint(uploadsymbolOne);
                        textview.setTextColor(Color.parseColor("#d2d2d2"));
                    }
                } else if (s.equals("dmv")) {
                    if (hasFocus) {
                        setBlueTint(uploadsymbolTwo);
                        textview.setTextColor(Color.parseColor("#00bcd4"));
                    } else {
                        setGreyTint(uploadsymbolTwo);
                        textview.setTextColor(Color.parseColor("#d2d2d2"));
                    }
                } else {
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
        ColorStateList csl = AppCompatResources.getColorStateList(Details.this, R.color.themeblue);
        Drawable drawable = DrawableCompat.wrap(imageView.getDrawable());
        DrawableCompat.setTintList(drawable, csl);
        imageView.setImageDrawable(drawable);
    }

    private void setGreyTint(ImageView imageView) {
        ColorStateList csl = AppCompatResources.getColorStateList(Details.this, R.color.themegrey);
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
        if (mobile != null && mobile.length() == 10) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean validAllFields() {
        validDate = validateDOB(dob.getText().toString());
        validMobile = validateMobile(mobile.getText().toString());
        validEmail = validateEmail(email.getText().toString());
        if (fname.getText().toString().trim().length() > 1) {
            if (validEmail && validDate && validMobile) {
                if (nickname.getText().toString().trim().length() > 1) {
                    if (ssn.getText().toString().trim().length() > 1) {
                        if (dmv.getText().toString().trim().length() > 1) {
                            if (Globals.threeMedia == 3) {
                                return true;
                            }
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
                        InputMethodManager imm = (InputMethodManager) Details.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    return true;
                }
                return true;
            }
        });
    }

    public void showCameraAlert(final Activity activity, ImageView imageView) {
        final AlertDialog alertDialog;
        this.alertImageview = imageView;
        TextView camera, gallery;
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Select type:");
        View v = activity.getLayoutInflater().inflate(R.layout.camera_alert_layout, null);
        camera = (TextView) v.findViewById(R.id.camera);
        gallery = (TextView) v.findViewById(R.id.gallery);
        builder.setView(v);
        builder.setCancelable(true);
        alertDialog = builder.create();
        alertDialog.show();

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                            REQUEST_PERMISSION);
                    return;
                }
                Calendar cal = Calendar.getInstance();
                File dir = new File(Environment.getExternalStorageDirectory() + "/DCare");
                if (!dir.exists()) {
                    dir.mkdir();
                }
                file = new File(dir, (cal.getTimeInMillis() + ".jpg"));
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    file.delete();
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                capturedImageUri = Uri.fromFile(file);
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, capturedImageUri);
                startActivityForResult(intent, cap);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION);
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), img);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == img && resultCode == RESULT_OK && data != null && data.getData() != null) {
            long length = getImageSize(data.getData().toString());
            Bitmap photo;
            Bitmap compressPhoto = null;
            Boolean compress = false;
            try {
                photo = MediaStore.Images.Media.getBitmap(getContentResolver()
                        , data.getData());
                if (photo != null) {
                    if (length >= 1048570) {
                        compressPhoto = getResizedBitmap(photo, 300, 300);
                        compress = true;
                    } else {
                        compress = false;
                    }
                    uploadPhoto(this.alertImageview, data.getData(), photo, compressPhoto, compress);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == cap && resultCode == RESULT_OK && data != null && data.getData() != null) {
            long length = getImageSize(capturedImageUri.toString());
            Bitmap photo;
            Bitmap compressPhoto = null;
            Boolean compress = false;
            try {
                photo = MediaStore.Images.Media.getBitmap(getContentResolver()
                        , capturedImageUri);
                if (photo != null) {
                    if (length >= 1048570) {
                        compressPhoto = getResizedBitmap(photo, 300, 300);
                        compress = true;
                    } else {
                        compress = false;
                    }
                    uploadPhoto(this.alertImageview, capturedImageUri, photo, compressPhoto, compress);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void uploadPhoto(ImageView imageView, Uri imageUri, final Bitmap photo, final Bitmap compressPhoto, final Boolean compress) {
        final String s = getResources().getResourceEntryName(imageView.getId());
        String extension = getMimeType(this, imageUri);
        long file_size = getImageSize(imageUri.toString());
        int file_size_inMB = (int) (file_size / 1048576);
        byte[] imageArray = getByteArrayOfImage(photo);
        String media_data = getBase64EncodingString(imageArray);
        byte[] hash = getMD5HashString(imageArray);
//        String hashString = new String(hash);

        StringBuffer hashString = new StringBuffer();
        for (int i = 0; i < hash.length; i++)
            hashString.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));


        if (extension.length() != 0 && file_size != 0) {
            if (s.equals("firstmedia")) {
                profileprogress.setVisibility(View.VISIBLE);
                Data.uploadPhoto(String.valueOf(file_size_inMB), "profile." + extension, media_data,
                        hashString, extension, "profile", this, new Data.UpdateCallback() {
                            @Override
                            public void onUpdate() {
                                profileprogress.setVisibility(View.GONE);
                                Globals.threeMedia++;
                                if (compress) {
                                    if (compressPhoto != null)
                                        alertImageview.setImageBitmap(compressPhoto);
                                } else {
                                    if (photo != null)
                                        alertImageview.setImageBitmap(photo);
                                }
                                alertImageview.setEnabled(false);
                                stopMediaUpload = false;
                            }

                            @Override
                            public void onFailure() {
                                profileprogress.setVisibility(View.GONE);
                                stopMediaUpload = false;
                                Globals.showFailAlert(Details.this, "Error Uploading");
                            }
                        });
            } else if (s.equals("secondmedia")) {
                ssnprogress.setVisibility(View.VISIBLE);
                Data.uploadPhoto(String.valueOf(file_size_inMB), "ssn." + extension, media_data,
                        hashString, extension, "ssn", this, new Data.UpdateCallback() {
                            @Override
                            public void onUpdate() {
                                ssnprogress.setVisibility(View.GONE);
                                Globals.threeMedia++;
                                if (compress) {
                                    if (compressPhoto != null)
                                        alertImageview.setImageBitmap(compressPhoto);
                                } else {
                                    if (photo != null)
                                        alertImageview.setImageBitmap(photo);
                                }
                                alertImageview.setEnabled(false);
                                stopMediaUpload = false;
                            }

                            @Override
                            public void onFailure() {
                                ssnprogress.setVisibility(View.GONE);
                                stopMediaUpload = false;
                                Globals.showFailAlert(Details.this, "Error Uploading");
                            }
                        });
            } else if (s.equals("thirdmedia")) {
                dmvprogress.setVisibility(View.VISIBLE);
                Data.uploadPhoto(String.valueOf(file_size_inMB), "dmv." + extension, media_data,
                        hashString, extension, "dmv", this, new Data.UpdateCallback() {
                            @Override
                            public void onUpdate() {
                                dmvprogress.setVisibility(View.GONE);
                                Globals.threeMedia++;
                                if (compress) {
                                    if (compressPhoto != null)
                                        alertImageview.setImageBitmap(compressPhoto);
                                } else {
                                    if (photo != null)
                                        alertImageview.setImageBitmap(photo);
                                }
                                alertImageview.setEnabled(false);
                                stopMediaUpload = false;
                            }

                            @Override
                            public void onFailure() {
                                dmvprogress.setVisibility(View.GONE);
                                stopMediaUpload = false;
                                Globals.showFailAlert(Details.this, "Error Uploading Photo");
                            }
                        });
            }
        } else {
            Globals.errorRes = "Error in getting photo";
            Globals.showFailAlert(Details.this, "Error Uploading Photo");
        }
    }


    public void sendDetails(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION);
        } else {
            Boolean valid = validAllFields();
            if (valid) {
                String f_name;
                String m_name;
                String l_name;
                String dob_upload = dob.getText().toString();
                String nickname_upload = nickname.getText().toString();
                String mailid = email.getText().toString();
                String ssn_upload = ssn.getText().toString();
                String dmv_upload = dmv.getText().toString();
                getCurrentLocation();
                String sex;
                if (sexSelected.toLowerCase().equals("male")) {
                    sex = "M";
                } else {
                    sex = "F";
                }

                String name = fname.getText().toString();
                String names[] = name.split(" ");
                if (names.length == 3) {
                    f_name = names[0];
                    m_name = names[1];
                    l_name = names[2];
                } else if (names.length == 2) {
                    f_name = names[0];
                    m_name = "";
                    l_name = names[1];
                } else {
                    f_name = names[0];
                    m_name = "";
                    l_name = "";
                }
                Data.uploadProfile(f_name, m_name, l_name, sex, dob_upload, nickname_upload, mailid, ssn_upload, dmv_upload, latitude, longitude, this, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        Prefs.setPrefs("details_completed", "1", Details.this);
                        startActivity(new Intent(Details.this, MainActivity.class));
                    }

                    @Override
                    public void onFailure() {
                        Globals.showFailAlert(Details.this, "Error Uploading Profile");
                    }
                });
            } else {
                Globals.errorRes = "Fill all the fields or else upload all photos";
                Globals.showFailAlert(Details.this, "Error");
            }
        }
    }


    public void getCurrentLocation() {
//        Log.d("tagg", "working");
        LocationManager locationManager;
        LocationListener locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
//                String result = "\nlatitude = " + location.getLatitude() +
//                                "\nlongitude = " + location.getLongitude();
                latitude=String.valueOf(location.getLatitude());
                longitude=String.valueOf(location.getLongitude());
//                Log.d("tagg","loc:"+result);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public long getImageSize(String picturePath) {
        if(picturePath!=null){
            String path= ImagePath.getPath(this,Uri.parse(picturePath));
            File img = new File(path);
            long length = img.length();
            return length;
        }else {
            return 0;
        }
    }

    public String getMimeType(Context context, Uri uriImage) {
        String strMimeType = null;
        if(uriImage!=null){
            Cursor cursor = context.getContentResolver().query(uriImage,
                    new String[]{MediaStore.MediaColumns.MIME_TYPE},
                    null, null, null);

            if (cursor != null && cursor.moveToNext()) {
                strMimeType = cursor.getString(0);
            }

            return strMimeType.replace("image/", "");
        }else {
            return "";
        }

    }

    public byte[] getByteArrayOfImage(Bitmap photo) {
        Bitmap bm = photo;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] b = baos.toByteArray();
        return b;
    }

    public String getBase64EncodingString(byte[] data) {
        String text = Base64.encodeToString(data, Base64.DEFAULT);
        return text;
    }

    public byte[] getMD5HashString(byte[] data) {
        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance("MD5").digest(data);
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }


}
