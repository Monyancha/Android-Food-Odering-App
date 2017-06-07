
package com.example.mayankaggarwal.dcare.models.Bootup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payload {
    @SerializedName("deviceid")
    @Expose
    public String deviceid;
    @SerializedName("os_type")
    @Expose
    public String osType;
    @SerializedName("fcm_token")
    @Expose
    public String fcmToken;

}
