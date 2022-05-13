package com.amplience.sampleapp.elements

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.amplience.sampleapp.ui.theme.Typography

@Composable
fun LightHeadline(headline: String, modifier: Modifier = Modifier) {
    Text(text = headline, style = Typography.h5, modifier = modifier, color = Color.White)
}

@Composable
fun Headline(headline: String, modifier: Modifier = Modifier) {
    Text(text = headline, style = Typography.h5, modifier = modifier)
}
