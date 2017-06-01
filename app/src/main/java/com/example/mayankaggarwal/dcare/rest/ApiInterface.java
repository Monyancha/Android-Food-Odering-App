package com.example.mayankaggarwal.dcare.rest;


import com.example.mayankaggarwal.dcare.models.BootupRequest;
import com.example.mayankaggarwal.dcare.models.BootupResponse;
import com.example.mayankaggarwal.dcare.models.OtpRequest;
import com.example.mayankaggarwal.dcare.models.OtpResponse;
import com.example.mayankaggarwal.dcare.models.VerifyOtpRequest;
import com.example.mayankaggarwal.dcare.models.VerifyResponse;

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


//    @GET("faculties.json")
//    Call<FacultiesData> getFaculties();

}
