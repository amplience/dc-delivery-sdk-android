package com.amplience.sampleapp.elements

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.amplience.sampleapp.ui.theme.Typography

@Composable
fun LightSubheading(subheading: String, modifier: Modifier = Modifier) {
    Text(text = subheading, style = Typography.subtitle1, modifier = modifier, color = Color.White)
}

@Composable
fun Subheading(subheading: String, modifier: Modifier = Modifier) {
    Text(text = subheading, style = Typography.subtitle1, modifier = modifier)
}
