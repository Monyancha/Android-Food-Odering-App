package com.example.mayankaggarwal.dcare.models.StartShift.StartEndShift;

import com.example.mayankaggarwal.dcare.models.Bootup.Error;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 08/06/17.
 */

public class ShiftStartResponse {
    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("payload")
    @Expose
    public PayloadResponseStart payload;
    @SerializedName("error")
    @Expose
    public Error error;
}
