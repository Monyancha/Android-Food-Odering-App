package com.example.mayankaggarwal.dcare.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 31/05/17.
 */

public class VerifyPayload {
    @SerializedName("source")
    @Expose
    public String source;
    @SerializedName("r_mobile")
    @Expose
    public String rMobile;
    @SerializedName("country_code")
    @Expose
    public String countryCode;
    @SerializedName("timestamp_of_request")
    @Expose
    public String timestampOfRequest;
    @SerializedName("otp")
    @Expose
    public String otp;
}
