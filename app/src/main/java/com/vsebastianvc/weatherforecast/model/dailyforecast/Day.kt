package com.vsebastianvc.weatherforecast.model.dailyforecast

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName(value = "Icon")
    val icon: Int,

    @SerializedName(value = "IconPhrase")
    val iconPhrase: String,
)