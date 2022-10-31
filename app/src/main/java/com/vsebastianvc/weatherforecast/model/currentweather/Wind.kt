package com.vsebastianvc.weatherforecast.model.currentweather

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName(value = "Speed")
    val speed: Speed
)