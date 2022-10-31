package com.vsebastianvc.weatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vsebastianvc.weatherforecast.model.autocomplete.AccuWeatherCity

@Database(entities = [AccuWeatherCity::class], version = 2, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}