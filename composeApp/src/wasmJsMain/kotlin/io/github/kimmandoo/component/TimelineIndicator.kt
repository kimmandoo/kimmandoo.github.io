package io.github.kimmandoo.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TimelineIndicator(
    isFirst: Boolean,
    isLast: Boolean,
    frontHeight: Dp,
    dotSize: Dp,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // 상단 라인 - 첫 번째가 아닐 때만 표시
        Box(
            modifier =
                Modifier
                    .width(2.dp)
                    .height(frontHeight)
                    .background(
                        color = if (isFirst) {
                            MaterialTheme.colorScheme.primary.copy(alpha = 0f)
                        } else {
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        },
                        shape = CircleShape,
                    ),
        )

        Spacer(Modifier.height(8.dp))

        // 중앙 점
        Box(
            modifier =
                Modifier
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        shape = CircleShape,
                    ).size(dotSize),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier =
                    Modifier
                        .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                        .size(6.dp),
            )
        }

        // 하단 라인 - 마지막이 아닐 때만 표시
        if (!isLast) {
            Spacer(Modifier.height(8.dp))
            Box(
                modifier =
                    Modifier
                        .width(2.dp)
                        .fillMaxHeight()
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            shape = CircleShape,
                        ),
            )
        }
    }
}
