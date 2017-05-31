package com.example.mayankaggarwal.dcare.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 30/05/17.
 */

public class OtpResponse {
    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("payload")
    @Expose
    public PayloadOtp payload;
    @SerializedName("error")
    @Expose
    public Error error;
}
