package com.example.mayankaggarwal.dcare.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 31/05/17.
 */

public class VerifyHeader {
    @SerializedName("requestID")
    @Expose
    public String requestID;
    @SerializedName("App_version")
    @Expose
    public String appVersion;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("crew_id")
    @Expose
    public String crewId;
    @SerializedName("wpr_token")
    @Expose
    public String wprToken;
}
