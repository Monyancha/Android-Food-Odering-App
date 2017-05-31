
package com.example.mayankaggarwal.dcare.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BootupResponse {
    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("payload")
    @Expose
    public PayloadResponse payload;
    @SerializedName("error")
    @Expose
    public Error error;


}
