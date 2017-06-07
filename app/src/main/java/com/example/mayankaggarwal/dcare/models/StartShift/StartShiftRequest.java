package com.example.mayankaggarwal.dcare.models.StartShift;

import com.example.mayankaggarwal.dcare.models.Details.MediaUpload.HeaderMediaRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 07/06/17.
 */

public class StartShiftRequest {
    @SerializedName("header")
    @Expose
    public HeaderMediaRequest header;
    @SerializedName("payload")
    @Expose
    public PayloadShiftRequest payload;

}
