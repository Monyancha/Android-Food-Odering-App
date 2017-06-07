package com.example.mayankaggarwal.dcare.models.Details.ProfileUpload;

import com.example.mayankaggarwal.dcare.models.Details.MediaUpload.HeaderMediaRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 07/06/17.
 */

public class ProfileRequest {
    @SerializedName("header")
    @Expose
    public HeaderMediaRequest header;
    @SerializedName("payload")
    @Expose
    public PayloadProfileRequest payload;

}
