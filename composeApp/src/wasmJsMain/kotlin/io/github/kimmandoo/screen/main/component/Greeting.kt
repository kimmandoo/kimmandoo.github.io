package io.github.kimmandoo.screen.main.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun GreetingAnimation() {
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
    var visible by remember { mutableStateOf(false) }

    // 1. visible 상태가 바뀔 때마다 alpha 값을 애니메이션으로 변경합니다.
    val animatedAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 1000) // 나타나거나 사라지는 데 1초
    )

    // 2. currentGreetingIndex가 바뀔 때마다 LaunchedEffect를 다시 실행하여 루프를 만듭니다.
    LaunchedEffect(currentGreetingIndex) {
        // Step 1: 글자 나타나기 (Fade In)
        visible = true
        delay(5000) // 3초 동안 글자를 보여줌

        // Step 2: 글자 사라지기 (Fade Out)
        visible = false
        delay(1000) // 글자가 완전히 사라질 때까지 1초 대기 (애니메이션 시간과 동일)

        // Step 3: 다음 인사말로 변경
        currentGreetingIndex = (currentGreetingIndex + 1) % greetings.size
    }

    Box(
        modifier = Modifier.width(300.dp),
        contentAlignment = Alignment.Center // 2. 내용물(Text)을 중앙에 정렬합니다.
    ){
        Text(
            text = greetings[currentGreetingIndex],
            fontSize = 48.sp,
            modifier = Modifier
                .padding(8.dp)
                .alpha(animatedAlpha) // 애니메이션이 적용된 alpha 값 사용
        )
    }
}