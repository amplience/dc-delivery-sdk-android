package com.amplience.sampleapp.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.amplience.sampleapp.model.BlogPost

@Composable
fun BlogPostUI(blogPost: BlogPost) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Text(text = blogPost.title, style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(blogPost.authors) { author ->
            Row {
                ImageUI(
                    image = author.avatar.image,
                    altText = author.avatar.altText,
                    modifier = Modifier
                        .height(48.dp)
                        .width(48.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(text = author.name)
                    Text(
                        text = blogPost.date,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Text(text = blogPost.description, fontStyle = FontStyle.Italic)
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(blogPost.content) { content ->
            Text(text = content.text)
        }
    }
}
