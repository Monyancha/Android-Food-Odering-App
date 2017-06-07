package com.example.mayankaggarwal.dcare.models.GetOTP;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 30/05/17.
 */

public class OtpHeader {
    @SerializedName("requesID")
    @Expose
    public String requesID;
    @SerializedName("App_version")
    @Expose
    public String appVersion;
    @SerializedName("user_id")
    @Expose
    public String user_id;
    @SerializedName("wpr_token")
    @Expose
    public String wprToken;

}
