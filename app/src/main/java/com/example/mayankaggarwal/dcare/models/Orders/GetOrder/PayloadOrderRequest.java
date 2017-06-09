package com.example.mayankaggarwal.dcare.models.Orders.GetOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 08/06/17.
 */

public class PayloadOrderRequest {
    @SerializedName("vendor_id")
    @Expose
    public String vendorId;
    @SerializedName("shift_id")
    @Expose
    public String shiftId;
}
