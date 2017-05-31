package com.example.mayankaggarwal.dcare.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 30/05/17.
 */

public class BootupRequest {

    @SerializedName("header")
    @Expose
    public Header header;
    @SerializedName("payload")
    @Expose
    public Payload payload;

}
