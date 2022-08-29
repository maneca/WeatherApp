package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
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
                Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)) {
                        state.value.forecast?.let {
                            Text(text = it.hourlyForecast.temperatures.toString())
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = state.value.error, style = TextStyle(color = Color.Red))
                    }
                }
            }
        }
    }
}