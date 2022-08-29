package com.example.weatherapp.di

import android.app.Application
import com.example.weatherapp.R
import com.example.weatherapp.data.repository.WeatherRepositoryImp
import com.example.weatherapp.domain.remote.WeatherApi
import com.example.weatherapp.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class WeatherModules {
    @Provides
    @Singleton
    fun provideWeatherApi(app: Application): WeatherApi {
        val logging = HttpLoggingInterceptor { message -> Timber.tag("OkHttp").d(message) }
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()

        return Retrofit.Builder()
            .baseUrl(app.getString(R.string.base_url))
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApi): WeatherRepository {
        return WeatherRepositoryImp(api)
    }
}