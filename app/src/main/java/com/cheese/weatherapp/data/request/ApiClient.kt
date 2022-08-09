package com.cheese.weatherapp.data.request

import com.cheese.weatherapp.util.Constants
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

class ApiClient {
    fun makeApiRequest(
        location: String,
        time: Int,
        daysCount: Int
    ): Call {
        return OkHttpClient().newCall(
            Request.Builder().run {
                url(createUrl(location, time, daysCount))
                get()
                build()
            }
        )
    }

    private fun createUrl(location: String, time: Int, daysCount: Int): HttpUrl {
        return HttpUrl.Builder().apply {
            scheme(Constants.SCHEME)
            host(Constants.HOST)
            addPathSegment(Constants.URL_FIRST_PS)
            addPathSegment(Constants.URL_SECOND_PS)
            addQueryParameter(Constants.URL_KEY_QP, Constants.URL_KEY_QP_VALUE)
            addQueryParameter(Constants.URL_Q_QP, location)
            addQueryParameter(Constants.URL_HOUR_QP, time.toString())
            addQueryParameter(Constants.URL_DAYS_QP, daysCount.toString())
        }.build()
    }
}