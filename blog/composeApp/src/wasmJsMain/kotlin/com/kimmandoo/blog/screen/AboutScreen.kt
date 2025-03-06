package com.kimmandoo.blog.screen

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kimmandoo.blog.markdown.MarkdownText
import com.kimmandoo.blog.readFileToString

@Composable
fun AboutScreen() {
    var markdownContent by remember { mutableStateOf("") }

    LaunchedEffect(Unit){
        markdownContent = readFileToString("/md/aboutme.md")
        println(markdownContent)
    }

    Box(modifier = Modifier.fillMaxSize()){
        SelectionContainer {
            MarkdownText(markdown = markdownContent.trimIndent())
//            Text(
//                text = markdownContent,
//                style = MaterialTheme.typography.body1
//            )
        }
    }
}