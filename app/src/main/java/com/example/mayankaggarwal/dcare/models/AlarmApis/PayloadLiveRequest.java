package com.example.mayankaggarwal.dcare.models.AlarmApis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 08/06/17.
 */

public class PayloadLiveRequest {
    @SerializedName("vendor_id")
    @Expose
    public String vendorId;
    @SerializedName("shift_id")
    @Expose
    public String shiftId;
    @SerializedName("timestamp")
    @Expose
    public String timestamp;
}
