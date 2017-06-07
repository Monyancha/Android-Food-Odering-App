package com.example.mayankaggarwal.dcare.models.Details.MediaUpload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 06/06/17.
 */

public class PayloadMediaRequest {
    @SerializedName("media_for")
    @Expose
    public String mediaFor;
    @SerializedName("media_type")
    @Expose
    public String mediaType;
    @SerializedName("media_checksum")
    @Expose
    public String mediaChecksum;
    @SerializedName("media_data")
    @Expose
    public String mediaData;
    @SerializedName("media_file_name")
    @Expose
    public String mediaFileName;
    @SerializedName("media_size_in_mb")
    @Expose
    public String mediaSizeInMb;
}
