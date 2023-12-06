package com.vsebastianvc.weatherforecast.screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vsebastianvc.weatherforecast.R
import com.vsebastianvc.weatherforecast.utils.Constants
import com.vsebastianvc.weatherforecast.widgets.WeatherAppBar


@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val measurementUnits = listOf(Constants.IMPERIAL, Constants.METRIC)

    val defaultChoice = settingsViewModel.getUnit()

    var choiceState by remember {
        mutableStateOf(defaultChoice)
    }

    Scaffold(
        topBar = {
            WeatherAppBar(
                title = stringResource(R.string.settings),
                icon = Icons.Default.ArrowBack,
                isMainScreen = false,
                navController = navController
            ) { navController.popBackStack() }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 10.dp, start = 25.dp, end = 25.dp)
            ) {
                Text(
                    text = stringResource(R.string.units),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6
                )
                Surface(
                    shape = MaterialTheme.shapes.large,
                    elevation = 4.dp,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(vertical = 10.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colors.onSurface)
                ) {
                    Row(
                        modifier = Modifier
                            .clip(shape = MaterialTheme.shapes.large)
                            .background(Color.Gray),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        measurementUnits.forEach { text ->
                            val unitText = text.split(" ")
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = MaterialTheme.typography.body1.toSpanStyle().copy(
                                            fontWeight = FontWeight.Bold,
                                        )
                                    ) {
                                        append(unitText[0] + "\n")
                                    }

                                    withStyle(
                                        style = MaterialTheme.typography.body2.toSpanStyle().copy(
                                            fontWeight = FontWeight.Normal,
                                        )
                                    ) {
                                        append("${unitText[1]} ${unitText[2]}")
                                    }
                                },
                                color = Color.White,
                                modifier = Modifier
                                    .clip(shape = MaterialTheme.shapes.large)
                                    .clickable {
                                        choiceState = text
                                        settingsViewModel.setUnit(unit = text)
                                    }
                                    .background(
                                        if (text == choiceState) {
                                            Color.DarkGray
                                        } else {
                                            Color.Gray
                                        }
                                    )
                                    .padding(
                                        vertical = 12.dp,
                                        horizontal = 16.dp,
                                    )
                                    .widthIn(min = 150.dp, max = 180.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    )
}
