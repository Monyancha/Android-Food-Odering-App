package com.example.mayankaggarwal.dcare.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 31/05/17.
 */

public class VerifyOtpRequest {
    @SerializedName("header")
    @Expose
    public VerifyHeader header;
    @SerializedName("payload")
    @Expose
    public VerifyPayload payload;
}