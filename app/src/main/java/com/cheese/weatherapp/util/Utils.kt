package com.cheese.weatherapp.util

import android.content.Context
import android.widget.Toast
import com.cheese.weatherapp.data.models.CurrentWeather
import com.cheese.weatherapp.data.models.WeatherResponse

fun shortToast(context: Context, message:String) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()
}

fun getCurrentWeatherFromWeatherResponse(weatherResponse: WeatherResponse): CurrentWeather {
    return CurrentWeather(
        location = "${weatherResponse.location.region}, ${weatherResponse.location.country}",
        temperature = weatherResponse.current.temperature,
        maxTemperature = weatherResponse.forecast.days[0].day.maxTemperature,
        minTemperature = weatherResponse.forecast.days[0].day.maxTemperature,
        weatherStatus = weatherResponse.current.condition.weatherStatus,
        pressure = weatherResponse.forecast.days[0].hours[0].pressure,
        cloud = weatherResponse.forecast.days[0].hours[0].cloud,
        windSpeed = weatherResponse.forecast.days[0].hours[0].windSpeed,
        humidity = weatherResponse.forecast.days[0].hours[0].humidity
    )
}