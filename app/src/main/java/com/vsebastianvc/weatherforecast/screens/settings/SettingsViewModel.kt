package com.vsebastianvc.weatherforecast.screens.settings

import androidx.lifecycle.ViewModel
import com.vsebastianvc.weatherforecast.utils.CustomSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val customSharedPreferences: CustomSharedPreferences
) : ViewModel() {

    fun setUnit(unit: String) {
        customSharedPreferences.setUnit(unit)
    }

    fun getUnit(): String? {
        return customSharedPreferences.getUnit()
    }
}