package com.vsebastianvc.weatherforecast.screens.main

import androidx.lifecycle.ViewModel
import com.vsebastianvc.weatherforecast.data.DataOrException
import com.vsebastianvc.weatherforecast.model.autocomplete.AccuWeatherCity
import com.vsebastianvc.weatherforecast.model.currentweather.CurrentWeather
import com.vsebastianvc.weatherforecast.model.dailyforecast.Forecast
import com.vsebastianvc.weatherforecast.repository.AccuWeatherRepository
import com.vsebastianvc.weatherforecast.utils.CustomSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val accuWeatherRepository: AccuWeatherRepository,
    private val customSharedPreferences: CustomSharedPreferences
) : ViewModel() {

    suspend fun getCurrentWeather(locationKey: String): DataOrException<List<CurrentWeather>, Boolean, Exception> {
        return accuWeatherRepository.getCurrentWeather(locationKey = locationKey)
    }

    suspend fun getDailyForecast(
        locationKey: String,
        isMetric: Boolean
    ): DataOrException<Forecast, Boolean, Exception> {
        return accuWeatherRepository.getDailyForecast(
            locationKey = locationKey,
            isMetric = isMetric
        )
    }

    fun setAccuWeatherCity(accuWeatherCity: AccuWeatherCity) {
        customSharedPreferences.setAccuWeatherCity(accuWeatherCity = accuWeatherCity)
    }

    fun getAccuWeatherCity(): AccuWeatherCity = customSharedPreferences.getAccuWeatherCity()
}