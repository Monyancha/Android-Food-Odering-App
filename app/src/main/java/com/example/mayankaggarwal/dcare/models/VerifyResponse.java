package com.example.mayankaggarwal.dcare.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 31/05/17.
 */

public class VerifyResponse {
    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("payload")
    @Expose
    public VerifyPayloadResponse payload;
    @SerializedName("error")
    @Expose
    public Error error;
}
