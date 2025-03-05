package com.kimmandoo.blog.markdown

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun H1(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h1.copy(fontSize = 24.sp),
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
fun H2(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h1.copy(fontSize = 20.sp),
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
fun H3(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h1.copy(fontSize = 18.sp),
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
fun H4(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h1.copy(fontSize = 16.sp),
        modifier = Modifier.padding(vertical = 4.dp)
    )
}