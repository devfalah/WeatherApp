package com.cheese.weatherapp.data.models

import com.cheese.weatherapp.util.Constants
import com.google.gson.annotations.SerializedName

data class Location (
    @SerializedName(Constants.LOCAL_TIME)
    val dateTime: String,
    @SerializedName(Constants.REGION)
    val region: String,
    @SerializedName(Constants.COUNTRY)
    val country: String,
)