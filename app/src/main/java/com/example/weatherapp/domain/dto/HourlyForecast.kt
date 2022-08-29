package com.example.weatherapp.domain.dto

import com.google.gson.annotations.SerializedName

data class HourlyForecast(
    @SerializedName("time") val hours: List<String>,
    @SerializedName("temperature_2m") val temperatures: List<Double>,
)
