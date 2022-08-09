package com.cheese.weatherapp.ui

import android.util.Log
import com.cheese.weatherapp.data.models.CurrentWeather
import com.cheese.weatherapp.data.models.WeatherResponse
import com.cheese.weatherapp.data.request.ApiClient
import com.cheese.weatherapp.databinding.ActivityMainBinding
import com.cheese.weatherapp.util.Constants
import com.cheese.weatherapp.util.getCurrentWeatherFromWeatherResponse
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate){
    private val logTag: String = Constants.MALT

    override fun setUpOnCreateView() {
        getForecast("Baghdad", 12, 7)
    }

    private fun setUpCurrentWeather(currentWeather: CurrentWeather) {
        binding.run {
            cityName.text = currentWeather.location
            temp.text = currentWeather.temperature
            description.text = currentWeather.weatherStatus
            maxMin.text = ""
            valuePressure.text = currentWeather.pressure
            valueClouds.text = currentWeather.cloud
            valueWind.text = currentWeather.windSpeed
            valueHumidity.text = currentWeather.humidity
        }
    }

    private fun getForecast(
        location: String,
        time: Int,
        daysCount: Int
    ) {
        ApiClient().makeApiRequest(location, time, daysCount).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(logTag, "Failed to grab data due to: ${e.message.toString()}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d(logTag, "Success!")
                response.body?.string()?.let {
                    val weatherResponse = Gson().fromJson(it, WeatherResponse::class.java)
                    runOnUiThread {
                        setUpCurrentWeather(getCurrentWeatherFromWeatherResponse(weatherResponse))
                    }
                }
            }
        })
    }
}
