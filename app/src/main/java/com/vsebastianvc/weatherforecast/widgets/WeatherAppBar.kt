package com.vsebastianvc.weatherforecast.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vsebastianvc.weatherforecast.model.autocomplete.AccuWeatherCity
import com.vsebastianvc.weatherforecast.navigation.WeatherScreens
import com.vsebastianvc.weatherforecast.screens.favorites.FavoriteViewModel
import com.vsebastianvc.weatherforecast.utils.Constants

@Composable
fun WeatherAppBar(
    title: String = "Title",
    accuWeatherCity: AccuWeatherCity = Constants.DEFAULT_CITY,
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onFavoritesClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val showDialog = remember {
        mutableStateOf(false)
    }

    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colors.onSecondary,
                style = MaterialTheme.typography.h6
            )
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = {
                    onAddActionClicked.invoke()
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                }

                IconButton(onClick = { showDialog.value = true }) {
                    Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "More Icon")
                }
            } else Box {}
        },
        navigationIcon = {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.clickable {
                        onButtonClicked.invoke()
                    }
                )
            }
            if (isMainScreen) {
                val isAlreadyFavList =
                    favoriteViewModel.favList.collectAsState().value.filter { item ->
                        item.key == accuWeatherCity.key
                    }
                if (isAlreadyFavList.isEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite Icon",
                        modifier = Modifier
                            .scale(1f)
                            .padding(start = 15.dp)
                            .clickable {
                                favoriteViewModel
                                    .insertFavorite(accuWeatherCity = accuWeatherCity)
                                    .run {
                                        onFavoritesClicked.invoke()
                                    }
                            },
                        tint = Color.Red.copy(alpha = 0.6f)
                    )
                } else {
                    Box {}
                }
            }
        },
        backgroundColor = Color.Transparent,
        elevation = elevation
    )
}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var expanded by remember {
        mutableStateOf(true)
    }
    val items = listOf("Favorites", "Settings", "About")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded, onDismissRequest = {
                expanded = false
            }, modifier = Modifier
                .width(140.dp)
                .background(MaterialTheme.colors.surface.copy(alpha = 0.6f))
        ) {
            items.forEachIndexed { _, text ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDialog.value = false
                }) {
                    Icon(
                        imageVector = when (text) {
                            "Favorites" -> Icons.Default.FavoriteBorder
                            "Settings" -> Icons.Default.Settings
                            else -> Icons.Default.Info
                        }, contentDescription = null,
                        tint = MaterialTheme.colors.onSurface
                    )
                    Text(
                        text = text,
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .clickable {
                                navController.navigate(
                                    when (text) {
                                        "Favorites" -> WeatherScreens.FavoriteScreen.name
                                        "Settings" -> WeatherScreens.SettingsScreen.name
                                        else -> WeatherScreens.AboutScreen.name
                                    }
                                )
                            },
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}
