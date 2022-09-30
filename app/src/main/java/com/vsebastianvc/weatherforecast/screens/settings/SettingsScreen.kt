package com.vsebastianvc.weatherforecast.screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Scaffold(topBar = {
        WeatherAppBar(
            title = stringResource(R.string.settings),
            icon = Icons.Default.ArrowBack,
            isMainScreen = false,
            navController = navController
        ) { navController.popBackStack() }
    }) {
        Column(
            modifier = Modifier
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
                shape = RoundedCornerShape(24.dp),
                elevation = 4.dp,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 10.dp),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Row(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(24.dp))
                        .background(Color.Gray),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    measurementUnits.forEach { text ->
                        val unitText = text.split(" ")
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                ) {
                                    append(unitText[0] + "\n")
                                }

                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 14.sp
                                    )
                                ) {
                                    append("${unitText[1]} ${unitText[2]}")
                                }
                            },
                            color = Color.White,
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(24.dp))
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
}
