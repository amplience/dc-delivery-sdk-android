package com.amplience.sampleapp.elements

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.amplience.sampleapp.ui.theme.Typography

@Composable
fun Headline(headline: String, modifier: Modifier = Modifier) {
    Text(text = headline, style = Typography.h5, modifier = modifier)
}
