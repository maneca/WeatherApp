package com.example.weatherapp.viewmodels

import android.app.Application
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager

import androidx.lifecycle.viewModelScope
import com.example.weatherapp.GPSTracker
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.viewmodels.models.WeatherForecastState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val app: Application,
    private val repository: WeatherRepository
) : DisposableViewModel() {

    private val _state = MutableStateFlow(WeatherForecastState())
    val state = _state.asStateFlow()

    fun getWeatherForecast(
        latitude: Double,
        longitude: Double,
        cityName: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getWeatherForecast(latitude, longitude)
            response.subscribeOn(Schedulers.io())
                .subscribe(
                    { _state.value = state.value.copy(
                        forecast = it.toWeatherForecastUI(cityName),
                        error = ""
                    ) },
                    {_state.value = state.value.copy(
                        forecast = null,
                        error = it.message ?: ""
                    )  })
                .addToDisposables()
        }
    }

    fun getCurrentLocation() {
        val gps = GPSTracker(app.applicationContext)

        // check if GPS location can get Location
        if (gps.canGetLocation()) {

            val locationManager = app.applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager

            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

                val longitude = gps.getLongitude()
                val latitude = gps.getLatitude()
                val cityName = gps.getCityName()

                getWeatherForecast(latitude, longitude, cityName)
            }
        }
    }
}