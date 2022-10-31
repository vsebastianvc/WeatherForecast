package com.vsebastianvc.weatherforecast.screens.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vsebastianvc.weatherforecast.R
import com.vsebastianvc.weatherforecast.data.DataOrException
import com.vsebastianvc.weatherforecast.model.autocomplete.AccuWeatherCity
import com.vsebastianvc.weatherforecast.navigation.WeatherScreens
import com.vsebastianvc.weatherforecast.utils.getLocation
import com.vsebastianvc.weatherforecast.utils.isInternetAvailable
import com.vsebastianvc.weatherforecast.utils.toJson
import com.vsebastianvc.weatherforecast.widgets.ErrorScreen
import com.vsebastianvc.weatherforecast.widgets.Loading
import com.vsebastianvc.weatherforecast.widgets.WeatherAppBar
import retrofit2.HttpException

@Composable
fun SearchScreen(navController: NavController, searchViewModel: SearchViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = stringResource(R.string.search),
            navController = navController,
            icon = Icons.Default.ArrowBack,
            isMainScreen = false
        ) {
            navController.popBackStack()
        }
    }) {

        var searchCity by remember {
            mutableStateOf("")
        }

        Surface {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar { city ->
                    searchCity = city
                }

                if (searchCity != "") {
                    if (!isInternetAvailable(LocalContext.current)) {
                        ErrorScreen(
                            id = R.drawable.ic_no_wifi,
                            mainMessage = R.string.no_connection,
                            secondMessage = R.string.please_try_check_your_connection
                        )
                    } else {
                        val cityData =
                            produceState<DataOrException<List<AccuWeatherCity>, Boolean, Exception>>(
                                initialValue = DataOrException(loading = true)
                            ) {
                                value = searchViewModel.getLocationFromText(city = searchCity)
                            }.value

                        if (cityData.loading == true) {
                            Loading()
                        } else if (cityData.data != null) {
                            Divider(
                                modifier = Modifier.padding(
                                    start = 15.dp,
                                    end = 15.dp,
                                    top = 15.dp
                                ),
                                thickness = 3.dp,
                                color = MaterialTheme.colors.onSurface
                            )
                            ListOfCities(list = cityData.data!!, navController = navController)
                        } else if (cityData.e != null) {
                            when (val error = cityData.e) {
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
                } else {
                    Box {}
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    onSearch: (String) -> Unit = {}
) {
    val searchQueryState = rememberSaveable {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQueryState.value) {
        searchQueryState.value.trim().isNotEmpty()
    }

    Column {
        CommonTextField(
            valueState = searchQueryState,
            label = stringResource(R.string.search),
            placeholder = stringResource(R.string.e_g_toronto),
            onAction = KeyboardActions {
                if (!valid) {
                    return@KeyboardActions
                } else {
                    onSearch(searchQueryState.value.trim())
                    searchQueryState.value = ""
                    keyboardController?.hide()
                }
            }
        )
        // TODO V2 add Current Location
        /*Row(modifier = Modifier.padding(top = 15.dp)) {
            WeatherStateImage(
                iconId = R.drawable.ic_location,
                modifier = Modifier
                    .padding(start = 15.dp, top = 10.dp, end = 20.dp, bottom = 10.dp)
                    .size(30.dp)
            )
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "Current Location",
                style = MaterialTheme.typography.subtitle1
            )
        }*/
    }
}

@Composable
fun CommonTextField(
    valueState: MutableState<String>,
    label: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Search,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    val isDarkMode = isSystemInDarkTheme()
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = if (isDarkMode) Color.White else Color.Blue,
            cursorColor = MaterialTheme.colors.onSurface
        ),
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    )
}

@Composable
fun ListOfCities(
    list: List<AccuWeatherCity>,
    navController: NavController
) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn {
                items(items = list) {
                    CityRow(
                        it,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun CityRow(
    accuWeatherCity: AccuWeatherCity,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(top = 5.dp, bottom = 5.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable { navController.navigate(WeatherScreens.MainScreen.name + "/${accuWeatherCity.toJson()}") },
        shape = MaterialTheme.shapes.small.copy(CornerSize(5.dp)),
        elevation = 5.dp,
        border = BorderStroke(2.dp, MaterialTheme.colors.onSurface)
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
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
        }
    }
}