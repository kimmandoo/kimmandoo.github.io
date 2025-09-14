package io.github.kimmandoo.screen.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kimmandoo.DESKTOP_CONTENT_HORIZONTAL_PADDING
import io.github.kimmandoo.TABLET_CONTENT_HORIZONTAL_PADDING
import io.github.kimmandoo.ui.DarkGray
import io.github.kimmandoo.ui.adaptive.Device
import io.github.kimmandoo.ui.adaptive.contentPadding
import io.github.kimmandoo.ui.adaptive.rememberDeviceState
import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.about_me_title
import kimmandoo_porfolio.composeapp.generated.resources.about_me_title1
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
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(deviceState.contentPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            stringResource(Res.string.section_about),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 36.sp,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            stringResource(Res.string.about_me_title),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            lineHeight = 36.sp,
            softWrap = true,
        )

        Spacer(modifier = Modifier.height(24.dp))

        val horizontalPadding =
            when (deviceState.value) {
                Device.UNKNOWN -> 0.dp
                Device.MOBILE -> 0.dp
                Device.TABLET -> (TABLET_CONTENT_HORIZONTAL_PADDING / 2).dp
                Device.DESKTOP -> (DESKTOP_CONTENT_HORIZONTAL_PADDING / 2).dp
            }

        AboutMe.entries.forEach { aboutMe ->
            PrettyContentCard(
                title = aboutMe.titleRes,
                description = aboutMe.descriptionRes,
                modifier = Modifier.padding(horizontal = horizontalPadding),
                icon = aboutMe.iconsRes,
            )
            Spacer(modifier.height(24.dp))
        }
    }
}

@Composable
private fun ContentCard(
    title: StringResource,
    description: StringResource,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(12.dp),
                    ambientColor = DarkGray.copy(0.01f),
                    spotColor = DarkGray.copy(0.01f),
                    clip = false,
                ).background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(12.dp),
                ).padding(20.dp),
    ) {
        Text(
            text = stringResource(title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(description),
            fontSize = 16.sp,
            lineHeight = 22.sp,
            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f),
        )
    }
}

@Composable
fun PrettyContentCard(
    title: StringResource,
    description: StringResource,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // 아이콘이 null이 아닐 경우에만 표시
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null, // 장식용 아이콘이므로 null 처리
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.width(16.dp))
            }

            Column(modifier = Modifier.weight(1f)) { // 텍스트 영역이 남은 공간을 모두 차지
                Text(
                    text = stringResource(title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(description),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 22.sp,
                )
            }
        }
    }
}