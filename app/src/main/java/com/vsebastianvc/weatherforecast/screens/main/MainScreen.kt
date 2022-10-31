package com.vsebastianvc.weatherforecast.screens.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vsebastianvc.weatherforecast.R
import com.vsebastianvc.weatherforecast.data.DataOrException
import com.vsebastianvc.weatherforecast.model.autocomplete.AccuWeatherCity
import com.vsebastianvc.weatherforecast.model.currentweather.CurrentWeather
import com.vsebastianvc.weatherforecast.model.dailyforecast.DailyForecast
import com.vsebastianvc.weatherforecast.model.dailyforecast.Forecast
import com.vsebastianvc.weatherforecast.navigation.WeatherScreens
import com.vsebastianvc.weatherforecast.screens.favorites.FavoriteViewModel
import com.vsebastianvc.weatherforecast.screens.settings.SettingsViewModel
import com.vsebastianvc.weatherforecast.utils.*
import com.vsebastianvc.weatherforecast.widgets.*
import retrofit2.HttpException

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    accuWeatherCity: AccuWeatherCity
) {
    val locationKey: String = if (accuWeatherCity != mainViewModel.getAccuWeatherCity()) {
        mainViewModel.setAccuWeatherCity(accuWeatherCity)
        accuWeatherCity.key.ifBlank { mainViewModel.getAccuWeatherCity().key }
    } else {
        mainViewModel.getAccuWeatherCity().key
    }

    val unitFromCache = settingsViewModel.getUnit()

    var unit by remember {
        mutableStateOf(Constants.METRIC_UNIT)
    }

    var isMetric by remember {
        mutableStateOf(true)
    }

    if (!isInternetAvailable(LocalContext.current)) {
        ErrorScreen(
            id = R.drawable.ic_no_wifi,
            mainMessage = R.string.no_connection,
            secondMessage = R.string.please_try_check_your_connection
        )
    } else {
        if (!unitFromCache.isNullOrEmpty()) {

            unit = unitFromCache.split(" ")[0].lowercase()
            isMetric = unit == Constants.METRIC_UNIT

            val currentWeatherData =
                produceState<DataOrException<List<CurrentWeather>, Boolean, Exception>>(
                    initialValue = DataOrException(loading = true)
                ) {
                    value = mainViewModel.getCurrentWeather(locationKey = locationKey)
                }.value

            val dailyForecastData =
                produceState<DataOrException<Forecast, Boolean, Exception>>(
                    initialValue = DataOrException(loading = true)
                ) {
                    value =
                        mainViewModel.getDailyForecast(
                            locationKey = locationKey,
                            isMetric = isMetric
                        )
                }.value

            if (currentWeatherData.loading == true) {
                Loading()
            } else if (currentWeatherData.data != null && dailyForecastData.data != null) {
                MainContent(
                    title = accuWeatherCity.getLocation(),
                    accuWeatherCity = accuWeatherCity,
                    currentWeather = currentWeatherData.data!![0],
                    dailyForecast = dailyForecastData.data!!.dailyForecasts,
                    navController = navController,
                    isMetric = isMetric
                )
            } else if (currentWeatherData.e != null || dailyForecastData.e != null) {
                when (val error = currentWeatherData.e ?: dailyForecastData.e) {
                    is HttpException -> {
                        if (error.code() == 503) {
                            ErrorScreen(
                                id = R.drawable.ic_sorry,
                                mainMessage = R.string.free_app,
                                secondMessage = R.string.please_try_again_later
                            )
                        }
                    }
                    else -> {
                        ErrorScreen(
                            id = R.drawable.ic_sad,
                            mainMessage = R.string.oops_something_got_lost_on_internet,
                            secondMessage = R.string.please_try_again_later
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun MainContent(
    title: String,
    accuWeatherCity: AccuWeatherCity,
    currentWeather: CurrentWeather,
    dailyForecast: List<DailyForecast>,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    navController: NavController,
    isMetric: Boolean
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    var shouldShowSnackBar by remember { mutableStateOf(false) }
    val snackbarMessage = stringResource(R.string.added_to_favorites)
    val snackbarActionLabel = stringResource(R.string.undo)

    if (shouldShowSnackBar) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = snackbarMessage,
                actionLabel = snackbarActionLabel
            )
            when (result) {
                SnackbarResult.Dismissed -> {
                    shouldShowSnackBar = false
                }
                SnackbarResult.ActionPerformed -> {
                    shouldShowSnackBar = false
                    favoriteViewModel.deleteFavorite(accuWeatherCity = accuWeatherCity)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            WeatherAppBar(
                title = title,
                accuWeatherCity = accuWeatherCity,
                navController = navController,
                elevation = 5.dp,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                },
                onFavoritesClicked = {
                    shouldShowSnackBar = true
                }
            )
        }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            TopContent(
                currentWeather = currentWeather,
                isMetric = isMetric
            )
            Spacer(modifier = Modifier.weight(1f))
            BottomContent(
                dailyForecast = dailyForecast
            )
        }
    }
}

@Composable
fun TopContent(currentWeather: CurrentWeather, isMetric: Boolean) {

    ConstraintLayout(
        Modifier
            .padding(4.dp),
    ) {
        val (dateText, cardContainer) = createRefs()
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .constrainAs(dateText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = formatDate(currentWeather.epochTime),
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        CurrentWeatherCard(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .constrainAs(cardContainer) {
                    top.linkTo(dateText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            currentWeather = currentWeather,
            isMetric = isMetric
        )
    }
}

@Composable
private fun CurrentWeatherCard(
    modifier: Modifier,
    currentWeather: CurrentWeather,
    isMetric: Boolean
) {
    val realFeelTemperature: Double =
        if (isMetric) currentWeather.realFeelTemperature.metric.value else currentWeather.realFeelTemperature.imperial.value

    val temperature = currentWeather.temperature
    val temperaturePair: Pair<String, Double> = if (isMetric) Pair(
        temperature.metric.unit,
        temperature.metric.value
    ) else Pair(
        temperature.imperial.unit,
        temperature.imperial.value
    )

    val speed = currentWeather.wind.speed
    val speedPair: Pair<String, Double> = if (isMetric) Pair(
        speed.metric.unit,
        speed.metric.value
    ) else Pair(
        speed.imperial.unit,
        speed.imperial.value
    )

    val pressure = currentWeather.pressure
    val pressurePair: Pair<String, Double> = if (isMetric) Pair(
        pressure.metric.unit,
        pressure.metric.value
    ) else Pair(
        pressure.imperial.unit,
        pressure.imperial.value
    )

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        border = BorderStroke(2.dp, MaterialTheme.colors.onSurface)
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(10.dp)
        ) {
            val (weatherIcon, temperatureText, degreeText, unitText, weatherText, realFeelText,
                humidityIcon, humidityText, pressureIcon, pressureText, windIcon, windText,
                uvIcon, uvText) = createRefs()

            LoadImageFromResources(
                iconId = getCurrentConditionsIcon(
                    id = currentWeather.weatherIcon,
                    context = LocalContext.current
                ),
                modifier = Modifier
                    .fillMaxSize(0.6f)
                    .padding(horizontal = 10.dp)
                    .constrainAs(weatherIcon) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            Text(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .constrainAs(temperatureText) {
                        top.linkTo(weatherIcon.top, margin = 40.dp)
                        start.linkTo(weatherIcon.end)
                    },
                text = formatDecimals(temperaturePair.second),
                style = MaterialTheme.typography.h4,
                fontSize = 50.sp,
                textAlign = TextAlign.Start
            )

            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .constrainAs(degreeText) {
                        top.linkTo(temperatureText.top)
                        start.linkTo(temperatureText.end)
                    },
                text = "°",
                style = MaterialTheme.typography.h4,
                fontSize = 40.sp,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .padding(start = 5.dp)
                    .constrainAs(unitText) {
                        start.linkTo(temperatureText.end)
                        baseline.linkTo(temperatureText.baseline)
                    },
                text = temperaturePair.first,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                style = MaterialTheme.typography.h4,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )

            CurrentWeatherRow(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .constrainAs(realFeelText) {
                        top.linkTo(unitText.bottom)
                        start.linkTo(temperatureText.start, margin = 30.dp)
                        end.linkTo(unitText.end)

                    },
                leftContent = stringResource(R.string.real_feel),
                fontSizeLeftContent = 14.sp,
                rightContent = "${formatDecimals(realFeelTemperature)}°",
                fontSizeRightContent = 16.sp
            )

            Text(
                modifier = Modifier
                    .padding(bottom = 5.dp, start = 15.dp)
                    .constrainAs(weatherText) {
                        top.linkTo(realFeelText.bottom)
                        start.linkTo(realFeelText.start)
                        end.linkTo(realFeelText.end)
                        width = Dimension.fillToConstraints
                    },
                text = currentWeather.weatherText,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )

            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .constrainAs(humidityIcon) {
                        top.linkTo(weatherIcon.bottom, margin = 30.dp)
                        start.linkTo(parent.start, margin = 30.dp)
                    },
                painter = painterResource(
                    id = R.drawable.ic_humidity
                ),
                contentDescription = "humidity icon"
            )
            Text(
                modifier = Modifier.constrainAs(humidityText) {
                    top.linkTo(humidityIcon.top)
                    start.linkTo(humidityIcon.end, margin = 5.dp)
                    bottom.linkTo(humidityIcon.bottom)
                },
                text = "${currentWeather.relativeHumidity}%",
                style = MaterialTheme.typography.subtitle1
            )

            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .constrainAs(pressureIcon) {
                        top.linkTo(weatherIcon.bottom, margin = 30.dp)
                        end.linkTo(pressureText.start, margin = 5.dp)
                    },
                painter = painterResource(
                    id = R.drawable.ic_pressure
                ),
                contentDescription = "pressure icon"
            )
            Text(
                modifier = Modifier
                    .constrainAs(pressureText) {
                        top.linkTo(pressureIcon.top)
                        end.linkTo(parent.end, margin = 30.dp)
                        bottom.linkTo(pressureIcon.bottom)
                    },
                text = "${pressurePair.second} ${pressurePair.first}",
                style = MaterialTheme.typography.subtitle1
            )

            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .padding(3.dp)
                    .constrainAs(windIcon) {
                        top.linkTo(pressureIcon.bottom, margin = 20.dp)
                        start.linkTo(pressureIcon.start)
                        end.linkTo(windText.start, margin = 5.dp)
                    },
                painter = painterResource(
                    id = R.drawable.ic_wind
                ),
                contentDescription = "wind icon"
            )
            Text(
                modifier = Modifier.constrainAs(windText) {
                    top.linkTo(windIcon.top)
                    start.linkTo(windIcon.end, margin = 5.dp)
                    bottom.linkTo(windIcon.bottom)
                },
                text = "${formatDecimals(speedPair.second)} ${speedPair.first}",
                style = MaterialTheme.typography.subtitle1
            )

            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .constrainAs(uvIcon) {
                        top.linkTo(humidityIcon.bottom, margin = 20.dp)
                        start.linkTo(humidityIcon.start)
                        end.linkTo(uvText.start, margin = 5.dp)
                    },
                painter = painterResource(
                    id = R.drawable.ic_uv_index
                ),
                contentDescription = "uv index icon"
            )

            CurrentWeatherRow(
                modifier = Modifier.constrainAs(uvText) {
                    top.linkTo(uvIcon.top)
                    start.linkTo(uvIcon.end, margin = 5.dp)
                    bottom.linkTo(uvIcon.bottom)
                },
                leftContent = "${currentWeather.uvIndex} ",
                fontSizeLeftContent = 14.sp,
                rightContent = "(${currentWeather.uvIndexText})",
                fontSizeRightContent = 16.sp
            )
        }
    }
}

@Composable
fun BottomContent(dailyForecast: List<DailyForecast>) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp),
        text = stringResource(R.string.this_week),
        style = MaterialTheme.typography.h5,
        textAlign = TextAlign.Center
    )
    Divider(thickness = 2.dp, color = MaterialTheme.colors.onSurface)
    LazyRow(
        modifier = Modifier.padding(vertical = 10.dp),
        contentPadding = PaddingValues(start = 10.dp)
    ) {
        items(items = dailyForecast) { item: DailyForecast ->
            WeatherDetailRow(item)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTopContent(
    @PreviewParameter(SampleCurrentWeatherDataProvider::class) currentWeather: CurrentWeather,
    isMetric: Boolean = true
) {
    TopContent(currentWeather = currentWeather, isMetric = isMetric)
}


