package com.kimmandoo.blog.markdown

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListHeader(text: String, fontSize: TextUnit) {
    Row {
        Text("• ", style = MaterialTheme.typography.h1.copy(fontSize = 16.sp))
        Text(
            text = text,
            style = MaterialTheme.typography.h1.copy(fontSize = fontSize),
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

@Composable
fun ListItem(text: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text("• ", style = MaterialTheme.typography.h1.copy(fontSize = 16.sp))
        Text(text, style = MaterialTheme.typography.body1)
    }
}