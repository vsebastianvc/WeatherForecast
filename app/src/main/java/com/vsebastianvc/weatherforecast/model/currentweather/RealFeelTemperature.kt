package com.vsebastianvc.weatherforecast.model.currentweather

import com.google.gson.annotations.SerializedName

data class RealFeelTemperature(
    @SerializedName(value = "Imperial")
    val imperial: Imperial,

    @SerializedName(value = "Metric")
    val metric: Metric
)