package com.cheese.weatherapp.data.models

import com.cheese.weatherapp.util.Constants
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName(Constants.LOCATION)
    val location: Location,
    @SerializedName(Constants.CURRENT)
    val current: Current,
    @SerializedName(Constants.FORECAST)
    val forecast: Forecast
)
