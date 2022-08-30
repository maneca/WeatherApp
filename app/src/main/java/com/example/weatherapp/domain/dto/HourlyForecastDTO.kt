package com.example.weatherapp.domain.dto

import com.example.weatherapp.data.model.HourlyForecast
import com.google.gson.annotations.SerializedName

data class HourlyForecastDTO(
    @SerializedName("time") val hours: List<String>,
    @SerializedName("temperature_2m") val temperatures: List<Double>,
){
    fun toHourlyForecast(): HourlyForecast{
        return HourlyForecast(
            hours,
            temperatures
        )
    }
}
