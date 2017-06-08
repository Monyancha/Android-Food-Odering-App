package com.example.mayankaggarwal.dcare.models.StartedShift;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 08/06/17.
 */

public class PayloadCheckShift {
    @SerializedName("vendor_id")
    @Expose
    public String vendorId;
    @SerializedName("shift_id")
    @Expose
    public String shiftId;
    @SerializedName("last_local_timestamp")
    @Expose
    public String lastLocalTimestamp;
    @SerializedName("current_latitude")
    @Expose
    public String currentLatitude;
    @SerializedName("current_longitude")
    @Expose
    public String currentLongitude;
}
