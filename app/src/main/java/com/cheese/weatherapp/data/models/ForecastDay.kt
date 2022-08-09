package com.cheese.weatherapp.data.models

import com.cheese.weatherapp.util.Constants
import com.google.gson.annotations.SerializedName

data class ForecastDay(
    @SerializedName(Constants.DATE)
    val date: String,
    @SerializedName(Constants.DAY)
    val day: Day,
    @SerializedName(Constants.HOUR)
    val hours: List<Hour>,
)