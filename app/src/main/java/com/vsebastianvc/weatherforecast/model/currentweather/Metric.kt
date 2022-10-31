package com.vsebastianvc.weatherforecast.model.currentweather

import com.google.gson.annotations.SerializedName

data class Metric(
    @SerializedName(value = "Unit")
    val unit: String,

    @SerializedName(value = "UnitType")
    val unitType: Int,

    @SerializedName(value = "Value")
    val value: Double
)