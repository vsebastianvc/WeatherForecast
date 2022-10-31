package com.vsebastianvc.weatherforecast.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vsebastianvc.weatherforecast.R
import com.vsebastianvc.weatherforecast.navigation.WeatherScreens
import com.vsebastianvc.weatherforecast.utils.toJson
import kotlinx.coroutines.delay

@Composable
fun WeatherSplashScreen(
    navController: NavController,
    weatherSplashViewModel: WeatherSplashViewModel = hiltViewModel()
) {
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true, block = {
        scale.animateTo(targetValue = 0.9f, tween(
            durationMillis = 800,
            easing = {
                OvershootInterpolator(8f).getInterpolation(it)
            }
        ))
        delay(2000L)
        navController.navigate(
            WeatherScreens.MainScreen.name + "/${
                weatherSplashViewModel.getAccuWeatherCity().toJson()
            }"
        )
    })
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(330.dp)
            .scale(scale.value),
        shape = CircleShape,
        color = MaterialTheme.colors.surface,
        border = BorderStroke(2.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.6f))
    ) {
        Column(
            modifier = Modifier.padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_splash),
                contentDescription = "sunny icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize(0.5f)
                    .padding(bottom = 10.dp)
            )
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.4f)
            )
        }
    }
}