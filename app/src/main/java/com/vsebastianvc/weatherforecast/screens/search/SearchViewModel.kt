package com.vsebastianvc.weatherforecast.screens.search

import androidx.lifecycle.ViewModel
import com.vsebastianvc.weatherforecast.data.DataOrException
import com.vsebastianvc.weatherforecast.model.autocomplete.AccuWeatherCity
import com.vsebastianvc.weatherforecast.repository.AccuWeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val accuWeatherRepository: AccuWeatherRepository) :
    ViewModel() {

    suspend fun getLocationFromText(city: String): DataOrException<List<AccuWeatherCity>, Boolean, Exception> {
        return accuWeatherRepository.getLocationFromSearch(city = city)
    }
}