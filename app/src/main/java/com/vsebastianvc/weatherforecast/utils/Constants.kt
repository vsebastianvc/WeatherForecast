package com.vsebastianvc.weatherforecast.utils

import com.vsebastianvc.weatherforecast.model.autocomplete.AccuWeatherCity
import com.vsebastianvc.weatherforecast.model.autocomplete.AdministrativeArea
import com.vsebastianvc.weatherforecast.model.autocomplete.Country

object Constants {

    //DataBase
    const val WEATHER_DATABASE = "weather_database"

    //AccuWeather API
    const val BASE_URL = "http://dataservice.accuweather.com/"
    const val ACCUWEATHER_API_KEY = "y92enVjnuGu7YoKer60IZh1970xseMIQ"
    const val GET_CURRENT_WEATHER_CONDITION = "/currentconditions/v1/{key}"
    const val GET_FIVE_DAYS_DAILY_FORECASTS = "/forecasts/v1/daily/5day/{key}"
    const val GET_LOCATION_FROM_TEXT = "/locations/v1/autocomplete"
    const val API_KEY = "apikey"
    const val KEY = "key"
    const val DETAILS = "details"
    const val LANGUAGE = "language"
    const val METRIC_UNIT = "metric"
    const val EN_US_LANGUAGE = "en-us"
    const val KEY_STROKE = "q"

    //SharedPreferences
    const val IMPERIAL = "Imperial °F, mph"
    const val METRIC = "Metric °C, m/s"
    const val SHARED_PREFS_FILE_KEY = "com.vsebastianvc.weatherforecast.settings_preferences"
    const val SHARED_PREFS_IS_METRIC_KEY = "SHARED_PREFS_IS_METRIC_KEY"
    const val SHARED_PREFS_ACCUWEATHER_KEY = "SHARED_PREFS_ACCUWEATHER_KEY"

    //Navigation Graph
    const val CITY_KEY = "city"
    val DEFAULT_CITY = AccuWeatherCity(
        administrativeArea = AdministrativeArea(id = "ON", localizedName = "Ontario"),
        country = Country(id = "CA", localizedName = "Canada"),
        key = "55488",
        localizedName = "Toronto",
        rank = 21,
        type = "City",
        version = 1
    )
}