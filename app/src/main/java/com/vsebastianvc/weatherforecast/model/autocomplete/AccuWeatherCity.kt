package com.vsebastianvc.weatherforecast.model.autocomplete

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "city_tbl")
data class AccuWeatherCity(
    @PrimaryKey
    @ColumnInfo(name = "location_key")
    @SerializedName(value = "Key")
    val key: String,

    @Embedded(prefix = "adm_area_")
    @SerializedName(value = "AdministrativeArea")
    val administrativeArea: AdministrativeArea,

    @Embedded(prefix = "country_")
    @SerializedName(value = "Country")
    val country: Country,

    @SerializedName(value = "LocalizedName")
    val localizedName: String,

    @SerializedName(value = "Rank")
    val rank: Int,

    @SerializedName(value = "Type")
    val type: String,

    @SerializedName(value = "Version")
    val version: Int
)
