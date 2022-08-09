package com.cheese.weatherapp.data.models

import com.cheese.weatherapp.util.Constants
import com.google.gson.annotations.SerializedName

data class Hour(
    @SerializedName(Constants.TEMPERATURE)
    val temperature: String,
    @SerializedName(Constants.WIND_SPEED_KPH)
    val windSpeed: String,
    @SerializedName(Constants.PRESSURE)
    val pressure: String,
    @SerializedName(Constants.CLOUD)
    val cloud: String,
    @SerializedName(Constants.HUMIDITY)
    val humidity: String,
)
