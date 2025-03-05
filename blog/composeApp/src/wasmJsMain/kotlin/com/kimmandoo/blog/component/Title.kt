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
fun Title() {
    val messages = listOf(
        "Hello.", "안녕하세요.", "Hola.", "Bonjour.", "こんにちは。", "Guten Tag.", "Ciao."
    )

    var currentIndex by remember { mutableIntStateOf(0) }
    var visible by remember { mutableStateOf(true) }

    // alpha 애니메이션 설정 (페이드 인 -> 페이드 아웃)
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(2000), // 1.5초 동안 페이드 인/아웃
        label = "FadeAnimation"
    )

    LaunchedEffect(visible) {
        if (!visible) {
            delay(2000) // 텍스트 변경 전 약간의 대기 시간 (자연스러운 전환)
            currentIndex = (currentIndex + 1) % messages.size
            visible = true // 다시 페이드 인
        } else {
            delay(2000) // 페이드 인 지속 시간만큼 대기
            visible = false // 페이드 아웃 시작
        }
    }

    Text(
        modifier = Modifier.padding(vertical = 20.dp),
        text = messages[currentIndex],
        color = Color.Black.copy(alpha = alpha),
        style = MaterialTheme.typography.h1
    )
}
