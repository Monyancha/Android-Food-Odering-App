package com.example.mayankaggarwal.dcare.rest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.provider.Settings;
import com.example.mayankaggarwal.dcare.activities.Details;
import com.example.mayankaggarwal.dcare.models.AlarmApis.PayloadLiveRequest;
import com.example.mayankaggarwal.dcare.models.AlarmApis.ShiftLiveRequest;
import com.example.mayankaggarwal.dcare.models.Bootup.BootupRequest;
import com.example.mayankaggarwal.dcare.models.Bootup.BootupResponse;
import com.example.mayankaggarwal.dcare.models.Bootup.Header;
import com.example.mayankaggarwal.dcare.models.Details.MediaUpload.HeaderMediaRequest;
import com.example.mayankaggarwal.dcare.models.Details.MediaUpload.MediaRequest;
import com.example.mayankaggarwal.dcare.models.Details.MediaUpload.MediaResponse;
import com.example.mayankaggarwal.dcare.models.Details.MediaUpload.PayloadMediaRequest;
import com.example.mayankaggarwal.dcare.models.Details.ProfileUpload.PayloadProfileRequest;
import com.example.mayankaggarwal.dcare.models.Details.ProfileUpload.ProfileRequest;
import com.example.mayankaggarwal.dcare.models.Details.ProfileUpload.ProfileResponse;
import com.example.mayankaggarwal.dcare.models.GetOTP.OtpHeader;
import com.example.mayankaggarwal.dcare.models.GetOTP.OtpPayload;
import com.example.mayankaggarwal.dcare.models.GetOTP.OtpRequest;
import com.example.mayankaggarwal.dcare.models.GetOTP.OtpResponse;
import com.example.mayankaggarwal.dcare.models.Bootup.Payload;
import com.example.mayankaggarwal.dcare.models.Orders.GetOrder.GetOrderRequest;
import com.example.mayankaggarwal.dcare.models.Orders.GetOrder.PayloadOrderRequest;
import com.example.mayankaggarwal.dcare.models.Orders.OrderState.ChangeRequest;
import com.example.mayankaggarwal.dcare.models.Orders.OrderState.PayloadChangeRequest;
import com.example.mayankaggarwal.dcare.models.StartEndTrip.StartTripRequest;
import com.example.mayankaggarwal.dcare.models.StartShift.FetchVendor.PayloadShiftRequest;
import com.example.mayankaggarwal.dcare.models.StartShift.FetchVendor.StartShiftRequest;
import com.example.mayankaggarwal.dcare.models.StartShift.StartEndShift.PayloadRequest;
import com.example.mayankaggarwal.dcare.models.StartShift.StartEndShift.ShiftStartRequest;
import com.example.mayankaggarwal.dcare.models.StartShift.StartEndShift.ShiftStartResponse;
import com.example.mayankaggarwal.dcare.models.StartedShift.CheckShiftStartRequest;
import com.example.mayankaggarwal.dcare.models.StartedShift.PayloadCheckShift;
import com.example.mayankaggarwal.dcare.models.VerifyOTP.VerifyHeader;
import com.example.mayankaggarwal.dcare.models.VerifyOTP.VerifyOtpRequest;
import com.example.mayankaggarwal.dcare.models.VerifyOTP.VerifyPayload;
import com.example.mayankaggarwal.dcare.models.VerifyOTP.VerifyResponse;
import com.example.mayankaggarwal.dcare.utils.Globals;
import com.example.mayankaggarwal.dcare.utils.Prefs;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;

/**
 * Created by mayankaggarwal on 12/02/17.
 */

public class Data {

    public static void bootup(final Activity activity, final UpdateCallback updateCallback) {
        BootUp bootup = new BootUp(updateCallback);
        bootup.execute(activity);
    }

    public static void getOTP(final String mobileNumber, final String countryCode, final Activity activity, final UpdateCallback updateCallback) {
        GetOtp getOtp = new GetOtp(updateCallback, mobileNumber, countryCode);
        getOtp.execute(activity);
    }

    public static void sendOTP(final String otp, final Activity activity, final UpdateCallback updateCallback) {
        SendOtp sendOtp = new SendOtp(updateCallback, otp);
        sendOtp.execute(activity);
    }

    public static void uploadPhoto(final String media_size_in_mb, final String media_file_name, final String media_file_data, final StringBuffer media_checksum, final String media_type, final String media_for, final Activity activity, final UpdateCallback updateCallback) {
        UploadPhoto uploadPhoto = new UploadPhoto(updateCallback, media_size_in_mb, media_file_name, media_file_data, media_checksum, media_type, media_for);
        uploadPhoto.execute(activity);
    }

    public static void uploadProfile(final String fname, final String mname, final String lname, final String sex, final String dob, final String nickname, final String email, final String ssn, final String dmv, final String lat, final String lng, final Activity activity, final UpdateCallback updateCallback) {
        UploadProfile uploadProfile = new UploadProfile(updateCallback, fname, mname, lname, sex, dob, nickname, email, ssn, dmv, lat, lng);
        uploadProfile.execute(activity);
    }

    public static void fetchStartShift(final Activity activity, final UpdateCallback updateCallback) {
        FetchStartShift fetchStartShift = new FetchStartShift(updateCallback);
        fetchStartShift.execute(activity);
    }

    public static void crewShiftStartEnd(final Activity activity, String vendor_id, String checkItems_id, String startORend, String latitude, String longitude, final UpdateCallback updateCallback) {
        CrewShiftStart crewShiftStart = new CrewShiftStart(updateCallback, vendor_id, checkItems_id, startORend, latitude, longitude);
        crewShiftStart.execute(activity);
    }

    public static void getAllOrders(final Activity activity, String vendor_id, String shift_id, final UpdateCallback updateCallback) {
        GetAllOrders getAllOrders = new GetAllOrders(updateCallback, vendor_id, shift_id);
        getAllOrders.execute(activity);
    }

    public static void shiftLive(final Context activity, String vendor_id, String shift_id, final UpdateCallback updateCallback) {
        ShiftLive shiftLive = new ShiftLive(updateCallback, vendor_id, shift_id);
        shiftLive.execute(activity);
    }

    public static void validateShift(final Context activity, String vendor_id, String shift_id, String lat, String lng, final UpdateCallback updateCallback) {
        ValidateShift validateShift = new ValidateShift(updateCallback, vendor_id, shift_id, lat, lng);
        validateShift.execute(activity);
    }

    public static void changeOrderState(final Context activity, String order_id, String state_code, final UpdateCallback updateCallback) {
        ChangeState changeState = new ChangeState(updateCallback, order_id, state_code);
        changeState.execute(activity);
    }

    public static void getReasons(final Activity activity, final UpdateCallback updateCallback) {
        GetReasons getReasons = new GetReasons(updateCallback);
        getReasons.execute(activity);
    }

    public static void crewTrip(final Activity activity, String op, final UpdateCallback updateCallback) {
        CrewTrip crewTrip = new CrewTrip(updateCallback, op);
        crewTrip.execute(activity);
    }

    public static void googleLatLngApi(final Activity activity, String address, final UpdateCallback updateCallback) {
        GoogleLatLng googleLatLng = new GoogleLatLng(updateCallback, address);
        googleLatLng.execute(activity);
    }

    public static void updatelatlng(final Activity activity, final UpdateCallback updateCallback) {
        UpdateLatLng updateLatLng = new UpdateLatLng(updateCallback);
        updateLatLng.execute(activity);
    }

    public static void googleRoadMAp(final Activity activity, String address, final UpdateCallback updateCallback) {
        GoogleRoadMap googleRoadMap = new GoogleRoadMap(updateCallback, address);
        googleRoadMap.execute(activity);
    }

    public static void plotRoad(final Activity activity, final UpdateCallback updateCallback) {
        PlotRoad plotRoad = new PlotRoad(updateCallback);
        plotRoad.execute(activity);
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

            Payload payload = new Payload();
            payload.deviceid = String.valueOf(android_id);
            payload.osType = Globals.appOS;
            payload.fcmToken = FirebaseInstanceId.getInstance().getToken();
//            payload.fcmToken="";
            bootupRequest.payload = payload;

            Header header = new Header();
            header.requestId = Globals.randomAlphaNumeric(10);
            header.appVersion = Globals.appVersion;
            if (!(Prefs.getPrefs("crewid", activity).equals("notfound"))) {
                header.crewId = Prefs.getPrefs("crewid", activity);
            } else {
                header.crewId = "";
            }
            header.wprToken = "";
            bootupRequest.header = header;

            final Call<BootupResponse> bootupRes = apiInterface.bootup(bootupRequest);

            try {
                BootupResponse bootupResponse = bootupRes.execute().body();
                if (bootupResponse.success) {
                    Prefs.setPrefs("wpr_token", bootupResponse.payload.wprToken, activity);
                    Prefs.setPrefs("crewid", bootupResponse.payload.crewid, activity);
                    Prefs.setPrefs("shift_refresh_frequency_rate", bootupResponse.payload.shiftRefreshFrequencyRate, activity);
                    Prefs.setPrefs("local_shift_refresh_frequency_rate", bootupResponse.payload.localShiftRefreshFrequencyRate, activity);
                    Prefs.setPrefs("location_refresh_frequency_rate", bootupResponse.payload.locationRefreshFrequencyRate, activity);
                    Prefs.setPrefs("salt_key", bootupResponse.payload.salt_key, activity);
                    error = 0;
                } else {
                    Globals.errorRes = bootupResponse.error.message;
                    error = 1;
                }
            } catch (Exception e) {
                error = 1;
                Globals.errorRes = "No Internet Connection!";
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

        GetOtp(UpdateCallback updateCallback, String mobileNumber, String countryCode) {
            this.updateCallback = updateCallback;
            this.mobileNumber = mobileNumber;
            this.countryCode = countryCode;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            OtpRequest otpRequest = new OtpRequest();

            OtpPayload payload = new OtpPayload();
            payload.source = "mb";
            payload.rMobile = this.mobileNumber;
            payload.timestampOfRequest = String.valueOf((int) System.currentTimeMillis() / 1000);
            payload.requestFlag = 0;
            payload.country_code = this.countryCode;
            otpRequest.payload = payload;

            OtpHeader header = new OtpHeader();
            header.requesID = Globals.randomAlphaNumeric(10);
            header.appVersion = Globals.appVersion;
            if (!(Prefs.getPrefs("crewid", activity).equals("notfound"))) {
                header.user_id = Prefs.getPrefs("crewid", activity);
            }
            if (!(Prefs.getPrefs("wpr_token", activity).equals("notfound"))) {
                header.wprToken = Prefs.getPrefs("wpr_token", activity);
            }
            otpRequest.header = header;

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
                Globals.errorRes = "No Internet Connection!";
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
        String otp;
        int error = 0;

        SendOtp(UpdateCallback updateCallback, String otp) {
            this.updateCallback = updateCallback;
            this.otp = otp;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            VerifyOtpRequest verifyOtpRequest = new VerifyOtpRequest();

            VerifyPayload payload = new VerifyPayload();
            payload.source = "mb";
            if (!(Prefs.getPrefs("user_mobile", activity).equals("notfound"))) {
                payload.rMobile = Prefs.getPrefs("user_mobile", activity);
            } else {
                payload.rMobile = "";
            }
            payload.timestampOfRequest = String.valueOf((int) System.currentTimeMillis() / 1000);
            if (!(Prefs.getPrefs("country_number", activity).equals("notfound"))) {
                payload.countryCode = Prefs.getPrefs("country_number", activity);
            } else {
                payload.countryCode = "91";
            }
            payload.otp = otp;
            verifyOtpRequest.payload = payload;

            VerifyHeader header = new VerifyHeader();
            header.requestID = Globals.randomAlphaNumeric(10);
            header.appVersion = Globals.appVersion;
            if (!(Prefs.getPrefs("crewid", activity).equals("notfound"))) {
                header.userId = Prefs.getPrefs("crewid", activity);
            }
            if (!(Prefs.getPrefs("crewid", activity).equals("notfound"))) {
                header.crewId = Prefs.getPrefs("crewid", activity);
            }
            if (!(Prefs.getPrefs("wpr_token", activity).equals("notfound"))) {
                header.wprToken = Prefs.getPrefs("wpr_token", activity);
            }
            verifyOtpRequest.header = header;

            final Call<VerifyResponse> verifyResponseCall = apiInterface.verifymobile(verifyOtpRequest);

            try {
                VerifyResponse verifyResponse = verifyResponseCall.execute().body();
                if (verifyResponse.success) {
                    error = 0;
                } else {
                    Globals.errorRes = verifyResponse.error.message;
                    error = 1;
                }
            } catch (Exception e) {
                error = 1;
                Globals.errorRes = "No Internet Connection!";
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

    public static class UploadPhoto extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;
        String media_size_in_mb;
        String media_file_name;
        String media_file_data;
        StringBuffer media_checksum;
        String media_type;
        String media_for;
        int error = 0;

        public UploadPhoto(UpdateCallback updateCallback, String media_size_in_mb, String media_file_name, String media_file_data, StringBuffer media_checksum, String media_type, String media_for) {
            this.updateCallback = updateCallback;
            this.media_size_in_mb = media_size_in_mb;
            this.media_file_name = media_file_name;
            this.media_file_data = media_file_data;
            this.media_checksum = media_checksum;
            this.media_type = media_type;
            this.media_for = media_for;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            MediaRequest mediaRequest = new MediaRequest();

            PayloadMediaRequest payload = new PayloadMediaRequest();
            payload.mediaSizeInMb = media_size_in_mb;
            payload.mediaData = media_file_data;
            payload.mediaFileName = media_file_name;
            payload.mediaChecksum = media_checksum.toString();
            payload.mediaType = media_type;
            payload.mediaFor = media_for;

            mediaRequest.payload = payload;

            HeaderMediaRequest header = new HeaderMediaRequest();
            header.requestId = Globals.randomAlphaNumeric(10);
            header.appVersion = Globals.appVersion;
            if (!(Prefs.getPrefs("crewid", activity).equals("notfound"))) {
                header.crewId = Prefs.getPrefs("crewid", activity);
            }
            if (!(Prefs.getPrefs("wpr_token", activity).equals("notfound"))) {
                header.wprToken = Prefs.getPrefs("wpr_token", activity);
            }
            mediaRequest.header = header;

            final Call<MediaResponse> mediaResponseCall = apiInterface.uploadmedia(mediaRequest);

            try {
                MediaResponse mediaResponse = mediaResponseCall.execute().body();
                if (mediaResponse.success) {
                    error = 0;
                } else {
                    Globals.errorRes = mediaResponse.error.message;
                    error = 1;
                }
            } catch (Exception e) {
                error = 1;
                Globals.errorRes = "No Internet Connection!";
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (Details.stopMediaUpload) {
                Details.stopMediaUpload = false;
                Globals.errorRes = "You Stopped File Upload!";
                updateCallback.onFailure();
            } else {
                if (error == 0) {
                    updateCallback.onUpdate();
                } else {
                    updateCallback.onFailure();
                }
            }
        }
    }


    public static class UploadProfile extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;
        String dob, nickname, email, ssn, dmv, lat, lng;
        String fname;
        String mname;
        String lname;
        String sex;
        int error = 0;

        public UploadProfile(UpdateCallback updateCallback, String fname, String mname,
                             String lname, String sex, String dob, String nickname, String email, String ssn,
                             String dmv, String lat, String lng) {
            this.updateCallback = updateCallback;
            this.fname = fname;
            this.mname = mname;
            this.lname = lname;
            this.sex = sex;
            this.dob = dob;
            this.nickname = nickname;
            this.email = email;
            this.ssn = ssn;
            this.dmv = dmv;
            this.lat = lat;
            this.lng = lng;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            ProfileRequest profileRequest = new ProfileRequest();

            PayloadProfileRequest payload = new PayloadProfileRequest();
            payload.fname = fname;
            payload.mname = mname;
            payload.lname = lname;
            payload.sex = sex;
            payload.dob = dob;
            payload.nickname = nickname;
            payload.email = email;
            payload.ssnNumber = ssn;
            payload.recordNumber = dmv;
            if (!(Prefs.getPrefs("country_number", activity).equals("notfound"))) {
                payload.mobileCountryCode = Prefs.getPrefs("country_number", activity);
            } else {
                payload.mobileCountryCode = "91";
            }
            if (!(Prefs.getPrefs("user_mobile", activity).equals("notfound"))) {
                payload.mobileNumber = Prefs.getPrefs("user_mobile", activity);
            }
            payload.address = "";
            payload.address2 = "";
            payload.cityCode = "";
            payload.postalCode = "";
            payload.homeLocationLat = lat;
            payload.homeLocationLong = lng;

            profileRequest.payload = payload;

            HeaderMediaRequest header = new HeaderMediaRequest();
            header.requestId = Globals.randomAlphaNumeric(10);
            header.appVersion = Globals.appVersion;
            if (!(Prefs.getPrefs("crewid", activity).equals("notfound"))) {
                header.crewId = Prefs.getPrefs("crewid", activity);
            }
            if (!(Prefs.getPrefs("wpr_token", activity).equals("notfound"))) {
                header.wprToken = Prefs.getPrefs("wpr_token", activity);
            }
            profileRequest.header = header;

            final Call<ProfileResponse> profileResponseCall = apiInterface.uploadprofile(profileRequest);

            try {
                ProfileResponse profileResponse = profileResponseCall.execute().body();
                if (profileResponse.success) {
                    error = 0;
                } else {
                    Globals.errorRes = profileResponse.error.message;
                    error = 1;
                }
            } catch (Exception e) {
                error = 1;
                Globals.errorRes = "No Internet Connection!";
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


    public static class FetchStartShift extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;
        int error = 0;

        public FetchStartShift(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            StartShiftRequest startShiftRequest = new StartShiftRequest();

            PayloadShiftRequest payload = new PayloadShiftRequest();

            startShiftRequest.payload = payload;

            HeaderMediaRequest header = new HeaderMediaRequest();
            header.requestId = Globals.randomAlphaNumeric(10);
            header.appVersion = Globals.appVersion;
            if (!(Prefs.getPrefs("crewid", activity).equals("notfound"))) {
                header.crewId = Prefs.getPrefs("crewid", activity);
            }
            if (!(Prefs.getPrefs("wpr_token", activity).equals("notfound"))) {
                header.wprToken = Prefs.getPrefs("wpr_token", activity);
            }
            startShiftRequest.header = header;

            final Call<JsonObject> shiftResponseCall = apiInterface.fetchvendor(startShiftRequest);

            try {
                JsonObject jsonObject = shiftResponseCall.execute().body();
                if (jsonObject.get("success").getAsBoolean()) {
                    Prefs.setPrefs("vendors", jsonObject.toString(), activity);
                    error = 0;
                } else {
                    Globals.errorRes = jsonObject.get("error").getAsJsonObject().get("message").getAsString();
                    error = 1;
                }
            } catch (Exception e) {
                error = 1;
                Globals.errorRes = "No Internet Connection!";
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


    public static class CrewShiftStart extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;
        int error = 0;
        String vendor_id, checkItems_id, startORend, latitude, longitude;

        public CrewShiftStart(UpdateCallback updateCallback, String vendor_id, String checkItems_id, String startORend, String latitude, String longitude) {
            this.updateCallback = updateCallback;
            this.vendor_id = vendor_id;
            this.checkItems_id = checkItems_id;
            this.startORend = startORend;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            ShiftStartRequest startShiftRequest = new ShiftStartRequest();

            PayloadRequest payload = new PayloadRequest();

            payload.activityList = checkItems_id;
            payload.comments = "valid values can be only start and end for operation";
            payload.currentLatitude = latitude;
            payload.currentLongitude = longitude;
            payload.operation = startORend;
            payload.vendorId = vendor_id;

            startShiftRequest.payload = payload;

            HeaderMediaRequest header = new HeaderMediaRequest();
            header.requestId = Globals.randomAlphaNumeric(10);
            header.appVersion = Globals.appVersion;
            if (!(Prefs.getPrefs("crewid", activity).equals("notfound"))) {
                header.crewId = Prefs.getPrefs("crewid", activity);
            }
            if (!(Prefs.getPrefs("wpr_token", activity).equals("notfound"))) {
                header.wprToken = Prefs.getPrefs("wpr_token", activity);
            }
            startShiftRequest.header = header;

            final Call<ShiftStartResponse> shiftResponseCall = apiInterface.crewshift(startShiftRequest);

            try {
                ShiftStartResponse jsonObject = shiftResponseCall.execute().body();
                if (jsonObject.success) {
                    Prefs.setPrefs("shift_id", String.valueOf(jsonObject.payload.shiftId), activity);
                    error = 0;
                } else {
                    Globals.errorRes = jsonObject.error.message;
                    error = 1;
                }
            } catch (Exception e) {
                error = 1;
                Globals.errorRes = "No Internet Connection!";
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

    public static class GetAllOrders extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;
        int error = 0;
        String vendor_id, shift_id;

        public GetAllOrders(UpdateCallback updateCallback, String vendor_id, String shift_id) {
            this.updateCallback = updateCallback;
            this.vendor_id = vendor_id;
            this.shift_id = shift_id;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            GetOrderRequest getOrderRequest = new GetOrderRequest();

            PayloadOrderRequest payload = new PayloadOrderRequest();

            payload.shiftId = shift_id;
            payload.vendorId = vendor_id;

            getOrderRequest.payload = payload;

            HeaderMediaRequest header = new HeaderMediaRequest();
            header.requestId = Globals.randomAlphaNumeric(10);
            header.appVersion = Globals.appVersion;
            if (!(Prefs.getPrefs("crewid", activity).equals("notfound"))) {
                header.crewId = Prefs.getPrefs("crewid", activity);
            }
            if (!(Prefs.getPrefs("wpr_token", activity).equals("notfound"))) {
                header.wprToken = Prefs.getPrefs("wpr_token", activity);
            }
            getOrderRequest.header = header;

            final Call<JsonObject> orderResponseCall = apiInterface.getallorderscrew(getOrderRequest);

            try {
                JsonObject jsonObject = orderResponseCall.execute().body();
                if (jsonObject.get("success").getAsBoolean()) {
                    Prefs.setPrefs("orderJson", jsonObject.toString(), activity);
                    error = 0;
                } else {
                    Globals.errorRes = jsonObject.get("error").getAsJsonObject().get("message").getAsString();
                    error = 1;
                }
            } catch (Exception e) {
                error = 1;
                Globals.errorRes = "No Internet Connection!";
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

    public static class ShiftLive extends AsyncTask<Context, Void, Integer> {

        UpdateCallback updateCallback;
        int error = 0;
        String vendor_id, shift_id;

        public ShiftLive(UpdateCallback updateCallback, String vendor_id, String shift_id) {
            this.updateCallback = updateCallback;
            this.vendor_id = vendor_id;
            this.shift_id = shift_id;
        }

        @Override
        protected Integer doInBackground(Context... params) {
            final Context activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            ShiftLiveRequest shiftLiveRequest = new ShiftLiveRequest();

            PayloadLiveRequest payload = new PayloadLiveRequest();

            payload.shiftId = shift_id;
            payload.vendorId = vendor_id;
            payload.timestamp = String.valueOf((int) System.currentTimeMillis() / 1000);

            Prefs.setPrefs("last_loacal_timestamp", payload.timestamp, activity);

            shiftLiveRequest.payload = payload;

            HeaderMediaRequest header = new HeaderMediaRequest();
            header.requestId = Globals.randomAlphaNumeric(10);
            header.appVersion = Globals.appVersion;
            if (!(Prefs.getPrefs("crewid", activity).equals("notfound"))) {
                header.crewId = Prefs.getPrefs("crewid", activity);
            }
            if (!(Prefs.getPrefs("wpr_token", activity).equals("notfound"))) {
                header.wprToken = Prefs.getPrefs("wpr_token", activity);
            }
            shiftLiveRequest.header = header;

            final Call<JsonObject> shiftLiveResponseCall = apiInterface.keepShiftLive(shiftLiveRequest);

            try {
                JsonObject jsonObject = shiftLiveResponseCall.execute().body();
                if (jsonObject.get("success").getAsBoolean()) {
                    error = 0;
                } else {
//                    Globals.errorRes = jsonObject.get("error").getAsJsonObject().get("message").getAsString();
                    error = 1;
                }
            } catch (Exception e) {
                error = 1;
//                Globals.errorRes = "No Internet Connection!";
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


    public static class ValidateShift extends AsyncTask<Context, Void, Integer> {

        UpdateCallback updateCallback;
        int error = 0;
        String vendor_id, shift_id;
        String lat, lng;

        public ValidateShift(UpdateCallback updateCallback, String vendor_id, String shift_id, String lat, String lng) {
            this.updateCallback = updateCallback;
            this.vendor_id = vendor_id;
            this.shift_id = shift_id;
            this.lat = lat;
            this.lng = lng;
        }

        @Override
        protected Integer doInBackground(Context... params) {
            final Context activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            CheckShiftStartRequest checkShiftStartRequest = new CheckShiftStartRequest();

            PayloadCheckShift payload = new PayloadCheckShift();

            payload.shiftId = shift_id;
            payload.vendorId = vendor_id;
            payload.currentLatitude = lat;
            payload.currentLongitude = lng;
            payload.lastLocalTimestamp = Prefs.getPrefs("last_loacal_timestamp", activity);

            checkShiftStartRequest.payload = payload;

            HeaderMediaRequest header = new HeaderMediaRequest();
            header.requestId = Globals.randomAlphaNumeric(10);
            header.appVersion = Globals.appVersion;
            if (!(Prefs.getPrefs("crewid", activity).equals("notfound"))) {
                header.crewId = Prefs.getPrefs("crewid", activity);
            }
            if (!(Prefs.getPrefs("wpr_token", activity).equals("notfound"))) {
                header.wprToken = Prefs.getPrefs("wpr_token", activity);
            }
            checkShiftStartRequest.header = header;

            final Call<JsonObject> shiftLiveResponseCall = apiInterface.validatecrewshift(checkShiftStartRequest);

            try {
                JsonObject jsonObject = shiftLiveResponseCall.execute().body();
                if (jsonObject.get("success").getAsBoolean()) {
                    error = 0;
                } else {
//                    Globals.errorRes = jsonObject.get("error").getAsJsonObject().get("message").getAsString();
                    error = 1;
                }
            } catch (Exception e) {
                error = 1;
//                Globals.errorRes = "No Internet Connection!";
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


    public static class ChangeState extends AsyncTask<Context, Void, Integer> {

        UpdateCallback updateCallback;
        int error = 0;
        String order_id;
        String state_code;

        public ChangeState(UpdateCallback updateCallback, String order_id, String state_code) {
            this.updateCallback = updateCallback;
            this.order_id = order_id;
            this.state_code = state_code;
        }

        @Override
        protected Integer doInBackground(Context... params) {
            final Context activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            ChangeRequest changeRequest = new ChangeRequest();

            PayloadChangeRequest payload = new PayloadChangeRequest();

            payload.orderId = order_id;
            payload.vendorId = Prefs.getPrefs("vendor_id_selected", activity);
            payload.custRating = Globals.star_Rating;
            payload.reasonCode = Globals.reason_id;
            payload.reasonText = Globals.reason_text;
            payload.actualDeliveryTime = String.valueOf((int) System.currentTimeMillis() / 1000);
            ;
            payload.crewPickupTime = String.valueOf((int) System.currentTimeMillis() / 1000);
            ;
            payload.estimatedDeliveryTime = String.valueOf((int) System.currentTimeMillis() / 1000);
            ;
            payload.newStateCode = state_code;

            changeRequest.payload = payload;

            HeaderMediaRequest header = new HeaderMediaRequest();
            header.requestId = Globals.randomAlphaNumeric(10);
            header.appVersion = Globals.appVersion;
            if (!(Prefs.getPrefs("crewid", activity).equals("notfound"))) {
                header.crewId = Prefs.getPrefs("crewid", activity);
            }
            if (!(Prefs.getPrefs("wpr_token", activity).equals("notfound"))) {
                header.wprToken = Prefs.getPrefs("wpr_token", activity);
            }
            changeRequest.header = header;

            final Call<JsonObject> changeLiveResponseCall = apiInterface.changeorderstatecrew(changeRequest);

            try {
                JsonObject jsonObject = changeLiveResponseCall.execute().body();
                if (jsonObject.get("success").getAsBoolean()) {
                    error = 0;
                } else {
                    Globals.errorRes = jsonObject.get("error").getAsJsonObject().get("message").getAsString();
                    error = 1;
                }
            } catch (Exception e) {
                error = 1;
                Globals.errorRes = "No Internet Connection!";
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

    public static class GetReasons extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;
        int error = 0;

        public GetReasons(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            StartShiftRequest startShiftRequest = new StartShiftRequest();

            PayloadShiftRequest payload = new PayloadShiftRequest();

            startShiftRequest.payload = payload;

            HeaderMediaRequest header = new HeaderMediaRequest();
            header.requestId = Globals.randomAlphaNumeric(10);
            header.appVersion = Globals.appVersion;
            if (!(Prefs.getPrefs("crewid", activity).equals("notfound"))) {
                header.crewId = Prefs.getPrefs("crewid", activity);
            }
            if (!(Prefs.getPrefs("wpr_token", activity).equals("notfound"))) {
                header.wprToken = Prefs.getPrefs("wpr_token", activity);
            }
            startShiftRequest.header = header;

            final Call<JsonObject> shiftResponseCall = apiInterface.getreasons(startShiftRequest);

            try {
                JsonObject jsonObject = shiftResponseCall.execute().body();
                if (jsonObject.get("success").getAsBoolean()) {
                    Prefs.setPrefs("reasonJson", jsonObject.toString(), activity);
                    error = 0;
                } else {
                    Globals.errorRes = jsonObject.get("error").getAsJsonObject().get("message").getAsString();
                    error = 1;
                }
            } catch (Exception e) {
                error = 1;
                Globals.errorRes = "No Internet Connection!";
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


    public static class CrewTrip extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;
        String operation;
        int error = 0;

        public CrewTrip(UpdateCallback updateCallback, String operation) {
            this.updateCallback = updateCallback;
            this.operation = operation;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            StartTripRequest startTripRequest = new StartTripRequest();

            JsonObject payload = new JsonObject();
            payload.addProperty("operation",operation);
            payload.addProperty("current_latitude",Globals.lat);
            payload.addProperty("current_longitude",Globals.lng);
            payload.addProperty("shift_id",Prefs.getPrefs("shift_id", activity));
            payload.addProperty("trip_id",Prefs.getPrefs("trip_id", activity));
            payload.addProperty("vendor_id",Prefs.getPrefs("vendor_id_selected", activity));

            JsonArray orderInfo=new JsonArray();
            if (!(Prefs.getPrefs("orderJson", activity)).equals("notfound")) {
                JsonParser jsonParser = new JsonParser();
                JsonObject ob = jsonParser.parse(Prefs.getPrefs("orderJson", activity)).getAsJsonObject();
                JsonArray orderArray = ob.get("payload").getAsJsonObject().get("orders").getAsJsonObject().get("orders").getAsJsonArray();
                for (int i = 0; i < orderArray.size(); i++) {
                    final JsonObject orderObject = orderArray.get(i).getAsJsonObject().get("order").getAsJsonObject();
                    String order_code = orderObject.get("order_last_state_code").getAsString();
                    if (Integer.parseInt(order_code) == Globals.ORDERSTATE_IN_TRANSIT) {
                        JsonObject order = new JsonObject();
                        order.addProperty("estimated_delivery_time","-1");
                        order.addProperty("order_id",orderObject.get("order_id").getAsString());
                        order.addProperty("new_order_state_code","7");
                        orderInfo.add(order);
                    }
                }
            }

            payload.add("order_info",orderInfo);

            startTripRequest.payload = payload;

            Prefs.setPrefs("order_info",payload.toString(),activity);

            HeaderMediaRequest header = new HeaderMediaRequest();
            header.requestId = Globals.randomAlphaNumeric(10);
            header.appVersion = Globals.appVersion;
            if (!(Prefs.getPrefs("crewid", activity).equals("notfound"))) {
                header.crewId = Prefs.getPrefs("crewid", activity);
            }
            if (!(Prefs.getPrefs("wpr_token", activity).equals("notfound"))) {
                header.wprToken = Prefs.getPrefs("wpr_token", activity);
            }
            startTripRequest.header = header;

            final Call<JsonObject> shiftResponseCall = apiInterface.crewtrip(startTripRequest);

            try {
                JsonObject jsonObject = shiftResponseCall.execute().body();
                if (jsonObject.get("success").getAsBoolean()) {
                    Prefs.setPrefs("trip_id", jsonObject.get("payload").getAsJsonObject().get("trip_id").getAsString(), activity);
                    error = 0;
                } else {
                    Globals.errorRes = jsonObject.get("error").getAsJsonObject().get("message").getAsString();
                    error = 1;
                }
            } catch (Exception e) {
                error = 1;
                Globals.errorRes = "No Internet Connection!";
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


    public static class GoogleLatLng extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;
        String address;
        int error = 0;

        public GoogleLatLng(UpdateCallback updateCallback, String address) {
            this.updateCallback = updateCallback;
            this.address = address;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            String url="https://maps.googleapis.com/maps/api/geocode/json?address="+address+"&key=AIzaSyBG9yyecx_pIrAUSy_VivF7kXhOWTH5230";
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    Globals.googleLat=null;
                    Globals.googleLng=null;
                    error=1;
                    call.cancel();
                }

                @Override
                public void onResponse(okhttp3.Call call, Response response) throws IOException {
                    try {
                        error=0;
                        String res=response.body().string();
                        JsonParser jsonParser=new JsonParser();
                        JsonObject jsonObject=jsonParser.parse(res).getAsJsonObject();
                        if(jsonObject.get("status").getAsString().toLowerCase().equals("ok")){
                            JsonObject loc=jsonObject.get("results").getAsJsonArray().get(0).getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject();
                            Globals.googleLat=loc.get("lat").getAsString();
                            Globals.googleLng=loc.get("lng").getAsString();
                            Globals.place_id=jsonObject.get("results").getAsJsonArray().get(0).getAsJsonObject().get("place_id").getAsString();
                        }else {
                            Globals.googleLat=null;
                            Globals.googleLng=null;
                            Globals.place_id=null;
                        }
                    }catch (Exception e){
                        Globals.googleLat=null;
                        Globals.googleLng=null;
                        Globals.place_id=null;
                        error=1;
                        e.printStackTrace();
                    }
                }
            });

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


    public static class GoogleRoadMap extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;
        String address;
        int error = 0;

        public GoogleRoadMap(UpdateCallback updateCallback, String address) {
            this.updateCallback = updateCallback;
            this.address = address;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            String url= "https://maps.googleapis.com/maps/api/directions/json?"+address+"&key=AIzaSyBG9yyecx_pIrAUSy_VivF7kXhOWTH5230";
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    Globals.googleLat=null;
                    Globals.googleLng=null;
                    error=1;
                    call.cancel();
                }

                @Override
                public void onResponse(okhttp3.Call call, Response response) throws IOException {
                    try {
                        error=0;
                        String res=response.body().string();
                        JsonParser jsonParser=new JsonParser();
                        JsonObject jsonObject=jsonParser.parse(res).getAsJsonObject();
                        if(jsonObject.get("status").getAsString().toLowerCase().equals("ok")){
                            JsonObject routes=jsonObject.get("routes").getAsJsonArray().get(0).getAsJsonObject();
                            Prefs.setPrefs("roadMapJson",routes.toString(),activity);
                        }
                    }catch (Exception e){
                        error=1;
                        e.printStackTrace();
                    }
                }
            });

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


    public static class UpdateLatLng extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;
        int error = 0;

        public UpdateLatLng(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            StartTripRequest startTripRequest = new StartTripRequest();

            JsonObject payload = new JsonObject();
            payload.addProperty("source","mb");
            payload.addProperty("place_id",Globals.place_id);
            payload.addProperty("address_id",Globals.address_id);
            payload.addProperty("lat",Globals.googleLat);
            payload.addProperty("long",Globals.googleLng);

            startTripRequest.payload = payload;

            HeaderMediaRequest header = new HeaderMediaRequest();
            header.requestId = Globals.randomAlphaNumeric(10);
            header.appVersion = Globals.appVersion;
            if (!(Prefs.getPrefs("crewid", activity).equals("notfound"))) {
                header.crewId = Prefs.getPrefs("crewid", activity);
            }
            if (!(Prefs.getPrefs("wpr_token", activity).equals("notfound"))) {
                header.wprToken = Prefs.getPrefs("wpr_token", activity);
            }
            startTripRequest.header = header;

            final Call<JsonObject> shiftResponseCall = apiInterface.updatelatlong(startTripRequest);

            try {
                JsonObject jsonObject = shiftResponseCall.execute().body();

            } catch (Exception e) {
                error = 1;
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


    public static class PlotRoad extends AsyncTask<Activity, Void, PolylineOptions> {

        UpdateCallback updateCallback;
        int error = 0;

        public PlotRoad(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected PolylineOptions doInBackground(Activity... params) {
            final Activity activity = params[0];

            List<LatLng> polylines = new ArrayList<LatLng>();
            List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
            List<HashMap<String, String>> path = new ArrayList<HashMap<String, String>>();

            if (!(Prefs.getPrefs("roadMapJson", activity).equals("notfound"))) {
                JsonParser jsonParser = new JsonParser();
                JsonObject obj = jsonParser.parse(Prefs.getPrefs("roadMapJson", activity)).getAsJsonObject();
                JsonArray legs = obj.get("legs").getAsJsonArray();
                for (int i = 0; i < legs.size(); i++) {
                    JsonArray steps = legs.get(i).getAsJsonObject().get("steps").getAsJsonArray();
                    for (int j = 0; j < steps.size(); j++) {
                        List<LatLng> list =decodePoly(steps.get(j).getAsJsonObject().get("polyline").getAsJsonObject().get("points").getAsString());
                        for (int l = 0; l < list.size(); l++) {
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("lat",
                                    Double.toString(((LatLng) list.get(l)).latitude));
                            hm.put("lng",
                                    Double.toString(((LatLng) list.get(l)).longitude));
                            path.add(hm);
                        }
                    }
                }
            }
            routes.add(path);


            ArrayList<LatLng> points = null;
            PolylineOptions polyLineOptions = null;

            // traversing through routes
            for (int i = 0; i < routes.size(); i++) {
                points = new ArrayList<LatLng>();
                polyLineOptions = new PolylineOptions();
                List<HashMap<String, String>> pathk = routes.get(i);

                for (int j = 0; j < pathk.size(); j++) {
                    HashMap<String, String> point = pathk.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                polyLineOptions.addAll(points);
                polyLineOptions.width(15);
                polyLineOptions.color(Color.parseColor("#fe4c13"));
            }
            return polyLineOptions;

//            return 0;
        }

        @Override
        protected void onPostExecute(PolylineOptions integer) {
            if (error == 0) {
                Globals.polylineOptions=integer;
                updateCallback.onUpdate();
            } else {
                updateCallback.onFailure();
            }
        }
    }
//
//    public static PolylineOptions getPolylines(Context context) {
//        List<LatLng> polylines = new ArrayList<LatLng>();
//        List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
//        List<HashMap<String, String>> path = new ArrayList<HashMap<String, String>>();
//
//        if (!(Prefs.getPrefs("roadMapJson", context).equals("notfound"))) {
//            JsonParser jsonParser = new JsonParser();
//            JsonObject obj = jsonParser.parse(Prefs.getPrefs("roadMapJson", context)).getAsJsonObject();
//            JsonArray legs = obj.get("legs").getAsJsonArray();
//            for (int i = 0; i < legs.size(); i++) {
//                JsonArray steps = legs.get(i).getAsJsonObject().get("steps").getAsJsonArray();
//                for (int j = 0; j < steps.size(); j++) {
//                    List<LatLng> list =decodePoly(steps.get(j).getAsJsonObject().get("polyline").getAsJsonObject().get("points").getAsString());
//                    for (int l = 0; l < list.size(); l++) {
//                        HashMap<String, String> hm = new HashMap<String, String>();
//                        hm.put("lat",
//                                Double.toString(((LatLng) list.get(l)).latitude));
//                        hm.put("lng",
//                                Double.toString(((LatLng) list.get(l)).longitude));
//                        path.add(hm);
//                    }
//                }
//            }
//        }
//        routes.add(path);
//
//
//        ArrayList<LatLng> points = null;
//        PolylineOptions polyLineOptions = null;
//
//        // traversing through routes
//        for (int i = 0; i < routes.size(); i++) {
//            points = new ArrayList<LatLng>();
//            polyLineOptions = new PolylineOptions();
//            List<HashMap<String, String>> pathk = routes.get(i);
//
//            for (int j = 0; j < pathk.size(); j++) {
//                HashMap<String, String> point = pathk.get(j);
//
//                double lat = Double.parseDouble(point.get("lat"));
//                double lng = Double.parseDouble(point.get("lng"));
//                LatLng position = new LatLng(lat, lng);
//
//                points.add(position);
//            }
//
//            polyLineOptions.addAll(points);
//            polyLineOptions.width(15);
//            polyLineOptions.color(Color.parseColor("#fe4c13"));
//        }
//        return polyLineOptions;
//    }

    private static List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
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
