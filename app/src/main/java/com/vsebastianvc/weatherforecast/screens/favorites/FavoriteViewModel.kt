package com.vsebastianvc.weatherforecast.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsebastianvc.weatherforecast.model.autocomplete.AccuWeatherCity
import com.vsebastianvc.weatherforecast.repository.AccuWeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: AccuWeatherDbRepository) :
    ViewModel() {

    private var _favList = MutableStateFlow<List<AccuWeatherCity>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged().collect { listOfFavorites ->
                _favList.value = listOfFavorites
            }
        }
    }

    fun insertFavorite(accuWeatherCity: AccuWeatherCity) =
        viewModelScope.launch { repository.insertFavorite(accuWeatherCity) }

    fun deleteFavorite(accuWeatherCity: AccuWeatherCity) =
        viewModelScope.launch { repository.deleteFavorite(accuWeatherCity) }
}