package io.github.kimmandoo.screen.about

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kimmandoo.DESKTOP_CONTENT_HORIZONTAL_PADDING
import io.github.kimmandoo.TABLET_CONTENT_HORIZONTAL_PADDING
import io.github.kimmandoo.ui.Emerald
import io.github.kimmandoo.ui.adaptive.Device
import io.github.kimmandoo.ui.adaptive.contentPadding
import io.github.kimmandoo.ui.adaptive.rememberDeviceState
import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.about_me_title
import kimmandoo_porfolio.composeapp.generated.resources.section_about
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    val deviceState = rememberDeviceState()

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.secondaryContainer,
                            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.95f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
                .padding(deviceState.contentPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // 섹션 뱃지
        Box(
            modifier = Modifier
                .background(
                    color = Emerald.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                stringResource(Res.string.section_about),
                color = Emerald,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                letterSpacing = 1.sp,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 서브 텍스트 구분선
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Emerald.copy(alpha = 0.5f),
                            Emerald,
                            Emerald.copy(alpha = 0.5f)
                        )
                    )
                )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            stringResource(Res.string.about_me_title),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            lineHeight = 44.sp,
            softWrap = true,
        )

        Spacer(modifier = Modifier.height(20.dp))

        val horizontalPadding =
            when (deviceState.value) {
                Device.UNKNOWN -> 0.dp
                Device.MOBILE -> 0.dp
                Device.TABLET -> (TABLET_CONTENT_HORIZONTAL_PADDING / 2).dp
                Device.DESKTOP -> (DESKTOP_CONTENT_HORIZONTAL_PADDING / 2).dp
            }

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(horizontal = horizontalPadding)
        ) {
            AboutMe.entries.forEachIndexed { index, aboutMe ->
                PrettyContentCard(
                    title = aboutMe.titleRes,
                    description = aboutMe.descriptionRes,
                    icon = aboutMe.iconsRes,
                    index = index,
                )
            }
        }
    }
}

@Composable
fun PrettyContentCard(
    title: StringResource,
    description: StringResource,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    index: Int = 0,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.01f else 1f,
        animationSpec = tween(durationMillis = 200)
    )

    val elevation by animateFloatAsState(
        targetValue = if (isHovered) 8f else 2f,
        animationSpec = tween(durationMillis = 200)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .hoverable(interactionSource = interactionSource)
            .shadow(
                elevation = elevation.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Emerald.copy(alpha = 0.25f)
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.Top,
        ) {
            // 아이콘 원형 배경
            icon?.let {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Emerald.copy(alpha = 0.2f),
                                    Emerald.copy(alpha = 0.1f)
                                )
                            ),
                            shape = CircleShape
                        )
                        .border(
                            width = 1.dp,
                            color = Emerald.copy(alpha = 0.3f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                        tint = Emerald,
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                // 인덱스 번호 배지
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(
                        text = stringResource(title),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 24.sp,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(description),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 15.sp,
                    lineHeight = 24.sp,
                )
            }
        }
    }
}