package com.vsebastianvc.weatherforecast.widgets

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vsebastianvc.weatherforecast.R
import com.vsebastianvc.weatherforecast.model.dailyforecast.DailyForecast
import com.vsebastianvc.weatherforecast.ui.theme.Blue200
import com.vsebastianvc.weatherforecast.ui.theme.Blue700
import com.vsebastianvc.weatherforecast.ui.theme.Red200
import com.vsebastianvc.weatherforecast.ui.theme.Red700
import com.vsebastianvc.weatherforecast.utils.*

@Composable
fun WeatherDetailRow(dailyForecast: DailyForecast) {
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(0.3f),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(2.dp, MaterialTheme.colors.onSurface)
    ) {
        val isDarkMode = isSystemInDarkTheme()
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            LoadImageFromResources(
                iconId = getCurrentConditionsIcon(
                    id = dailyForecast.getDailyForecastIcon(date = dailyForecast.date),
                    context = LocalContext.current
                ),
                modifier = Modifier
                    .padding(start = 5.dp, end = 0.dp, top = 5.dp, bottom = 5.dp)
            )
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.padding(top = 0.dp, bottom = 5.dp),
                    text = formatDateDay(dailyForecast.epochDate).split(",").first(),
                    style = MaterialTheme.typography.h4,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
                Surface(
                    modifier = Modifier.padding(top = 0.dp, bottom = 5.dp),
                    shape = RectangleShape,
                    color = if (isDarkMode) MaterialTheme.colors.onSurface.copy(alpha = 0.5f) else
                        MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = dailyForecast.getDailyForecastIconPhrase(date = dailyForecast.date),
                        modifier = Modifier.padding(5.dp),
                        style = MaterialTheme.typography.overline
                    )
                }

                Text(text = buildAnnotatedString {
                    withStyle(
                        style = MaterialTheme.typography.body1.toSpanStyle().copy(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("Minimum ")
                    }

                    withStyle(
                        style = MaterialTheme.typography.body1.toSpanStyle().copy(
                            color = if (isDarkMode) Blue200 else Blue700,
                            fontWeight = FontWeight.ExtraBold
                        )
                    ) {
                        append("${formatDecimals(dailyForecast.temperature.minimum.value)}°")
                    }
                })
                Text(text = buildAnnotatedString {
                    withStyle(
                        style = MaterialTheme.typography.body1.toSpanStyle().copy(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("Maximum")
                    }

                    withStyle(
                        style = MaterialTheme.typography.body1.toSpanStyle().copy(
                            color = if (isDarkMode) Red200 else Red700,
                            fontWeight = FontWeight.ExtraBold
                        )
                    ) {
                        append("${formatDecimals(dailyForecast.temperature.maximum.value)}°")
                    }
                })
            }
        }
    }
}

@Composable
fun LoadImageFromResources(iconId: Int, modifier: Modifier) {
    Image(
        painter = painterResource(iconId),
        contentDescription = "icon image",
        modifier = modifier,
    )
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        CircularProgressIndicator()
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun CurrentWeatherRow(
    modifier: Modifier = Modifier,
    leftContent: String,
    fontSizeLeftContent: TextUnit,
    rightContent: String,
    fontSizeRightContent: TextUnit
) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = MaterialTheme.typography.body1.toSpanStyle().copy(
                    color = MaterialTheme.colors.onSurface,
                    fontSize = fontSizeLeftContent,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(leftContent)
            }

            withStyle(
                style = MaterialTheme.typography.body1.toSpanStyle().copy(
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                    fontSize = fontSizeRightContent,
                    fontWeight = FontWeight.ExtraBold
                )
            ) {
                append(rightContent)
            }
        },
        modifier = modifier
    )
}

@Composable
fun ErrorScreen(id: Int, mainMessage: Int, secondMessage: Int) {
    val activity = (LocalContext.current as? Activity)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = id),
            contentDescription = "sorry icon",
            modifier = Modifier.fillMaxSize(0.6f)
        )
        Text(
            text = stringResource(id = mainMessage),
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Justify,
            fontSize = 24.sp
        )
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = stringResource(id = secondMessage),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        OutlinedButton(
            onClick = { activity?.finish() },
            modifier = Modifier.padding(10.dp),
            border = BorderStroke(2.dp, MaterialTheme.colors.onSurface),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.onSurface)
        ) {
            Text(
                text = stringResource(id = R.string.exit_the_app),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )
        }
    }
}