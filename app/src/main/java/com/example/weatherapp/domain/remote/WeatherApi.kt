package com.example.weatherapp.domain.remote

import com.example.weatherapp.domain.dto.WeatherForecastDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast")
    fun getWeatherForecast(@Query("latitude") latitude: Double,
                           @Query("longitude") longitude: Double,
                           @Query("hourly") hourly: String,
                           @Query("current_weather") currentWeather: Boolean)
    : Single<WeatherForecastDTO>
}