package com.amplience.sampleapp.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.amplience.sampleapp.ui.theme.Purple500

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize().fillMaxHeight().background(Purple500)
    ) {
        Text(text = "Loading...", modifier = Modifier.align(Alignment.Center), color = Color.White)
    }
}
