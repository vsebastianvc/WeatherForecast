package com.vsebastianvc.weatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vsebastianvc.weatherforecast.model.autocomplete.AccuWeatherCity
import com.vsebastianvc.weatherforecast.screens.about.AboutScreen
import com.vsebastianvc.weatherforecast.screens.favorites.FavoriteScreen
import com.vsebastianvc.weatherforecast.screens.main.MainScreen
import com.vsebastianvc.weatherforecast.screens.main.MainViewModel
import com.vsebastianvc.weatherforecast.screens.search.SearchScreen
import com.vsebastianvc.weatherforecast.screens.settings.SettingsScreen
import com.vsebastianvc.weatherforecast.screens.splash.WeatherSplashScreen
import com.vsebastianvc.weatherforecast.utils.Constants
import com.vsebastianvc.weatherforecast.utils.fromJson

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name
    ) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        val route = WeatherScreens.MainScreen.name
        composable(
            "$route/{city}", arguments = listOf(
                navArgument(name = Constants.CITY_KEY) {
                    type = NavType.StringType
                })
        ) { city ->
            city.arguments?.getString(Constants.CITY_KEY)?.let { jsonString ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(
                    navController = navController,
                    mainViewModel,
                    accuWeatherCity = jsonString.fromJson(AccuWeatherCity::class.java)
                )
            }
        }

        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }

        composable(WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }

        composable(WeatherScreens.FavoriteScreen.name) {
            FavoriteScreen(navController = navController)
        }
    }
}