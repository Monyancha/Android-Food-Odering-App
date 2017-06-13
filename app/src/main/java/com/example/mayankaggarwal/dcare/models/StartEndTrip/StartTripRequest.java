package com.example.mayankaggarwal.dcare.models.StartEndTrip;

import com.example.mayankaggarwal.dcare.models.Details.MediaUpload.HeaderMediaRequest;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * Created by mayankaggarwal on 13/06/17.
 */

public class StartTripRequest {
    @SerializedName("header")
    @Expose
    public HeaderMediaRequest header;
    @SerializedName("payload")
    @Expose
    public JsonObject payload;
}
