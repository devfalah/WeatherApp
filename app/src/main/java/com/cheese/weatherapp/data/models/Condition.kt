package com.cheese.weatherapp.data.models

import com.cheese.weatherapp.util.Constants
import com.google.gson.annotations.SerializedName

data class Condition(
    @SerializedName(Constants.TEXT)
    val weatherStatus: String,
)
