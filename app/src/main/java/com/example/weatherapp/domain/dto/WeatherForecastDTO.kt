package com.example.weatherapp.domain.dto

import com.example.weatherapp.data.model.WeatherForecast
import com.google.gson.annotations.SerializedName

data class WeatherForecastDTO(
    @SerializedName("latitude") val latitude: Float,
    @SerializedName("longitude") val longitude: Float,
    @SerializedName("current_weather") val currentWeather: CurrentWeather,
    @SerializedName("hourly") val hourlyForecast: HourlyForecast
){
    fun toWeatherForecast(): WeatherForecast{
        return WeatherForecast(
            this.latitude,
            this.longitude,
            this.currentWeather,
            this.hourlyForecast
        )
    }
}
