package com.vsebastianvc.weatherforecast.utils

import android.content.SharedPreferences
import com.google.gson.Gson
import com.vsebastianvc.weatherforecast.model.autocomplete.AccuWeatherCity
import javax.inject.Inject

class CustomSharedPreferences @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun setAccuWeatherCity(accuWeatherCity: AccuWeatherCity) {
        with(sharedPreferences.edit()) {
            putString(
                Constants.SHARED_PREFS_ACCUWEATHER_KEY,
                accuWeatherCity.toJson()
            )
            apply()
        }
    }

    fun getAccuWeatherCity(): AccuWeatherCity {
        val accuWeatherCityJson = sharedPreferences.getString(
            Constants.SHARED_PREFS_ACCUWEATHER_KEY,
            Constants.DEFAULT_CITY.toJson()
        )
        return Gson().fromJson(accuWeatherCityJson, AccuWeatherCity::class.java)
    }

    fun setUnit(unit: String) {
        with(sharedPreferences.edit()) {
            putString(
                Constants.SHARED_PREFS_IS_METRIC_KEY,
                unit
            )
            apply()
        }
    }

    fun getUnit(): String? {
        return sharedPreferences.getString(
            Constants.SHARED_PREFS_IS_METRIC_KEY,
            Constants.METRIC
        )
    }
}