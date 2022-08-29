package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = hiltViewModel<WeatherViewModel>()
            val state = viewModel.state.collectAsState()
            val scaffoldState = rememberScaffoldState()

            Scaffold(
                scaffoldState = scaffoldState
            ) {
                Box(modifier = Modifier.background( brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        colorResource(R.color.teal_200),
                    )
                ))) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        state.value.forecast?.let {
                            Image(painter = painterResource(typeOfWeather(it.currentWeather.temperature)),
                                "content description",
                                modifier = Modifier
                                    .size(100.dp, 100.dp)
                                )
                            Text(text = "Berlin",
                                Modifier.fillMaxWidth(),
                                style = TextStyle(fontSize = 38.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center))
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "${it.currentWeather.temperature} ºC",
                                Modifier.fillMaxWidth(),
                                style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center))
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = state.value.error, style = TextStyle(color = Color.Red))
                    }
                }
            }
        }
    }

    private fun typeOfWeather(temperature: Float) : Int{
        return when(temperature){
            in 0.0..15.0 -> R.drawable.cold
            in 15.1..30.0 -> R.drawable.warm
            in 30.1..50.0 -> R.drawable.hot
            else -> R.drawable.freezing
        }
    }
}