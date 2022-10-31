@file:Suppress("unused", "unused")

package com.vsebastianvc.weatherforecast.repository

import com.vsebastianvc.weatherforecast.data.WeatherDao
import com.vsebastianvc.weatherforecast.model.autocomplete.AccuWeatherCity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccuWeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {
    fun getFavorites(): Flow<List<AccuWeatherCity>> = weatherDao.getFavorites()
    suspend fun insertFavorite(accuWeatherCity: AccuWeatherCity) =
        weatherDao.insertFavorite(accuWeatherCity)

    suspend fun updateFavorite(accuWeatherCity: AccuWeatherCity) =
        weatherDao.updateFavorite(accuWeatherCity)

    suspend fun deleteFavorite(accuWeatherCity: AccuWeatherCity) =
        weatherDao.deleteFavorite(accuWeatherCity)

}