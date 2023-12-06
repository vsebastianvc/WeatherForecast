package com.vsebastianvc.weatherforecast.utils

enum class LocationWithStates {
    US, CA;

    companion object {
        fun isSupported(countryId: String): Boolean =
            entries
                .asSequence()
                .filter { it.name == countryId }
                .any()
    }
}