package com.example.mayankaggarwal.dcare.models.Orders.OrderState;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 09/06/17.
 */

public class PayloadChangeRequest {
    @SerializedName("order_id")
    @Expose
    public String orderId;
    @SerializedName("vendor_id")
    @Expose
    public String vendorId;
    @SerializedName("cust_rating")
    @Expose
    public String custRating;
    @SerializedName("reason_code")
    @Expose
    public String reasonCode;
    @SerializedName("reason_text")
    @Expose
    public String reasonText;
    @SerializedName("actual_delivery_time")
    @Expose
    public String actualDeliveryTime;
    @SerializedName("crew_pickup_time")
    @Expose
    public String crewPickupTime;
    @SerializedName("estimated_delivery_time")
    @Expose
    public String estimatedDeliveryTime;
    @SerializedName("new_state_code")
    @Expose
    public String newStateCode;
}
