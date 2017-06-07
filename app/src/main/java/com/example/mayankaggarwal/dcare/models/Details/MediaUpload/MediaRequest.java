package com.example.mayankaggarwal.dcare.models.Details.MediaUpload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 06/06/17.
 */

public class MediaRequest {
    @SerializedName("header")
    @Expose
    public HeaderMediaRequest header;
    @SerializedName("payload")
    @Expose
    public PayloadMediaRequest payload;

}
