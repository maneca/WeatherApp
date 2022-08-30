package com.example.weatherapp.domain.dto

import com.example.weatherapp.data.model.WeatherForecast
import com.google.gson.annotations.SerializedName

data class WeatherForecastDTO(
    @SerializedName("latitude") val latitude: Float,
    @SerializedName("longitude") val longitude: Float,
    @SerializedName("current_weather") val currentWeather: CurrentWeatherDTO,
    @SerializedName("hourly") val hourlyForecast: HourlyForecastDTO
){
    fun toWeatherForecast(): WeatherForecast{
        return WeatherForecast(
            latitude,
            longitude,
            currentWeather.toCurrentWeather(),
            hourlyForecast.toHourlyForecast()
        )
    }
}
