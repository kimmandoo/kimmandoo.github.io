package com.kimmandoo.blog.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.reflect.KProperty

@Composable
fun Title(){
    val messages =
        listOf(
            "Hello.", "안녕하세요.", "Hola.", "Bonjour.", "こんにちは。", "Guten Tag.", "Ciao."
        )

    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000L)
            currentIndex = (currentIndex + 1) % messages.size
        }
    }

    val transition = rememberInfiniteTransition(label = "")
    val alpha by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec =
            InfiniteRepeatableSpec(
                animation = tween(1500), // tween 이니까 한 텍스트에 6초
                RepeatMode.Reverse,
                StartOffset(0, StartOffsetType.FastForward),
            ),
        label = "FloatAnimation",
    )
    Text(
        modifier = Modifier.padding(vertical = 20.dp),
        text = messages[currentIndex],
        color = Color.Black.copy(alpha = alpha),
        style = MaterialTheme.typography.h2
    )
    Spacer(modifier = Modifier.height(1.dp).background(Color.Gray))
}