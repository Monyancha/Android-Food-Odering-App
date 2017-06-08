package com.example.mayankaggarwal.dcare.models.AlarmApis;

import com.example.mayankaggarwal.dcare.models.Details.MediaUpload.HeaderMediaRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 08/06/17.
 */

public class ShiftLiveRequest {
    @SerializedName("header")
    @Expose
    public HeaderMediaRequest header;
    @SerializedName("payload")
    @Expose
    public PayloadLiveRequest payload;
}
