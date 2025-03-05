package com.kimmandoo.blog.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.kimmandoo.blog.readFileToString

@Composable
fun BlogScreen() {
    Box(modifier = Modifier.fillMaxSize()){
        SelectionContainer{
            Text(
                text = "this becomes blog page",
                style = MaterialTheme.typography.body1
            )
        }
    }
}