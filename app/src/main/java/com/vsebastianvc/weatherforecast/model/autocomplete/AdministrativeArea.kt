package com.vsebastianvc.weatherforecast.model.autocomplete

import com.google.gson.annotations.SerializedName

data class AdministrativeArea(
    @SerializedName(value = "ID")
    val id: String,

    @SerializedName(value = "LocalizedName")
    val localizedName: String
)