package com.amplience.sampleapp.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.amplience.sampleapp.model.Slide

@Composable
fun SlideUI(slide: Slide, modifier: Modifier = Modifier) {
    Box(modifier) {
        ImageUI(image = slide.imageItem.image, slide.imageItem.altText)
        Column {
            Headline(headline = slide.headline)
            Subheading(subheading = slide.subheading)
        }
    }
}
