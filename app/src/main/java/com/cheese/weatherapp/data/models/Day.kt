package com.cheese.weatherapp.data.models

import com.cheese.weatherapp.util.Constants
import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName(Constants.MAX_TEMPERATURE)
    val maxTemperature: String,
    @SerializedName(Constants.MIN_TEMPERATURE)
    val minTemperature: String,
)
