package com.vsebastianvc.weatherforecast.screens.favorites

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vsebastianvc.weatherforecast.R
import com.vsebastianvc.weatherforecast.model.autocomplete.AccuWeatherCity
import com.vsebastianvc.weatherforecast.navigation.WeatherScreens
import com.vsebastianvc.weatherforecast.utils.getLocation
import com.vsebastianvc.weatherforecast.utils.toJson
import com.vsebastianvc.weatherforecast.widgets.WeatherAppBar

@Composable
fun FavoriteScreen(
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = stringResource(id = R.string.favorite_cities),
                icon = Icons.Default.ArrowBack,
                isMainScreen = false,
                navController = navController
            ) { navController.popBackStack() }
        },
        content = { padding ->
            Surface(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 5.dp, vertical = 10.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val list = favoriteViewModel.favList.collectAsState().value
                    if (list.isNotEmpty()) {
                        LazyColumn {
                            items(items = list) {
                                CityRow(
                                    it,
                                    navController = navController,
                                    favoriteViewModel = favoriteViewModel
                                )
                            }
                        }
                    } else {
                        NoFavoriteCities()
                    }
                }
            }
        }
    )
}

@Composable
fun CityRow(
    accuWeatherCity: AccuWeatherCity,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel
) {
    Card(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable { navController.navigate(WeatherScreens.MainScreen.name + "/${accuWeatherCity.toJson()}") },
        shape = MaterialTheme.shapes.medium,
        elevation = 2.dp,
        border = BorderStroke(1.dp, MaterialTheme.colors.onSurface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = accuWeatherCity.getLocation(),
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = stringResource(id = R.string.delete_description),
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable {
                        favoriteViewModel.deleteFavorite(accuWeatherCity)
                    },
                tint = MaterialTheme.colors.error,
            )
        }
    }
}

@Preview
@Composable
fun NoFavoriteCities() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_no_favorite),
            contentDescription = "No Favorites",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(95.dp)
        )
        Text(
            text = stringResource(id = R.string.no_favorites_added),
            style = MaterialTheme.typography.h3,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
    }
}
