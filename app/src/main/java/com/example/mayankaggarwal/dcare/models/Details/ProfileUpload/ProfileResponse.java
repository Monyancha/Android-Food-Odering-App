package com.example.mayankaggarwal.dcare.models.Details.ProfileUpload;

import com.example.mayankaggarwal.dcare.models.Bootup.Error;
import com.example.mayankaggarwal.dcare.models.Details.MediaUpload.PayloadMediaResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 07/06/17.
 */

public class ProfileResponse {
    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("payload")
    @Expose
    public PayloadMediaResponse payload;
    @SerializedName("error")
    @Expose
    public Error error;
}

