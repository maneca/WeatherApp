package com.example.weatherapp.viewmodels.models

data class WeatherForecastState (
    val forecast: WeatherForecastUI? = null,
    val error: String = ""
)