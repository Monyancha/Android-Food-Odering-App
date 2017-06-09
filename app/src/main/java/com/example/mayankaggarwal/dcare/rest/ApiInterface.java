package com.example.mayankaggarwal.dcare.rest;


import com.example.mayankaggarwal.dcare.models.AlarmApis.ShiftLiveRequest;
import com.example.mayankaggarwal.dcare.models.Bootup.BootupRequest;
import com.example.mayankaggarwal.dcare.models.Bootup.BootupResponse;
import com.example.mayankaggarwal.dcare.models.Details.MediaUpload.MediaRequest;
import com.example.mayankaggarwal.dcare.models.Details.MediaUpload.MediaResponse;
import com.example.mayankaggarwal.dcare.models.Details.ProfileUpload.ProfileRequest;
import com.example.mayankaggarwal.dcare.models.Details.ProfileUpload.ProfileResponse;
import com.example.mayankaggarwal.dcare.models.GetOTP.OtpRequest;
import com.example.mayankaggarwal.dcare.models.GetOTP.OtpResponse;
import com.example.mayankaggarwal.dcare.models.Orders.GetOrder.GetOrderRequest;
import com.example.mayankaggarwal.dcare.models.Orders.OrderState.ChangeRequest;
import com.example.mayankaggarwal.dcare.models.StartShift.FetchVendor.StartShiftRequest;
import com.example.mayankaggarwal.dcare.models.StartShift.StartEndShift.ShiftStartRequest;
import com.example.mayankaggarwal.dcare.models.StartShift.StartEndShift.ShiftStartResponse;
import com.example.mayankaggarwal.dcare.models.StartedShift.CheckShiftStartRequest;
import com.example.mayankaggarwal.dcare.models.VerifyOTP.VerifyOtpRequest;
import com.example.mayankaggarwal.dcare.models.VerifyOTP.VerifyResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by mayankaggarwal on 12/02/17.
 */

public interface ApiInterface {

    @POST("bootup")
    Call<BootupResponse> bootup(@Body BootupRequest bootupRequest);

    @POST("generateotp")
    Call<OtpResponse> generateotp(@Body OtpRequest otpRequest);

    @POST("verifymobile")
    Call<VerifyResponse> verifymobile(@Body VerifyOtpRequest verifyOtpRequest);

    @POST("uploadmedia")
    Call<MediaResponse> uploadmedia(@Body MediaRequest mediaRequest);

    @POST("uploadprofile")
    Call<ProfileResponse> uploadprofile(@Body ProfileRequest profileRequest);

    @POST("fetchvendor")
    Call<JsonObject> fetchvendor(@Body StartShiftRequest startShiftRequest);

    @POST("crewshift")
    Call<ShiftStartResponse> crewshift(@Body ShiftStartRequest shiftStartRequest);

    @POST("getallorderscrew")
    Call<JsonObject> getallorderscrew(@Body GetOrderRequest getOrderRequest);

    @POST("keepcrewshiftlive")
    Call<JsonObject> keepShiftLive(@Body ShiftLiveRequest shiftLiveRequest);

    @POST("validatecrewshift")
    Call<JsonObject> validatecrewshift(@Body CheckShiftStartRequest checkShiftStartRequest);

    @POST("changeorderstatecrew")
    Call<JsonObject> changeorderstatecrew(@Body ChangeRequest changeRequest);

    @POST("getreasons")
    Call<JsonObject> getreasons(@Body StartShiftRequest startShiftRequest);

//    @GET("faculties.json")
//    Call<FacultiesData> getFaculties();

}
