package com.example.mayankaggarwal.dcare.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 30/05/17.
 */

public class OtpPayload {
    @SerializedName("source")
    @Expose
    public String source;
    @SerializedName("r_mobile")
    @Expose
    public String rMobile;
    @SerializedName("timestamp_of_request")
    @Expose
    public String timestampOfRequest;
    @SerializedName("request_flag")
    @Expose
    public Integer requestFlag;
    @SerializedName("country_code")
    @Expose
    public String country_code;
}
