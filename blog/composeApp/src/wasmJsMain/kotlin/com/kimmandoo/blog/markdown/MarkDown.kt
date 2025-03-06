package com.kimmandoo.blog.markdown

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MarkdownText(markdown: String, modifier: Modifier = Modifier) {
    val lines = markdown.lines()
    Column(modifier = modifier.padding(8.dp)) {
        lines.forEach { line ->
            when {
                line.startsWith("# ") -> {
                    H1(line.removePrefix("# "))
                }
                line.startsWith("## ") -> {
                    H2(line.removePrefix("## "))
                }
                line.startsWith("### ") -> {
                    H3(line.removePrefix("### "))
                }
                line.startsWith("#### ") -> {
                    H4(line.removePrefix("#### "))
                }
                line.startsWith("- # ") -> {
                    ListHeader(line.removePrefix("- # "), fontSize = 24.sp)
                }
                line.startsWith("- ## ") -> {
                    ListHeader(line.removePrefix("- ## "), fontSize = 20.sp)
                }
                line.startsWith("- ### ") -> {
                    ListHeader(line.removePrefix("- ### "), fontSize = 18.sp)
                }
                line.startsWith("- #### ") -> {
                    ListHeader(line.removePrefix("- #### "), fontSize = 16.sp)
                }
                line.startsWith("- ") -> {
                    ListItem(line.removePrefix("- "))
                }
                line.startsWith("> ") -> {
                    Quote(line.removePrefix("> "))
                }
                line == "---" -> {
                    Divider()
                }
                else -> {
                    Text(text = line, style = MaterialTheme.typography.body1, modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}