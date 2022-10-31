package com.vsebastianvc.weatherforecast.utils

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.vsebastianvc.weatherforecast.model.autocomplete.AccuWeatherCity
import com.vsebastianvc.weatherforecast.model.autocomplete.AdministrativeArea
import com.vsebastianvc.weatherforecast.model.autocomplete.Country
import com.vsebastianvc.weatherforecast.model.currentweather.*
import com.vsebastianvc.weatherforecast.model.currentweather.Temperature
import com.vsebastianvc.weatherforecast.model.dailyforecast.*

class SampleCurrentWeatherDataProvider : PreviewParameterProvider<CurrentWeather> {
    override val values: Sequence<CurrentWeather> = sequenceOf(
        CurrentWeather(
            epochTime = 1664940840,
            hasPrecipitation = true,
            isDayTime = true,
            temperature = Temperature(
                metric = Metric(value = 12.8, unit = "C", unitType = 17),
                imperial = Imperial(value = 55.0, unit = "F", unitType = 18)
            ),
            weatherIcon = 33,
            weatherText = "Clear",
            relativeHumidity = 62,
            realFeelTemperature = RealFeelTemperature(
                metric = Metric(value = 10.8, unit = "C", unitType = 17),
                imperial = Imperial(value = 51.0, unit = "F", unitType = 18)
            ),
            wind = Wind(
                Speed(
                    metric = Metric(value = 13.0, unit = "km/h", unitType = 7),
                    imperial = Imperial(value = 8.1, unit = "mi/h", unitType = 9)
                )
            ),
            pressure = Pressure(
                metric = Metric(value = 1020.5, unit = "mb", unitType = 14),
                imperial = Imperial(value = 30.14, unit = "inHg", unitType = 12)
            ),
            uvIndex = 0,
            uvIndexText = "Low"
        )
    )
}

class SampleDailyForecastDataProvider : PreviewParameterProvider<DailyForecast> {
    override val values: Sequence<DailyForecast> = sequenceOf(
        DailyForecast(
            date = "2022-10-04T07:00:00-04:00",
            day = Day(
                icon = 2,
                iconPhrase = "Mostly Sunny"
            ),
            epochDate = 1664881200,
            night = Night(
                icon = 34,
                iconPhrase = "Mostly Clear"
            ),
            temperature = com.vsebastianvc.weatherforecast.model.dailyforecast.Temperature(
                maximum = Maximum(
                    unit = "C",
                    unitType = 17,
                    value = 16.7
                ),
                minimum = Minimum(
                    unit = "C",
                    unitType = 17,
                    value = 10.0
                )
            )
        )
    )
}

class SampleAccuWeatherCityDataProvider : PreviewParameterProvider<AccuWeatherCity> {
    override val values: Sequence<AccuWeatherCity> = sequenceOf(
        AccuWeatherCity(
            key = "55488",
            administrativeArea = AdministrativeArea(
                id = "ON",
                localizedName = "Ontario"
            ),
            country = Country(
                id = "CA",
                localizedName = "Canada"
            ),
            localizedName = "Toronto",
            rank = 21,
            type = "City",
            version = 1
        )
    )
}