package com.example.weatherapp.data.model

import com.example.weatherapp.domain.dto.CurrentWeather
import com.example.weatherapp.domain.dto.HourlyForecast

data class WeatherForecast(
    val latitude: Float,
    val longitude: Float,
    val currentWeather: CurrentWeather,
    val hourlyForecast: HourlyForecast
)
