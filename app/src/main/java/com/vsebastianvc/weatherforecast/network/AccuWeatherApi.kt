package com.vsebastianvc.weatherforecast.network

import com.vsebastianvc.weatherforecast.model.autocomplete.AccuWeatherCity
import com.vsebastianvc.weatherforecast.model.currentweather.CurrentWeather
import com.vsebastianvc.weatherforecast.model.dailyforecast.Forecast
import com.vsebastianvc.weatherforecast.utils.Constants
import com.vsebastianvc.weatherforecast.utils.Constants.API_KEY
import com.vsebastianvc.weatherforecast.utils.Constants.DETAILS
import com.vsebastianvc.weatherforecast.utils.Constants.EN_US_LANGUAGE
import com.vsebastianvc.weatherforecast.utils.Constants.GET_CURRENT_WEATHER_CONDITION
import com.vsebastianvc.weatherforecast.utils.Constants.GET_FIVE_DAYS_DAILY_FORECASTS
import com.vsebastianvc.weatherforecast.utils.Constants.GET_LOCATION_FROM_TEXT
import com.vsebastianvc.weatherforecast.utils.Constants.KEY
import com.vsebastianvc.weatherforecast.utils.Constants.KEY_STROKE
import com.vsebastianvc.weatherforecast.utils.Constants.LANGUAGE
import com.vsebastianvc.weatherforecast.utils.Constants.METRIC_UNIT
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface AccuWeatherApi {
    @GET(value = GET_CURRENT_WEATHER_CONDITION)
    suspend fun getCurrentWeather(
        @Path(KEY) locationKey: String,
        @Query(LANGUAGE) language: String = EN_US_LANGUAGE,
        @Query(DETAILS) details: Boolean = true,
        @Query(API_KEY) apikey: String = Constants.ACCUWEATHER_API_KEY
    ): List<CurrentWeather>

    @GET(value = GET_FIVE_DAYS_DAILY_FORECASTS)
    suspend fun getDailyForecast(
        @Path(KEY) locationKey: String,
        @Query(LANGUAGE) language: String = EN_US_LANGUAGE,
        @Query(DETAILS) details: Boolean = false,
        @Query(METRIC_UNIT) isMetric: Boolean = true,
        @Query(API_KEY) apiKey: String = Constants.ACCUWEATHER_API_KEY
    ): Forecast

    @GET(value = GET_LOCATION_FROM_TEXT)
    suspend fun getLocationFromSearch(
        @Query(KEY_STROKE) key_stroke: String?,
        @Query(LANGUAGE) language: String = EN_US_LANGUAGE,
        @Query(API_KEY) apiKey: String = Constants.ACCUWEATHER_API_KEY
    ): List<AccuWeatherCity>
}