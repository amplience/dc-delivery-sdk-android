package com.amplience.sampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amplience.sampleapp.elements.LoadingView
import com.amplience.sampleapp.elements.ScreenUI
import com.amplience.sampleapp.ui.theme.SampleAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Main(viewModel)
                }
            }
        }
    }
}

@Composable
fun Main(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val title = viewModel.appBarTitle

    if (viewModel.screens.isEmpty()) {
        LoadingView()
    } else {
        Scaffold(
            topBar = { TopBar(title, navController) }
        ) { innerPadding ->
            NavHost(
                navController,
                startDestination = "home",
                Modifier.padding(innerPadding)
            ) {
                viewModel.screens.forEach { screen ->
                    composable(screen.id) {
                        ScreenUI(
                            screen = screen,
                            viewModel = viewModel,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    title: String,
    navController: NavHostController
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = if (navController.currentDestination?.route != "home") {
            {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        } else null
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SampleAppTheme {
        Main(MainViewModel())
    }
}
