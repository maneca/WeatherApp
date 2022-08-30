package com.example.weatherapp.data.model

data class CurrentWeather(
    val temperature: Double,
    val windSpeed: Float,
    val windDirection: Float,
    val time: String,
)
