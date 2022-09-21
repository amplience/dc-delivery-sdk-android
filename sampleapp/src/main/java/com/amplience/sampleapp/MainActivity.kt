package com.amplience.sampleapp

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amplience.sampleapp.elements.LoadingView
import com.amplience.sampleapp.elements.ScreenUI
import com.amplience.sampleapp.model.Screen
import com.amplience.sampleapp.ui.theme.SampleAppTheme
import com.amplience.sdk.delivery.android.ContentClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras
        val stagingEnvironmentUrl = extras?.getString("stagingEnvironment")
        ContentClient.initialise(applicationContext, "ampproduct-doc", ContentClient.Configuration(stagingEnvironmentUrl = stagingEnvironmentUrl))

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

        checkNetworkSpeed()
        viewModel.applicationContext = applicationContext
        viewModel.stagingEnvironmentUrl = stagingEnvironmentUrl
        viewModel.getExamples()
    }

    private fun checkNetworkSpeed() {
        val cm = this.getSystemService(CONNECTIVITY_SERVICE) as? ConnectivityManager
        val nc = cm?.getNetworkCapabilities(cm.activeNetwork)
        if (nc != null) {
            val downSpeed = nc.linkDownstreamBandwidthKbps
            val upSpeed = nc.linkUpstreamBandwidthKbps

            /*  Network speeds (approx)
                2G GSM ~14.4 Kbps
                G GPRS ~26.8 Kbps
                E EDGE ~108.8 Kbps
                3G UMTS ~128 Kbps
                H HSPA ~3.6 Mbps
                H+ HSPA+ ~14.4 Mbps-23.0 Mbps
                4G LTE ~50 Mbps
                4G LTE-A ~500 Mbps
            */
            Timber.d("Down $downSpeed")
            Timber.d("Up $upSpeed")

            if (downSpeed < 128) {
                viewModel.lowBandwidth = true
            }
        }

        lifecycleScope.launch {
            // Recheck every minute while activity is running
            delay(60000)
            checkNetworkSpeed()
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
                    if (screen is Screen.BlogPostMenuScreen) {
                        screen.posts.forEach { blogScreen ->
                            composable(blogScreen.id) {
                                ScreenUI(
                                    screen = blogScreen,
                                    viewModel = viewModel,
                                    navController = navController
                                )
                            }
                        }
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
