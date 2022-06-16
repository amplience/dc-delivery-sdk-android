package com.amplience.sampleapp.elements

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amplience.sampleapp.MainViewModel
import com.amplience.sampleapp.model.Screen
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

@OptIn(ExperimentalSnapperApi::class)
@Composable
fun ScreenUI(
    screen: Screen,
    viewModel: MainViewModel,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        viewModel.appBarTitle = screen.name
    }
    when (screen) {
        is Screen.HomeScreen -> {
            Home(viewModel.screens.filter {
                // Don't add home screen to the button list
                it !is Screen.HomeScreen
            }) {
                navController.navigate(it.id)
            }
        }
        is Screen.BannerScreen -> {
            Banner(
                banner = screen.banner,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2.33f)
            )
        }
        is Screen.SlidesScreen -> {
            LazyColumn {
                items(screen.slides) {
                    SlideUI(
                        slide = it,
                        modifier = Modifier.aspectRatio(1.5f)
                    )
                }
            }
        }
        is Screen.MultiContentExampleScreen -> {
            val lazyListState = rememberLazyListState()
            LazyColumn {
                item {
                    Banner(
                        banner = screen.banner,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2.33f)
                    )
                }
                item {
                    LazyRow(
                        state = lazyListState,
                        flingBehavior = rememberSnapperFlingBehavior(lazyListState),
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.5f)
                    ) {
                        items(screen.slides) {
                            ImageUI(
                                image = it.image,
                                altText = null,
                                modifier = Modifier.aspectRatio(1.5f)
                            )
                        }
                    }
                }
                item {
                    Text(text = screen.text, modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}
