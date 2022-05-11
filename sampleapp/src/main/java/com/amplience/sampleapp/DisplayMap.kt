package com.amplience.sampleapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amplience.ampliencesdk.models.PropertyEntry
import com.amplience.ampliencesdk.models.PropertyName
import com.amplience.ampliencesdk.models.Schema
import com.amplience.sampleapp.ui.theme.SampleAppTheme
import com.amplience.sampleapp.ui.theme.Typography
import timber.log.Timber

@Composable
fun DisplayUI(map: Map<String, Any>) {
    val uriHandler = LocalUriHandler.current
    Timber.d("Loading composable")
    Column(modifier = Modifier.padding(16.dp)) {
        map.entries.forEach {
            if (it.key == Schema.Property.Type.String.name) {
                Timber.d("Property: $it")
                when (it.key) {
                    PropertyName.Headline.apiName -> {
                        Text(text = it.value.toString(), style = Typography.h3)
                    }
                    PropertyName.Strapline.apiName -> {
                        Text(text = it.value.toString(), style = Typography.subtitle1)
                    }
                    else -> {}
                }
            }
        }

        /*val ctaText = propertyEntries.find { it.property.name == PropertyName.CTAText }
        val ctaUrl = propertyEntries.find { it.property.name == PropertyName.CTAUrl }
        if (ctaText != null && ctaUrl != null) {
            TextButton(
                onClick = { uriHandler.openUri(ctaUrl.stringValue) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = ctaText.stringValue)
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
            }
        }*/
    }
}

@Preview
@Composable
fun PreviewDisplayUI() {
    SampleAppTheme {
        // DisplayUI()
    }
}
