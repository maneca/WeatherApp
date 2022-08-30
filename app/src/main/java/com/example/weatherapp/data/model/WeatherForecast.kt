package com.example.weatherapp.data.model

import com.example.weatherapp.viewmodels.models.WeatherForecastUI

data class WeatherForecast(
    val latitude: Float,
    val longitude: Float,
    val currentWeather: CurrentWeather,
    val hourlyForecast: HourlyForecast
){
    fun toWeatherForecastUI(): WeatherForecastUI{
        return WeatherForecastUI(
            currentWeather,
            hourlyForecast.toHourlyForecastUI()
        )
    }
}
