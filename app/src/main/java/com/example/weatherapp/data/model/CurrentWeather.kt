package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    val temperature: Double,
    val windSpeed: Float,
    val windDirection: Float,
    val time: String,
)
