package com.example.mayankaggarwal.dcare.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 30/05/17.
 */

public class PayloadResponse {
    @SerializedName("crewid")
    @Expose
    public String crewid;
    @SerializedName("wpr_token")
    @Expose
    public String wprToken;
    @SerializedName("shift_refresh_frequency_rate")
    @Expose
    public String shiftRefreshFrequencyRate;
    @SerializedName("local_shift_refresh_frequency_rate")
    @Expose
    public String localShiftRefreshFrequencyRate;
    @SerializedName("location_refresh_frequency_rate")
    @Expose
    public String locationRefreshFrequencyRate;
    @SerializedName("crew_state")
    @Expose
    public String crewState;
}
