package com.vsebastianvc.weatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vsebastianvc.weatherforecast.model.Favorite
import com.vsebastianvc.weatherforecast.model.Unit

@Database(entities = [Favorite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}