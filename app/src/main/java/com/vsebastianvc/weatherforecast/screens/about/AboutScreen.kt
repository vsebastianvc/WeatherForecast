package com.vsebastianvc.weatherforecast.screens.about

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.vsebastianvc.weatherforecast.R
import com.vsebastianvc.weatherforecast.widgets.LoadImageFromResources
import com.vsebastianvc.weatherforecast.widgets.WeatherAppBar

val listOfTechnologies: List<Pair<String, Int>> = listOf(
    Pair("Kotlin", R.drawable.ic_kotlin),
    Pair("JetPack Compose", R.drawable.ic_compose),
    Pair("AccuWeather", R.drawable.ic_accuweather),
    Pair("MVVM", R.drawable.ic_architecture),
    Pair("Navigation Graph", R.drawable.ic_navigation),
    Pair("Coroutines", R.drawable.ic_coroutines),
    Pair("Flow", R.drawable.ic_flow),
    Pair("Room", R.drawable.ic_database),
    Pair("Shared Preferences", R.drawable.ic_cache),
    Pair("Dagger Hilt", R.drawable.ic_dagger),
    Pair("Retrofit", R.drawable.ic_retrofit),
    Pair("Support Dark Mode", R.drawable.ic_dark_theme),
)

@Composable
fun AboutScreen(navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = stringResource(id = R.string.about),
            icon = Icons.Default.ArrowBack,
            isMainScreen = false,
            navController = navController
        ) {
            navController.popBackStack()
        }
    }) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                text = stringResource(R.string.technologies_used_on_this_app),
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )
            Divider(
                modifier = Modifier.padding(bottom = 10.dp, end = 5.dp),
                thickness = 2.dp,
                startIndent = 5.dp,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
            AboutContent()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun AboutContent(technologies: List<Pair<String, Int>> = listOfTechnologies) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(3)
    ) {
        items(technologies.size) { index ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AboutContentRow(
                    iconId = technologies[index].second,
                    description = technologies[index].first
                )
            }
        }
    }
}

@Composable
fun AboutContentRow(iconId: Int, description: String) {
    Card(
        modifier = Modifier
            .size(150.dp)
            .padding(5.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        border = BorderStroke(2.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.6f))
    ) {
        ConstraintLayout(
            Modifier
                .padding(4.dp),
        ) {
            val (logoPainterImage, descriptionText) = createRefs()
            LoadImageFromResources(
                iconId = iconId,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxSize(0.6f)
                    .constrainAs(logoPainterImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(descriptionText.top)
                    }
            )
            Text(
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 10.dp)
                    .constrainAs(descriptionText) {
                        top.linkTo(logoPainterImage.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                text = description,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
            createVerticalChain(logoPainterImage, descriptionText, chainStyle = ChainStyle.Packed)
        }
    }
}
