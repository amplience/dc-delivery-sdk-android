package com.amplience.sampleapp.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amplience.sampleapp.model.Screen

@Composable
fun Home(
    screens: List<Screen>,
    onScreenClick: (Screen) -> Unit
) {
    LazyColumn {
        item {
            Text(
                text = "Click through to see any of the sample examples...",
                Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        items(screens) { screen ->
            Button(
                onClick = { onScreenClick(screen) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(text = screen.name)
            }
        }
    }
}
