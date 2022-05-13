package com.amplience.sampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.amplience.sampleapp.elements.Banner
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
                    /*LazyColumn {
                        items(viewModel.slides) {
                            Slide(slide = it, modifier = Modifier.aspectRatio(1.5f))
                        }
                    }*/

                    Column {
                        viewModel.banner?.let {
                            Banner(
                                banner = it,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(2.33f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SampleAppTheme {
        Greeting("Android")
    }
}