package com.vsebastianvc.weatherforecast.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsebastianvc.weatherforecast.model.Favorite
import com.vsebastianvc.weatherforecast.model.Unit
import com.vsebastianvc.weatherforecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDbRepository) :
    ViewModel() {
    private var _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnits().distinctUntilChanged().collect { listOfUnits ->
                if (listOfUnits.isNullOrEmpty()) {

                } else {
                    _unitList.value = listOfUnits
                }
            }
        }
    }

    fun insertUnit(unit: Unit) =
        viewModelScope.launch { repository.insertUnit(unit) }

    fun updateUnit(unit: Unit) =
        viewModelScope.launch { repository.updateUnit(unit) }

    fun deleteUnit(unit: Unit) =
        viewModelScope.launch { repository.deleteUnit(unit) }

    fun deleteAllUnits() =
        viewModelScope.launch { repository.deleteAllUnits() }
}