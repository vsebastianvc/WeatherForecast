package com.vsebastianvc.weatherforecast.model.autocomplete

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName(value = "ID")
    val id: String,

    @SerializedName(value = "LocalizedName")
    val localizedName: String
)