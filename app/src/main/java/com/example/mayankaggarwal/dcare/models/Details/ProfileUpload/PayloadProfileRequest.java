package com.example.mayankaggarwal.dcare.models.Details.ProfileUpload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 07/06/17.
 */

public class PayloadProfileRequest {

    @SerializedName("fname")
    @Expose
    public String fname;
    @SerializedName("mname")
    @Expose
    public String mname;
    @SerializedName("lname")
    @Expose
    public String lname;
    @SerializedName("sex")
    @Expose
    public String sex;
    @SerializedName("dob")
    @Expose
    public String dob;
    @SerializedName("nickname")
    @Expose
    public String nickname;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("ssn_number")
    @Expose
    public String ssnNumber;
    @SerializedName("record_number")
    @Expose
    public String recordNumber;
    @SerializedName("mobile_country_code")
    @Expose
    public String mobileCountryCode;
    @SerializedName("mobile_number")
    @Expose
    public String mobileNumber;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("address2")
    @Expose
    public String address2;
    @SerializedName("city_code")
    @Expose
    public String cityCode;
    @SerializedName("postal_code")
    @Expose
    public String postalCode;
    @SerializedName("home_location_lat")
    @Expose
    public String homeLocationLat;
    @SerializedName("home_location_long")
    @Expose
    public String homeLocationLong;

}
