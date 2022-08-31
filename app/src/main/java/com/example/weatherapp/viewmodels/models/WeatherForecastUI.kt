package com.example.weatherapp.viewmodels.models

import com.example.weatherapp.data.model.CurrentWeather

data class WeatherForecastUI(
    val currentWeather: CurrentWeather,
    val hourlyForecast: List<HourlyForecastUI>,
    val cityName: String = ""
)
