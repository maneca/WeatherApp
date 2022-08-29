package com.example.weatherapp.domain.dto

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("temperature") val temperature: Float,
    @SerializedName("windspeed") val windSpeed: Float,
    @SerializedName("winddirection") val windDirection: Float,
    @SerializedName("time") val time: String,
)
