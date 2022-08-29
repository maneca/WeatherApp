package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.model.WeatherForecast
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Query

interface WeatherRepository {

    fun getWeatherForecast(
        latitude: Double,
        longitude: Double,
        hourly: String)
    : Observable<WeatherForecast>
}