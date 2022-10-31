package com.vsebastianvc.weatherforecast.model.dailyforecast

import com.google.gson.annotations.SerializedName

data class DailyForecast(
    @SerializedName(value = "Date")
    val date: String,

    @SerializedName(value = "Day")
    val day: Day,

    @SerializedName(value = "EpochDate")
    val epochDate: Int,

    @SerializedName(value = "Night")
    val night: Night,

    @SerializedName(value = "Temperature")
    val temperature: Temperature
)
