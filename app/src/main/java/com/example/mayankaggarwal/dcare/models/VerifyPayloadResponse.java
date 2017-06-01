package com.example.mayankaggarwal.dcare.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 31/05/17.
 */

public class VerifyPayloadResponse {
    @SerializedName("previous_try_count")
    @Expose
    public Integer previousTryCount;
}
