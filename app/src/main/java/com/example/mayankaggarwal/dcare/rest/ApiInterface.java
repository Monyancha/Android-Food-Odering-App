package com.example.mayankaggarwal.dcare.rest;


import com.example.mayankaggarwal.dcare.models.Bootup.BootupRequest;
import com.example.mayankaggarwal.dcare.models.Bootup.BootupResponse;
import com.example.mayankaggarwal.dcare.models.Details.MediaUpload.MediaRequest;
import com.example.mayankaggarwal.dcare.models.Details.MediaUpload.MediaResponse;
import com.example.mayankaggarwal.dcare.models.Details.ProfileUpload.ProfileRequest;
import com.example.mayankaggarwal.dcare.models.Details.ProfileUpload.ProfileResponse;
import com.example.mayankaggarwal.dcare.models.GetOTP.OtpRequest;
import com.example.mayankaggarwal.dcare.models.GetOTP.OtpResponse;
import com.example.mayankaggarwal.dcare.models.VerifyOTP.VerifyOtpRequest;
import com.example.mayankaggarwal.dcare.models.VerifyOTP.VerifyResponse;

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


//    @GET("faculties.json")
//    Call<FacultiesData> getFaculties();

}
