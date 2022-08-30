package com.example.weatherapp.data.model

import com.example.weatherapp.viewmodels.models.HourlyForecastUI
import java.util.*

data class HourlyForecast(
    val hours: List<String>,
    val temperatures: List<Double>,
){
    fun toHourlyForecastUI(): List<HourlyForecastUI>{
        val forecasts = mutableListOf<HourlyForecastUI>()
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        for (index in hours.indices ){
            val dateAndHour = hours[index].split('T')
            var limit = false

            if(!limit && dateAndHour[1].split(':')[0].toInt() > currentHour)
                limit = true

            if(limit && forecasts.size < 10){
                val date = dateAndHour[0].split('-')
                val forecast = HourlyForecastUI(
                    hour = "${date[2]}-${date[1]}\n${dateAndHour[1]}",
                    temperature = temperatures[index]
                )
                forecasts.add(forecast)
            }
        }

        return forecasts
    }
}
