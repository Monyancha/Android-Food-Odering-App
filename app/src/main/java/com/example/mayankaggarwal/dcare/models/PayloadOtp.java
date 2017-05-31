package com.example.mayankaggarwal.dcare.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 30/05/17.
 */

public class PayloadOtp {
    @SerializedName("state")
    @Expose
    public Integer state;
}
