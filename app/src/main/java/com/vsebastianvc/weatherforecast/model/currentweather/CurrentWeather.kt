package com.vsebastianvc.weatherforecast.model.currentweather

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName(value = "EpochTime")
    val epochTime: Int,

    @SerializedName(value = "HasPrecipitation")
    val hasPrecipitation: Boolean,

    @SerializedName(value = "IsDayTime")
    val isDayTime: Boolean,

    @SerializedName(value = "Temperature")
    val temperature: Temperature,

    @SerializedName(value = "WeatherIcon")
    val weatherIcon: Int,

    @SerializedName(value = "WeatherText")
    val weatherText: String,

    @SerializedName(value = "RelativeHumidity")
    val relativeHumidity: Int,

    @SerializedName(value = "RealFeelTemperature")
    val realFeelTemperature: RealFeelTemperature,

    @SerializedName(value = "Wind")
    val wind: Wind,

    @SerializedName(value = "Pressure")
    val pressure: Pressure,

    @SerializedName(value = "UVIndex")
    val uvIndex: Int,

    @SerializedName(value = "UVIndexText")
    val uvIndexText: String
)