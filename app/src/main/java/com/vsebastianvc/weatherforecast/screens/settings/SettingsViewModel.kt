package com.vsebastianvc.weatherforecast.screens.settings

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.vsebastianvc.weatherforecast.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) :
    ViewModel() {

    fun setUnit(unit: String) {
        with(sharedPreferences.edit()) {
            putString(
                Constants.SHARED_PREFS_IS_METRIC_KEY,
                unit
            )
            apply()
        }
    }

    fun getUnit(): String? {
        return sharedPreferences.getString(
            Constants.SHARED_PREFS_IS_METRIC_KEY,
            Constants.METRIC
        )
    }
}