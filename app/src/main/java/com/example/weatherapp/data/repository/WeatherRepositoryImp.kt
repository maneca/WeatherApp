package com.example.weatherapp.data.repository

import com.example.weatherapp.data.model.WeatherForecast
import com.example.weatherapp.domain.remote.WeatherApi
import com.example.weatherapp.domain.repository.WeatherRepository
import io.reactivex.rxjava3.core.Single

class WeatherRepositoryImp(
    private val api: WeatherApi
): WeatherRepository {

    override fun getWeatherForecast(
        latitude: Double,
        longitude: Double,
        hourly: String
    ): Single<WeatherForecast> {
        return api.getWeatherForecast(latitude, longitude, hourly, true).map { it.toWeatherForecast() }
    }
}