package com.cheese.weatherapp.util

import com.cheese.weatherapp.R
import com.cheese.weatherapp.data.models.WeatherState


fun Double.toPercent(): Double {
    return String.format("%.2f", (this / 100)).toDouble()
}

fun String.getWeatherStateAnimation(): Int {
    return when (this) {
        WeatherState.CLOUDS.toString() -> R.raw.cloudy
        WeatherState.CLEAR.toString() -> R.raw.sunny
        WeatherState.RAIN.toString() -> R.raw.rain
        else -> R.raw.sunny
    }
}

