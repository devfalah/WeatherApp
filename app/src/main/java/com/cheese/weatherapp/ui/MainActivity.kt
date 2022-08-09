package com.cheese.weatherapp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.cheese.weatherapp.R
import com.cheese.weatherapp.databinding.ActivityMainBinding
import com.cheese.weatherapp.data.models.WeatherState
import com.cheese.weatherapp.data.request.ApiClient
import com.cheese.weatherapp.status.NetworkResult
import com.example.mikmok.data.models.WeatherMain
import com.example.mikmok.util.Constants
import com.example.mikmok.util.toCelsius
import com.example.mikmok.util.toPercent
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity<ActivityMainBinding>() {
    val gson = Gson()
    private val apiClient by lazy { ApiClient() }
    override val LOG_TAG: String = Constants.MAIN_ACTIVITY
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    override fun setUp() {
        handleResponseStatus()
        binding.refreshButton.setOnClickListener {
            handleResponseStatus()
        }
    }

    override fun addCallbacks() {
        onSearchChange()
    }


    private fun onSearchChange() {
        val observableSearch = Observable.create<String> { emitter ->
            binding.searchCity.doOnTextChanged { text, _, _, count ->
                if (count != Constants.INDEXT_COUNT_EMPTY) {
                    emitter.onNext(text.toString())
                }
            }
        }.debounce(Constants.TIME.toLong(), TimeUnit.SECONDS)
        observableSearch.subscribe(
            { cityName ->
                handleResponseStatus(
                    NetworkResult.Loading(),
                    cityName
                )
            },
            {
                handleResponseStatus(
                    NetworkResult.Fail(it.message.toString())
                )
            }
        )
    }

    private fun runResponseOnUiThread(
        progressBarView: Int,
        contentView: Int,
        errorTextView: Int,
        refreshButton: Int,
        funToImplement: () -> Unit,
    ) {
        runOnUiThread {
            binding.progressBar.visibility = progressBarView
            binding.content.visibility = contentView
            binding.errorText.visibility = errorTextView
            binding.refreshButton.visibility = refreshButton
            funToImplement()
        }
    }

    private fun handleResponseStatus(
        responseData: NetworkResult<WeatherMain>? = NetworkResult.Loading(),
        cityName: String = Constants.CITY
    ) {
        when (responseData) {
            is NetworkResult.Loading -> {
                runResponseOnUiThread(View.VISIBLE, View.GONE, View.GONE, View.GONE) {
                    getWeather(cityName = cityName)
                }
            }
            is NetworkResult.Success -> {
                runResponseOnUiThread(View.GONE, View.VISIBLE, View.GONE, View.GONE) {
                    processComingData(responseData)
                }
            }
            is NetworkResult.Fail -> {
                runResponseOnUiThread(View.GONE, View.GONE, View.VISIBLE, View.VISIBLE) {
                    binding.errorText.text = responseData.message
                }
            }
            else -> {
                runResponseOnUiThread(View.GONE, View.GONE, View.VISIBLE, View.VISIBLE) {
                    binding.errorText.text = responseData?.message
                }
            }
        }
    }

    private fun processComingData(responseData: NetworkResult.Success<WeatherMain>) {
        when (Constants.NOT_FOUND) {
            responseData.data?.cod.toString() -> {
                showToast(responseData.data?.message)
            }
            else -> {
                if (responseData.data != null) {
                    attributeBinding(responseData.data)
                }
                weatherState(responseData.data?.weather?.get(Constants.INDEXT_WEATHER)?.main?.uppercase())
            }
        }
    }

    private fun getWeather(cityName: String) {
        apiClient.makeApiRequest(cityName = cityName.trim()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                handleResponseStatus(
                    responseData = NetworkResult.Fail(e.message.toString()),
                    cityName = cityName
                )
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { jsonString ->
                    val result = gson.fromJson(jsonString, WeatherMain::class.java)
                    handleResponseStatus(
                        responseData = NetworkResult.Success(result),
                        cityName = cityName
                    )
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun attributeBinding(result: WeatherMain) {
        binding.apply {
            cityName.text = "${result.name},${result.sys.country}"
            temp.text = "${result.main.temp.toCelsius()}°"
            description.text = result.weather[Constants.INDEXT_WEATHER].description
            maxMin.text =
                "${result.main.tempMax.toCelsius()}° Max - ${result.main.tempMin.toCelsius()}° Min"
            valueWind.text = "${result.wind.speed} km/h"
            valueClouds.text = "${result.clouds.all.toPercent()}%"
            valueHumidity.text = "${result.main.humidity.toPercent()}%"
            valuePressure.text = "${result.main.pressure.toPercent()}%"
        }
    }

    private fun weatherState(weatherState: String?) {
        when (weatherState) {
            WeatherState.CLOUDS.toString() -> {
                binding.weather.setAnimation(R.raw.cloudy)
                binding.weather.playAnimation()
            }
            WeatherState.CLEAR.toString() -> {
                binding.weather.setAnimation(R.raw.sunny)
                binding.weather.playAnimation()
            }
            WeatherState.RAIN.toString() -> {
                binding.weather.setAnimation(R.raw.rain)
                binding.weather.playAnimation()
            }
        }
    }

}
