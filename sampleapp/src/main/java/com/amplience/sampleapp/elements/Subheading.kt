package com.amplience.sampleapp.elements

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.amplience.sampleapp.ui.theme.Typography

@Composable
fun Subheading(subheading: String, modifier: Modifier = Modifier) {
    Text(text = subheading, style = Typography.subtitle1, modifier = modifier)
}
