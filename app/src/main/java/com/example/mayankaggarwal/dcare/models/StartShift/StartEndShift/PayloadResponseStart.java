package com.example.mayankaggarwal.dcare.models.StartShift.StartEndShift;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 08/06/17.
 */

public class PayloadResponseStart {
    @SerializedName("crew_state")
    @Expose
    public String crewState;
    @SerializedName("shift_id")
    @Expose
    public Integer shiftId;
}
