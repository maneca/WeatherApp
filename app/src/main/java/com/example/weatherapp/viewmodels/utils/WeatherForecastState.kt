package com.example.weatherapp.viewmodels.utils

import com.example.weatherapp.data.model.WeatherForecast

data class WeatherForecastState (
    val forecast: WeatherForecast? = null,
    val error: String = ""
)