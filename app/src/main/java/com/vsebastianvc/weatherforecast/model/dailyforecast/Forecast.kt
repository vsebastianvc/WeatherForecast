package com.vsebastianvc.weatherforecast.model.dailyforecast

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName(value = "DailyForecasts")
    val dailyForecasts: List<DailyForecast>
)