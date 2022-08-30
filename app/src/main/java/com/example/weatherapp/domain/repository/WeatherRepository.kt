package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.model.WeatherForecast
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {

    fun getWeatherForecast(
        latitude: Double,
        longitude: Double,
        hourly: String = "temperature_2m")
    : Single<WeatherForecast>
}