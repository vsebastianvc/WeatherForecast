package com.vsebastianvc.weatherforecast.screens.splash

import androidx.lifecycle.ViewModel
import com.vsebastianvc.weatherforecast.model.autocomplete.AccuWeatherCity
import com.vsebastianvc.weatherforecast.utils.CustomSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherSplashViewModel @Inject constructor(
    private val customSharedPreferences: CustomSharedPreferences
) : ViewModel() {

    fun getAccuWeatherCity(): AccuWeatherCity = customSharedPreferences.getAccuWeatherCity()

}