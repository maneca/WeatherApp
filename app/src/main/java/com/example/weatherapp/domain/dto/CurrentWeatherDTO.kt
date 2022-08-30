package com.example.weatherapp.domain.dto

import com.example.weatherapp.data.model.CurrentWeather
import com.google.gson.annotations.SerializedName

data class CurrentWeatherDTO(
    @SerializedName("temperature") val temperature: Double,
    @SerializedName("windspeed") val windSpeed: Float,
    @SerializedName("winddirection") val windDirection: Float,
    @SerializedName("time") val time: String,
){
    fun toCurrentWeather(): CurrentWeather{
        return CurrentWeather(
            temperature,
            windSpeed,
            windDirection,
            time
        )
    }
}
