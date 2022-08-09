package com.cheese.weatherapp.data.models

import com.cheese.weatherapp.util.Constants
import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName(Constants.FORECAST_DAY)
    val days: List<ForecastDay>,
)
