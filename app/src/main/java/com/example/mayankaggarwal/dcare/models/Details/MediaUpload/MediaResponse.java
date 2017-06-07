package com.example.mayankaggarwal.dcare.models.Details.MediaUpload;

import com.example.mayankaggarwal.dcare.models.Bootup.Error;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 06/06/17.
 */

public class MediaResponse {
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
