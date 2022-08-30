package com.example.weatherapp.viewmodels

import androidx.lifecycle.viewModelScope
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
    private val repository: WeatherRepository
) : DisposableViewModel() {

    private val _state = MutableStateFlow(WeatherForecastState())
    val state = _state.asStateFlow()

    fun getWeatherForecast(
        latitude: Double,
        longitude: Double
    ){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getWeatherForecast(latitude, longitude)
            response.subscribeOn(Schedulers.io())
                .subscribe(
                    { _state.value = state.value.copy(
                        forecast = it.toWeatherForecastUI(),
                        error = ""
                    ) },
                    {_state.value = state.value.copy(
                        forecast = null,
                        error = it.message ?: ""
                    )  })
                .addToDisposables()
        }
    }
}