package com.cheese.weatherapp.data.models

import com.cheese.weatherapp.util.Constants
import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName(Constants.TEMPERATURE)
    val temperature: String,
    @SerializedName(Constants.CONDITION)
    val condition: Condition,
)