package com.example.weatherapp.viewmodels

import com.example.weatherapp.data.model.WeatherForecast

data class WeatherForecastState (
    val forecast: WeatherForecast? = null,
    val error: String = ""
)