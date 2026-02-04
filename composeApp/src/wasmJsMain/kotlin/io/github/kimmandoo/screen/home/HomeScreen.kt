package io.github.kimmandoo.screen.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.random.Random

// 상수 관리: URL 및 설정값
private object HomeConfig {
    const val GITHUB_URL = "https://github.com/kimmandoo"
    const val MEDIUM_URL = "https://kimmandoo.medium.com"
    const val BLOG_URL = "https://kimmandoo.vercel.app"

    val BUTTON_HEIGHT = 56.dp
    val ICON_SIZE = 24.dp
    val BUTTON_CORNER_RADIUS = 28.dp
}

// 파티클 데이터 클래스
private data class Particle(
    val id: Int,
    val initialX: Float,
    val initialY: Float,
    val size: Float,
    val speed: Float,
    val alpha: Float,
    val delay: Int
)

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
    val introText = stringResource(Res.string.intro)

    // 파티클 생성
    val particles = remember {
        List(20) { index ->
            Particle(
                id = index,
                initialX = Random.nextFloat(),
                initialY = Random.nextFloat(),
                size = Random.nextFloat() * 4f + 2f,
                speed = Random.nextFloat() * 0.5f + 0.3f,
                alpha = Random.nextFloat() * 0.4f + 0.1f,
                delay = Random.nextInt(0, 5000)
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // 파티클 배경
        ParticleBackground(
            particles = particles,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = MOBILE_CONTENT_VERTICAL_PADDING.dp,
                    horizontal = MOBILE_CONTENT_HORIZONTAL_PADDING.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // 상단 아이콘 및 애니메이션 영역
            Column(
                modifier = Modifier.height(maxOf(contentMinHeight, screenSize.height).dp / topSpacerRatio),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                GlowingBugroidIcon(size = if (isMobile) 80.dp else 100.dp)
                Spacer(modifier = Modifier.height(8.dp))
                GreetingAnimation()
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 텍스트 영역
            HomeTextArea(
                nicknameString = nicknameString,
                introText = introText,
                textAlign = TextAlign.Center,
                fontSize = fontSize
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 버튼 영역
            SocialButtonsLayout(
                isVertical = isMobile,
                modifier = if (isMobile) Modifier.padding(horizontal = 8.dp) else Modifier
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
    val introText = stringResource(Res.string.intro)

    // 파티클 생성
    val particles = remember {
        List(10) { index ->
            Particle(
                id = index,
                initialX = Random.nextFloat(),
                initialY = Random.nextFloat(),
                size = Random.nextFloat() * 150f + 2f,
                speed = Random.nextFloat() * 0.6f + 0.2f,
                alpha = Random.nextFloat() * 0.3f + 0.1f,
                delay = Random.nextInt(0, 5000)
            )
        }
    }

    Box(
        modifier = modifier
            .width(screenSize.width.dp)
            .height((maxOf(TABLET_CONTENT_MIN_HEIGHT, screenSize.height) - HEADER_HEIGHT).dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // 파티클 배경
        ParticleBackground(
            particles = particles,
            modifier = Modifier.fillMaxSize()
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 80.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // 좌측: 텍스트 및 버튼
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 40.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
            ) {
                HomeTextArea(
                    nicknameString = nicknameString,
                    introText = introText,
                    textAlign = TextAlign.Start,
                    fontSize = fontSize
                )

                Spacer(modifier = Modifier.height(40.dp))

                SocialButtonsLayout(isVertical = false)
            }

            Spacer(modifier = Modifier.width(60.dp))

            // 우측: 아이콘 및 애니메이션
            Box(
                modifier = Modifier.weight(0.6f),
                contentAlignment = Alignment.Center
            ) {
                // 배경 글로우 원
                Box(
                    modifier = Modifier
                        .size(280.dp)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.04f),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    GlowingBugroidIcon(size = 140.dp)
                    Spacer(modifier = Modifier.height(12.dp))
                    GreetingAnimation()
                }
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

/**
 * 파티클 배경 컴포넌트
 */
@Composable
private fun ParticleBackground(
    particles: List<Particle>,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "particles")
    val primaryColor = MaterialTheme.colorScheme.primary

    // 애니메이션을 Composable 컨텍스트에서 미리 생성
    val animatedParticles = particles.map { particle ->
        val progress by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = (8000 / particle.speed).toInt(),
                    delayMillis = particle.delay,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "particle_${particle.id}"
        )
        particle to progress
    }

    Box(modifier = modifier.drawBehind {
        animatedParticles.forEach { (particle, progress) ->
            val x = particle.initialX * size.width + (progress * 100f - 50f)
            val y = particle.initialY * size.height + (progress * 80f - 40f)

            drawCircle(
                color = primaryColor.copy(alpha = particle.alpha * (0.3f + progress * 0.7f)),
                radius = particle.size,
                center = Offset(x, y)
            )
        }
    })
}

/**
 * 글로우 효과가 있는 버그로이드 아이콘
 */
@Composable
private fun GlowingBugroidIcon(size: Dp) {
    val infiniteTransition = rememberInfiniteTransition(label = "glow")

    // 부드러운 위아래 움직임
    val offsetY by infiniteTransition.animateFloat(
        initialValue = -6f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float_y"
    )

    // 글로우 펄스
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    Box(contentAlignment = Alignment.Center) {
        // 글로우 배경
        Box(
            modifier = Modifier
                .size(size * 1.4f)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF3DDC84).copy(alpha = glowAlpha * 0.3f),
                            Color(0xFF3DDC84).copy(alpha = glowAlpha * 0.1f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        Image(
            modifier = Modifier
                .height(size)
                .graphicsLayer { translationY = offsetY },
            painter = painterResource(resource = Res.drawable.ic_android),
            contentDescription = "버그로이드"
        )
    }
}

/**
 * 텍스트 영역
 */
@Composable
private fun HomeTextArea(
    nicknameString: AnnotatedString,
    introText: String,
    textAlign: TextAlign,
    fontSize: TextUnit
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = nicknameString,
        lineHeight = fontSize * 1.2f,
        letterSpacing = (-0.02).em,
        textAlign = textAlign,
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = introText,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        fontSize = 16.sp,
        lineHeight = 26.sp,
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
    val onClick = { uriHandler.openUri(url) }
    val shape = RoundedCornerShape(HomeConfig.BUTTON_CORNER_RADIUS)
    val contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp)

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
            modifier = modifier.height(HomeConfig.BUTTON_HEIGHT),
            shape = shape,
            contentPadding = contentPadding,
            content = content
        )
    } else {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.height(HomeConfig.BUTTON_HEIGHT),
            shape = shape,
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