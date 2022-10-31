package com.vsebastianvc.weatherforecast.data

import androidx.room.*
import com.vsebastianvc.weatherforecast.model.autocomplete.AccuWeatherCity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * from city_tbl")
    fun getFavorites(): Flow<List<AccuWeatherCity>>

    @Query("SELECT * from city_tbl where location_key =:locationKey")
    suspend fun getFavById(locationKey: String): AccuWeatherCity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(accuWeatherCity: AccuWeatherCity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(accuWeatherCity: AccuWeatherCity)

    @Query("DELETE from city_tbl")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteFavorite(accuWeatherCity: AccuWeatherCity)
}