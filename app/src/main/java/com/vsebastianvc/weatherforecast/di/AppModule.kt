package com.vsebastianvc.weatherforecast.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.vsebastianvc.weatherforecast.data.WeatherDao
import com.vsebastianvc.weatherforecast.data.WeatherDatabase
import com.vsebastianvc.weatherforecast.network.AccuWeatherApi
import com.vsebastianvc.weatherforecast.utils.Constants
import com.vsebastianvc.weatherforecast.utils.CustomSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao =
        weatherDatabase.weatherDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(context, WeatherDatabase::class.java, Constants.WEATHER_DATABASE)
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideAccuweatherWeatherApi(): AccuWeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AccuWeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.SHARED_PREFS_FILE_KEY, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideCustomSharedPreference(sharedPreferences: SharedPreferences): CustomSharedPreferences {
        return CustomSharedPreferences(sharedPreferences = sharedPreferences)
    }
}