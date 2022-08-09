package com.cheese.weatherapp.data.models

data class CurrentWeather(
    val location: String,
    val temperature: String,
    val maxTemperature: String,
    val minTemperature: String,
    val weatherStatus: String,
    val pressure: String,
    val cloud: String,
    val windSpeed: String,
    val humidity: String
)