package io.github.kimmandoo.screen.main.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kimmandoo.ui.adaptive.rememberDeviceState
import io.github.kimmandoo.ui.adaptive.titleFontSize
import kotlinx.coroutines.delay

@Composable
fun GreetingAnimation(contentAlignment: Alignment = Alignment.Center) {
    val deviceState = rememberDeviceState()

    val greetings = remember {
        listOf(
            "안녕하세요",   // Korean
            "Hello",      // English
            "Bonjour",    // French
            "Hola",       // Spanish
            "こんにちは"      // Japanese
        )
    }

    var currentGreetingIndex by remember { mutableStateOf(0) }
    var displayedText by remember { mutableStateOf("") }
    var isTyping by remember { mutableStateOf(true) }
    var showCursor by remember { mutableStateOf(true) }

    // 커서 깜빡임
    LaunchedEffect(Unit) {
        while (true) {
            delay(530)
            showCursor = !showCursor
        }
    }

    // 타이핑 효과
    LaunchedEffect(currentGreetingIndex) {
        val currentText = greetings[currentGreetingIndex]
        displayedText = ""
        isTyping = true
        
        // 타이핑 효과 (한 글자씩 나타남)
        currentText.forEachIndexed { index, _ ->
            delay(100)
            displayedText = currentText.substring(0, index + 1)
        }
        
        isTyping = false
        delay(3000) // 3초간 유지
        
        // 삭제 효과 (한 글자씩 사라짐)
        for (i in currentText.length downTo 0) {
            delay(50)
            displayedText = currentText.substring(0, i)
        }
        
        delay(300)
        
        // 다음 인사말로 변경
        currentGreetingIndex = (currentGreetingIndex + 1) % greetings.size
    }

    Box(
        contentAlignment = contentAlignment
    ){
        // 줄어듬 방지용 투명 텍스트
        Column(modifier = Modifier.alpha(0f)) {
            Text(
                text = greetings.maxByOrNull { it.length } ?: greetings.first(),
                fontSize = deviceState.titleFontSize(),
                fontWeight = FontWeight.Bold,
            )
        }
        Row {
            Text(
                text = displayedText,
                fontSize = deviceState.titleFontSize(),
                fontWeight = FontWeight.Bold,
            )
            // 커서
            Text(
                text = "|",
                fontSize = deviceState.titleFontSize(),
                fontWeight = FontWeight.Light,
                modifier = Modifier.alpha(if (showCursor) 1f else 0f)
            )
        }
    }
}