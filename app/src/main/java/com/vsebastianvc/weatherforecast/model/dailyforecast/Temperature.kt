package com.vsebastianvc.weatherforecast.model.dailyforecast

import com.google.gson.annotations.SerializedName

data class Temperature(
    @SerializedName(value = "Maximum")
    val maximum: Maximum,

    @SerializedName(value = "Minimum")
    val minimum: Minimum
)