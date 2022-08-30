package com.example.weatherapp.widgets

data class Country(
    val name: String,
    val latitude: Double,
    val longitude: Double
)

val countries = listOf(
    Country("Lisbon", 38.7072, -9.1355),
    Country("Berlin", 52.5235, 13.4115),
    Country("Paris", 48.8567, 2.3510),
    Country("London", 51.5002, -0.1262),
    Country("Vienna", 48.2092, 16.3728),
    Country("Brussels", 50.8371, 4.3676),
    Country("Moscow", 55.7558, 37.6176),
    Country("Sofia", 42.7105, 23.3238),
    Country("Copenhagen", 55.6763, 12.5681),
    Country("Athens", 37.9792, 23.7166),
)
