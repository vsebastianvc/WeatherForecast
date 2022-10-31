package com.vsebastianvc.weatherforecast.utils

import com.google.gson.Gson
import com.vsebastianvc.weatherforecast.model.autocomplete.AccuWeatherCity
import com.vsebastianvc.weatherforecast.model.dailyforecast.DailyForecast
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * If Country Id is "US" or "CA"
 *      City Localized name [, Admin area id] ← which would be state/province
 * Else
 *      City Localized name [, Country Id] ← the 2 letter country code
 *
 * @param useFullCountry if true it will use the full country name (ex "Japan") instead of
 *                       the abbreviation.  Defaults to false
 */
fun AccuWeatherCity.getLocation(useFullCountry: Boolean = false): String {
    val list = mutableListOf<String?>(this.localizedName)
    this.country.let {
        if (LocationWithStates.isSupported(it.id)) {
            list.add(this.administrativeArea.id)
        } else {
            if (useFullCountry) list.add(it.localizedName) else list.add(it.id)
        }
    }
    return list.filterNotNull().joinToString(", ")
}

/**
 * if Calendar.HOUR_OF_DAY is between 12:00 am to 6:59pm,
 * false if is between 7:00 pm to 11:59pm
 */
fun String.isDay(): Boolean {
    return LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME).hour in 0..18
}

/**
 * Function to get daily details
 * @return daily forecast icon phrase
 */
fun DailyForecast.getDailyForecastIconPhrase(date: String): String {
    return if (date.isDay()) {
        this.day.iconPhrase
    } else {
        this.night.iconPhrase
    }
}


/**
 * Function to get daily icon value
 * @return daily forecast icon
 */
fun DailyForecast.getDailyForecastIcon(date: String): Int {
    return if (date.isDay()) {
        this.day.icon
    } else {
        this.night.icon
    }
}

fun <A> String.fromJson(type: Class<A>): A {
    return Gson().fromJson(this, type)
}

fun <A> A.toJson(): String? {
    return Gson().toJson(this)
}

