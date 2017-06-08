package com.example.mayankaggarwal.dcare.models.StartShift.StartEndShift;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 08/06/17.
 */

public class PayloadRequest {
    @SerializedName("vendor_id")
    @Expose
    public String vendorId;
    @SerializedName("current_latitude")
    @Expose
    public String currentLatitude;
    @SerializedName("current_longitude")
    @Expose
    public String currentLongitude;
    @SerializedName("__comments__")
    @Expose
    public String comments;
    @SerializedName("operation")
    @Expose
    public String operation;
    @SerializedName("activity_list")
    @Expose
    public String activityList;
}
