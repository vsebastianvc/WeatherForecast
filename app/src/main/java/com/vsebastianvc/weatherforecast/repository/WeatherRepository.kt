package com.vsebastianvc.weatherforecast.repository

import com.vsebastianvc.weatherforecast.data.DataOrException
import com.vsebastianvc.weatherforecast.model.Weather
import com.vsebastianvc.weatherforecast.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(cityQuery: String, units: String): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery, units = units)
        } catch (e: Exception) {
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }
}