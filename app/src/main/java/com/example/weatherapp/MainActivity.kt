package com.example.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.viewmodels.WeatherViewModel
import com.example.weatherapp.viewmodels.models.HourlyForecastUI
import com.example.weatherapp.widgets.Country
import com.example.weatherapp.widgets.DropDownList
import com.example.weatherapp.widgets.countries
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
                Box(
                    modifier = Modifier.background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.White,
                                colorResource(R.color.teal_200),
                            )
                        )
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(Alignment.CenterVertically),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            LocationSelection(viewModel)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { onClickCurrentLocation(viewModel) },
                                Modifier.height(55.dp),
                                contentPadding = PaddingValues(
                                    start = 20.dp,
                                    top = 12.dp,
                                    end = 20.dp,
                                    bottom = 12.dp
                                )
                            ) {
                                Icon(
                                    Icons.Filled.LocationOn,
                                    contentDescription = "Current location",
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        state.value.forecast?.let {
                            Image(
                                painter = painterResource(typeOfWeather(it.currentWeather.temperature)),
                                "",
                                modifier = Modifier
                                    .size(100.dp, 100.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = it.cityName,
                                Modifier.fillMaxWidth(),
                                style = TextStyle(
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            )
                            Text(
                                text = "${it.currentWeather.temperature} ºC",
                                Modifier.fillMaxWidth(),
                                style = TextStyle(
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            NextHours(forecast = it.hourlyForecast)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = state.value.error, style = TextStyle(color = Color.Red))
                    }
                }
            }
        }
    }

    @Composable
    fun NextHours(forecast: List<HourlyForecastUI>) {
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            items(items = forecast) { item ->
                Column(
                    Modifier.padding(4.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = item.hour, Modifier.fillMaxWidth(), style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Image(
                        painter = painterResource(typeOfWeather(item.temperature)),
                        "",
                        modifier = Modifier
                            .size(36.dp, 36.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "${item.temperature} ºC")
                }
            }
        }
    }

    @Composable
    fun LocationSelection(viewModel: WeatherViewModel) {
        val text = remember { mutableStateOf("") } // initial value
        val isOpen = remember { mutableStateOf(false) } // initial value
        val openCloseOfDropDownList: (Boolean) -> Unit = {
            isOpen.value = it
        }
        val userSelectedString: (Country) -> Unit = {
            viewModel.getWeatherForecast(it.latitude, it.longitude, it.name)
        }
        Box {
            Column {
                OutlinedTextField(
                    value = text.value,
                    onValueChange = { text.value = it },
                    label = { Text(text = stringResource(id = R.string.select_country)) },
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                DropDownList(
                    requestToOpen = isOpen.value,
                    list = countries,
                    openCloseOfDropDownList,
                    userSelectedString
                )
            }
            Spacer(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Transparent)
                    .padding(4.dp)
                    .clickable(
                        onClick = { isOpen.value = true }
                    )
            )
        }
    }

    private fun onClickCurrentLocation(viewModel: WeatherViewModel) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestMultiplePermissions.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        } else {
            viewModel.getCurrentLocation()
        }
    }

    private val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach {
            // TODO
            //viewModel.getCurrentLocation()
        }
    }

    private fun typeOfWeather(temperature: Double): Int {
        return when (temperature) {
            in 0.0..15.0 -> R.drawable.cold
            in 15.1..30.0 -> R.drawable.warm
            in 30.1..50.0 -> R.drawable.hot
            else -> R.drawable.freezing
        }
    }
}