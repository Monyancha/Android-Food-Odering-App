package com.example.mayankaggarwal.dcare.models.GetOTP;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 30/05/17.
 */

public class OtpRequest {
    @SerializedName("header")
    @Expose
    public OtpHeader header;
    @SerializedName("payload")
    @Expose
    public OtpPayload payload;
}
