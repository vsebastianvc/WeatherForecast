package com.vsebastianvc.weatherforecast.model.dailyforecast

import com.google.gson.annotations.SerializedName

data class Minimum(
    @SerializedName(value = "Unit")
    val unit: String,

    @SerializedName(value = "UnitType")
    val unitType: Int,

    @SerializedName(value = "Value")
    val value: Double
)