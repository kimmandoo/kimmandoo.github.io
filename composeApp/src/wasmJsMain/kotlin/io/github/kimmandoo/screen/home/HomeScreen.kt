package io.github.kimmandoo.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.*
import io.github.kimmandoo.*
import io.github.kimmandoo.component.Loading
import io.github.kimmandoo.model.Section
import io.github.kimmandoo.screen.main.component.GreetingAnimation
import io.github.kimmandoo.ui.adaptive.*
import kimmandoo_porfolio.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.w3c.dom.Text

// 상수 관리: URL 및 설정값
private object HomeConfig {
    const val GITHUB_URL = "https://github.com/kimmandoo"
    const val MEDIUM_URL = "https://kimmandoo.medium.com"
    const val BLOG_URL = "https://kimmandoo.vercel.app"

    val BUTTON_HEIGHT = 56.dp
    val ICON_SIZE = 24.dp
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSectionClicked: (Section) -> Unit,
) {
    val deviceState = rememberDeviceState()

    // 로딩 상태 처리
    if (deviceState.value == Device.UNKNOWN) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Loading()
        }
        return
    }

    val nicknameString = buildNicknameString(deviceState)

    // 레이아웃 분기 처리
    when (deviceState.value) {
        Device.DESKTOP ->
            HomeDesktopSection(
                nicknameString = nicknameString,
                modifier = modifier,
                onSectionClicked = onSectionClicked,
                fontSize = TextUnit(deviceState.titleFontSize().value + 4.sp.value, TextUnitType.Sp)
            )
        // Mobile과 Tablet은 구조가 비슷하므로 통합하여 재사용성 증대
        else ->
            HomeCenteredSection(
                nicknameString = nicknameString,
                isMobile = deviceState.value == Device.MOBILE,
                modifier = modifier,
                fontSize = TextUnit(deviceState.titleFontSize().value + 4.sp.value, TextUnitType.Sp)
            )
    }
}

// ------------------------------------------------------------------------
// Layout Sections
// ------------------------------------------------------------------------

/**
 * Mobile 및 Tablet용 중앙 정렬 레이아웃
 */
@Composable
private fun HomeCenteredSection(
    nicknameString: AnnotatedString,
    isMobile: Boolean,
    modifier: Modifier = Modifier,
    fontSize: TextUnit
) {
    val screenSize = LocalScreenSize.current.asDp()
    val contentMinHeight = if (isMobile) MOBILE_CONTENT_MIN_HEIGHT else TABLET_CONTENT_MIN_HEIGHT
    val topSpacerRatio = 3f

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = MOBILE_CONTENT_VERTICAL_PADDING.dp,
                horizontal = MOBILE_CONTENT_HORIZONTAL_PADDING.dp,
            ),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // 상단 아이콘 및 애니메이션 영역
            Column(
                modifier = Modifier.height(maxOf(contentMinHeight, screenSize.height).dp / topSpacerRatio),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                BugroidIcon(size = if (isMobile) 80.dp else 100.dp)
                GreetingAnimation()
            }

            // 텍스트 영역
            HomeTextArea(
                nicknameString = nicknameString,
                textAlign = TextAlign.Center,
                fontSize = fontSize
            )

            Spacer(modifier = Modifier.height(36.dp))

            // 버튼 영역
            SocialButtonsLayout(
                isVertical = isMobile, // 모바일만 수직 배치
                modifier = if (isMobile) Modifier.padding(horizontal = MOBILE_CONTENT_HORIZONTAL_PADDING.dp) else Modifier
            )
        }
    }
}

/**
 * Desktop용 좌우 분할 레이아웃
 */
@Composable
private fun HomeDesktopSection(
    nicknameString: AnnotatedString,
    modifier: Modifier = Modifier,
    onSectionClicked: (Section) -> Unit,
    fontSize: TextUnit
) {
    val screenSize = LocalScreenSize.current.asDp()

    Box(
        modifier = modifier
            .width(screenSize.width.dp)
            .height((maxOf(TABLET_CONTENT_MIN_HEIGHT, screenSize.height) - HEADER_HEIGHT).dp)
            .padding(horizontal = 120.dp),
    ) {
        // 좌측: 텍스트 및 버튼
        Column(
            modifier = Modifier
                .width((DESKTOP_CONTENT_WIDTH.dp / 1.5f))
                .align(Alignment.CenterStart),
            horizontalAlignment = Alignment.CenterHorizontally, // 원본 유지 (좌측 정렬이 더 자연스러울 수 있으나 원본 존중)
            verticalArrangement = Arrangement.Center,
        ) {
            HomeTextArea(
                nicknameString = nicknameString,
                textAlign = TextAlign.Center, // 원본 유지
                fontSize = fontSize
            )

            Spacer(modifier = Modifier.height(36.dp))

            SocialButtonsLayout(isVertical = false)
        }

        // 우측: 아이콘 및 애니메이션
        Box(
            modifier = Modifier
                .width(DESKTOP_CONTENT_WIDTH.dp / 2)
                .align(Alignment.CenterEnd),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                BugroidIcon(size = 100.dp)
                GreetingAnimation()
            }
        }

        // 하단 화살표
        AnimatedArrow(
            modifier = Modifier
                .size(128.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            onClick = { onSectionClicked(Section.About) },
        )
    }
}

// ------------------------------------------------------------------------
// Components
// ------------------------------------------------------------------------

@Composable
private fun BugroidIcon(size: Dp) {
    Image(
        modifier = Modifier.height(size),
        painter = painterResource(resource = Res.drawable.ic_android),
        contentDescription = "버그로이드"
    )
}

@Composable
private fun HomeTextArea(
    nicknameString: AnnotatedString,
    textAlign: TextAlign,
    fontSize: TextUnit
) {
    Text(
        text = nicknameString,
        lineHeight = fontSize, // 상황에 따라 조정 필요
        textAlign = textAlign,
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = stringResource(Res.string.intro),
        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
        fontSize = 16.sp,
        lineHeight = 24.sp,
        textAlign = textAlign,
    )
}

@Composable
private fun SocialButtonsLayout(
    isVertical: Boolean,
    modifier: Modifier = Modifier
) {
    if (isVertical) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SocialButtonsContent(fillMaxWidth = true)
        }
    } else {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SocialButtonsContent(fillMaxWidth = false)
        }
    }
}

@Composable
private fun SocialButtonsContent(fillMaxWidth: Boolean) {
    val modifier = if (fillMaxWidth) Modifier.fillMaxWidth() else Modifier

    SocialButton(
        text = stringResource(Res.string.follow_github),
        icon = Res.drawable.ic_github,
        url = HomeConfig.GITHUB_URL,
        isPrimary = true,
        modifier = modifier
    )
    SocialButton(
        text = stringResource(Res.string.visit_blog),
        icon = Res.drawable.ic_medium,
        url = HomeConfig.MEDIUM_URL,
        modifier = modifier
    )
    SocialButton(
        text = stringResource(Res.string.visit_blog2),
        icon = Res.drawable.ic_blog,
        url = HomeConfig.BLOG_URL,
        modifier = modifier
    )
}

/**
 * 재사용 가능한 통합 소셜 버튼
 */
@Composable
private fun SocialButton(
    text: String,
    icon: DrawableResource,
    url: String,
    isPrimary: Boolean = false,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current

    val buttonModifier = modifier.height(HomeConfig.BUTTON_HEIGHT)
    val contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp)
    val onClick = { uriHandler.openUri(url) }

    // 버튼 내부 컨텐츠
    val content: @Composable RowScope.() -> Unit = {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(HomeConfig.ICON_SIZE),
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = text,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
        )
    }

    if (isPrimary) {
        Button(
            onClick = onClick,
            modifier = buttonModifier,
            contentPadding = contentPadding,
            content = content
        )
    } else {
        OutlinedButton(
            onClick = onClick,
            modifier = buttonModifier,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            contentPadding = contentPadding,
            content = content
        )
    }
}

@Composable
private fun buildNicknameString(deviceState: DeviceState): AnnotatedString =
    buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = deviceState.titleFontSize(),
                fontWeight = FontWeight.Black,
            ),
        ) {
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(stringResource(Res.string.blue_chip))
            }
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onPrimaryContainer)) {
                append(stringResource(Res.string.kimmingyu))
            }
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(".")
            }
        }
    }