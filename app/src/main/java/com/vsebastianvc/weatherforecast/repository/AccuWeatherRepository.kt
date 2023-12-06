package com.vsebastianvc.weatherforecast.repository

import com.vsebastianvc.weatherforecast.data.DataOrException
import com.vsebastianvc.weatherforecast.model.autocomplete.AccuWeatherCity
import com.vsebastianvc.weatherforecast.model.currentweather.CurrentWeather
import com.vsebastianvc.weatherforecast.model.dailyforecast.Forecast
import com.vsebastianvc.weatherforecast.network.AccuWeatherApi
import javax.inject.Inject

class AccuWeatherRepository @Inject constructor(private val api: AccuWeatherApi) {

    suspend fun getCurrentWeather(locationKey: String): DataOrException<List<CurrentWeather>, Boolean, Exception> {
        val response = try {
            api.getCurrentWeather(locationKey = locationKey)
        } catch (e: Exception) {
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }

    suspend fun getDailyForecast(
        locationKey: String,
        isMetric: Boolean,
    ): DataOrException<Forecast, Boolean, Exception> {
        val response = try {
            api.getDailyForecast(locationKey = locationKey, isMetric = isMetric)
        } catch (e: Exception) {
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }

    suspend fun getLocationFromSearch(city: String): DataOrException<List<AccuWeatherCity>, Boolean, Exception> {
        val response = try {
            api.getLocationFromSearch(keyStroke = city)
        } catch (e: Exception) {
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }
}