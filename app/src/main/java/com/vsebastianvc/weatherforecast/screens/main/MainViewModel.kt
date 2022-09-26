package com.vsebastianvc.weatherforecast.screens.main

import androidx.lifecycle.ViewModel
import com.vsebastianvc.weatherforecast.data.DataOrException
import com.vsebastianvc.weatherforecast.model.Weather
import com.vsebastianvc.weatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {


    suspend fun getWeatherData(city: String, units: String): DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(cityQuery = city, units = units)
    }

   /* val data: MutableState<DataOrException<Weather, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception("")))

    init {
        loadWeather()
    }

    private fun loadWeather() {
        getWeather("Seattle")
    }

    private fun getWeather(city: String) {
        viewModelScope.launch {
            if (city.isEmpty()) {
                return@launch
            }
            data.value.loading = true
            data.value = repository.getWeather(cityQuery = city)

            if (data.value.data.toString().isNotEmpty()) {
                data.value.loading = false
            }
        }
    }*/
}