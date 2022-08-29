package com.example.weatherapp.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.viewmodels.utils.WeatherForecastState
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

    init {
        getWeatherForecast()
    }

    private fun getWeatherForecast(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getWeatherForecast(41.53, -8.44, "temperature_2m")
            response.subscribeOn(Schedulers.io())
                .subscribe(
                    { _state.value = state.value.copy(
                        forecast = it,
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