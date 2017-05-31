package com.example.mayankaggarwal.dcare.rest;

import android.app.Activity;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import com.example.mayankaggarwal.dcare.models.BootupRequest;
import com.example.mayankaggarwal.dcare.models.BootupResponse;
import com.example.mayankaggarwal.dcare.models.Header;
import com.example.mayankaggarwal.dcare.models.OtpHeader;
import com.example.mayankaggarwal.dcare.models.OtpPayload;
import com.example.mayankaggarwal.dcare.models.OtpRequest;
import com.example.mayankaggarwal.dcare.models.OtpResponse;
import com.example.mayankaggarwal.dcare.models.Payload;
import com.example.mayankaggarwal.dcare.utils.Globals;
import com.example.mayankaggarwal.dcare.utils.Prefs;
import com.google.firebase.iid.FirebaseInstanceId;


import retrofit2.Call;

/**
 * Created by mayankaggarwal on 12/02/17.
 */

public class Data {

    public static void bootup(final Activity activity, final UpdateCallback updateCallback) {
        BootUp bootup = new BootUp(updateCallback);
        bootup.execute(activity);
    }

    public static void getOTP(final String mobileNumber,final String countryCode,final Activity activity, final UpdateCallback updateCallback) {
        GetOtp getOtp = new GetOtp(updateCallback,mobileNumber,countryCode);
        getOtp.execute(activity);
    }

    public static void sendOTP(final String mobileNumber,final Activity activity, final UpdateCallback updateCallback) {
        SendOtp sendOtp = new SendOtp(updateCallback,mobileNumber);
        sendOtp.execute(activity);
    }


    public static void internetConnection(final UpdateCallback updateCallback) {
        InternetConnection intenetConnection = new InternetConnection(updateCallback);
        intenetConnection.execute();
    }


    public static class BootUp extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;
        int error = 0;

        BootUp(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            BootupRequest bootupRequest = new BootupRequest();
            String android_id = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);

            Payload payload=new Payload();
            payload.deviceid = String.valueOf(android_id);
            payload.osType = Globals.appOS;
            payload.fcmToken = FirebaseInstanceId.getInstance().getToken();
//            payload.fcmToken="";
            bootupRequest.payload=payload;

            Header header=new Header();
            header.requestId = Globals.randomAlphaNumeric(10);
            header.appVersion = Globals.appVersion;
            if(!(Prefs.getPrefs("crewid",activity).equals("notfound"))){
                header.crewId=Prefs.getPrefs("crewid",activity);
            }else {
                header.crewId = "";
            }
            header.wprToken = "";
            bootupRequest.header=header;

            final Call<BootupResponse> bootupRes = apiInterface.bootup(bootupRequest);

            try {
                BootupResponse bootupResponse = bootupRes.execute().body();
                if (bootupResponse.success) {
                    Prefs.setPrefs("wpr_token",bootupResponse.payload.wprToken,activity);
                    Prefs.setPrefs("crewid",bootupResponse.payload.crewid,activity);
                    Prefs.setPrefs("shift_refresh_frequency_rate",bootupResponse.payload.shiftRefreshFrequencyRate,activity);
                    Prefs.setPrefs("local_shift_refresh_frequency_rate",bootupResponse.payload.localShiftRefreshFrequencyRate,activity);
                    Prefs.setPrefs("location_refresh_frequency_rate",bootupResponse.payload.locationRefreshFrequencyRate,activity);
                    error = 0;
                } else {
                    Globals.errorRes = bootupResponse.error.message;
                    error = 1;
                }
            } catch (Exception e) {
                error = 1;
                Globals.errorRes="No Internet Connection!";
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (error == 0) {
                updateCallback.onUpdate();
            } else {
                updateCallback.onFailure();
            }
        }
    }

    public static class GetOtp extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;
        String mobileNumber;
        String countryCode;
        int error = 0;

        GetOtp(UpdateCallback updateCallback,String mobileNumber,String countryCode) {
            this.updateCallback = updateCallback;
            this.mobileNumber=mobileNumber;
            this.countryCode=countryCode;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            OtpRequest otpRequest = new OtpRequest();

            OtpPayload payload=new OtpPayload();
            payload.source = "mb";
            payload.rMobile = this.mobileNumber;
            payload.timestampOfRequest = String.valueOf((int)System.currentTimeMillis()/1000);
            payload.requestFlag = 0;
            payload.country_code=this.countryCode;
            otpRequest.payload=payload;

            OtpHeader header=new OtpHeader();
            header.requesID = Globals.randomAlphaNumeric(10);
            header.appVersion = Globals.appVersion;
            if(!(Prefs.getPrefs("crewid",activity).equals("notfound"))){
                header.user_id=Prefs.getPrefs("crewid",activity);
            }
            if(!(Prefs.getPrefs("wpr_token",activity).equals("notfound"))){
                header.wprToken=Prefs.getPrefs("wpr_token",activity);
            }
            otpRequest.header=header;

            final Call<OtpResponse> otpResponseCall = apiInterface.generateotp(otpRequest);

            try {
                OtpResponse otpResponse = otpResponseCall.execute().body();
                if (otpResponse.success) {
                    error = 0;
                } else {
                    Globals.errorRes = otpResponse.error.message;
                    error = 1;
                }
            } catch (Exception e) {
                error = 1;
                Globals.errorRes="No Internet Connection!";
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (error == 0) {
                updateCallback.onUpdate();
            } else {
                updateCallback.onFailure();
            }
        }
    }



    public static class SendOtp extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;
        String mobileNumber;
        int error = 0;

        SendOtp(UpdateCallback updateCallback,String mobileNumber) {
            this.updateCallback = updateCallback;
            this.mobileNumber=mobileNumber;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            OtpRequest otpRequest = new OtpRequest();

            OtpPayload payload=new OtpPayload();
            payload.source = "mb";
            payload.rMobile = this.mobileNumber;
            payload.timestampOfRequest = String.valueOf((int)System.currentTimeMillis()/1000);
            payload.requestFlag = 0;
//            payload.country_code=this.countryCode;
            otpRequest.payload=payload;

            OtpHeader header=new OtpHeader();
            header.requesID = Globals.randomAlphaNumeric(10);
            header.appVersion = Globals.appVersion;
            if(!(Prefs.getPrefs("crewid",activity).equals("notfound"))){
                header.user_id=Prefs.getPrefs("crewid",activity);
            }
            if(!(Prefs.getPrefs("wpr_token",activity).equals("notfound"))){
                header.wprToken=Prefs.getPrefs("wpr_token",activity);
            }
            otpRequest.header=header;

            final Call<OtpResponse> otpResponseCall = apiInterface.generateotp(otpRequest);

            try {
                OtpResponse otpResponse = otpResponseCall.execute().body();
                if (otpResponse.success) {
                    error = 0;
                } else {
                    Globals.errorRes = otpResponse.error.message;
                    error = 1;
                }
            } catch (Exception e) {
                error = 1;
                Globals.errorRes="No Internet Connection!";
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (error == 0) {
                updateCallback.onUpdate();
            } else {
                updateCallback.onFailure();
            }
        }
    }




    public static class InternetConnection extends AsyncTask<Void, Void, Boolean> {

        UpdateCallback updateCallback;

        InternetConnection(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return true;
//            Runtime runtime = Runtime.getRuntime();
//            try {
//
//                Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
//                int exitValue = ipProcess.waitFor();
//                return (exitValue == 0);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            return false;
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool == true) {
                updateCallback.onUpdate();
            } else {
                updateCallback.onFailure();
            }
        }

    }


    public interface UpdateCallback {
        void onUpdate();

        void onFailure();
    }


//    public static class DownloadImageTask extends AsyncTask<String, Void, Integer> {
//        ImageView bmImage;
//        Activity activity;
//        Picasso picasso;
//
//        public DownloadImageTask(ImageView bmImage,Activity activity) {
//            this.bmImage = bmImage;
//            this.activity=activity;
//        }
//
//        protected Integer doInBackground(String... urls) {
//
//            String urldisplay = urls[0];
//            Bitmap mIcon11 = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return 0;
//        }
//
//        protected void onPostExecute(int result) {
//            if(result!=null){
//                float aspectRatio = result.getWidth() /
//                        (float) result.getHeight();
//                int width = 500;
//                int height = Math.round(width / aspectRatio);
//
//                result = Bitmap.createScaledBitmap(
//                        result, width, height, false);
//                bmImage.setImageBitmap(result);
//            }else{
//                bmImage.setImageResource(R.drawable.unknown);
//            }
//        }
//    }

}
