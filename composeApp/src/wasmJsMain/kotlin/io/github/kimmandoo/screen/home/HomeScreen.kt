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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kimmandoo.*
import io.github.kimmandoo.component.Loading
import io.github.kimmandoo.model.Section
import io.github.kimmandoo.screen.main.component.GreetingAnimation
import io.github.kimmandoo.ui.adaptive.*
import kimmandoo_porfolio.composeapp.generated.resources.*
import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.blue_chip
import kimmandoo_porfolio.composeapp.generated.resources.ic_android
import kimmandoo_porfolio.composeapp.generated.resources.kimmingyu
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSectionClicked: (Section) -> Unit,
) {
    val deviceState = rememberDeviceState()
    val nicknameString = buildNicknameString(deviceState = deviceState)

    when (deviceState.value) {
        Device.UNKNOWN -> {
            Box(modifier = modifier.fillMaxSize()) {
                Loading()
            }
        }

        Device.DESKTOP ->
            HomeDesktopSection(
                nicknameString = nicknameString,
                modifier = modifier,
                onSectionClicked = onSectionClicked,
            )

        Device.TABLET ->
            HomeTabletSection(
                nicknameString = nicknameString,
                modifier = modifier,
            )

        Device.MOBILE ->
            HomeMobileSection(
                nicknameString = nicknameString,
                modifier = modifier,
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
            if(deviceState.value == Device.MOBILE) append("\n")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onPrimaryContainer)) {
                append(stringResource(Res.string.kimmingyu))
            }
        }
    }

@Composable
private fun HomeMobileSection(
    nicknameString: AnnotatedString,
    modifier: Modifier = Modifier,
) {
    val screenSize = LocalScreenSize.current.asDp()

    Box(
        modifier =
            modifier
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
            Column(modifier = Modifier.height(maxOf(MOBILE_CONTENT_MIN_HEIGHT, screenSize.height).dp / 3f,),
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Bottom
            ) {
                Image(
                    modifier = Modifier.height(80.dp),
                    painter = painterResource(
                        resource = Res.drawable.ic_android,
                    ),
                    contentDescription = "버그로이드"
                )
                GreetingAnimation()
            }

            Text(
                text = nicknameString,
                lineHeight = 40.sp,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(Res.string.intro),
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                fontSize = 16.sp,
                lineHeight = 24.sp,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(36.dp))

            Column(
                modifier =
                    Modifier.padding(
                        horizontal = MOBILE_CONTENT_HORIZONTAL_PADDING.dp,
                    ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                GithubButton(modifier = Modifier.fillMaxWidth())
                MediumButton(modifier = Modifier.fillMaxWidth())
                BlogButton(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun HomeTabletSection(
    nicknameString: AnnotatedString,
    modifier: Modifier = Modifier,
) {
    val screenSize = LocalScreenSize.current.asDp()

    Box(
        modifier =
            modifier
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
            Column(modifier = Modifier.height(maxOf(MOBILE_CONTENT_MIN_HEIGHT, screenSize.height).dp / 3f,),
                   horizontalAlignment = Alignment.CenterHorizontally,
                   verticalArrangement = Arrangement.Bottom
            ) {
                Image(
                    modifier = Modifier.height(100.dp),
                    painter = painterResource(
                        resource = Res.drawable.ic_android,
                    ),
                    contentDescription = "버그로이드"
                )
                GreetingAnimation()
            }
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = nicknameString,
                lineHeight = 76.sp,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(Res.string.intro),
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                fontSize = 16.sp,
                lineHeight = 24.sp,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(36.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                GithubButton()
                MediumButton()
                BlogButton()
            }
        }
    }
}

@Composable
private fun HomeDesktopSection(
    nicknameString: AnnotatedString,
    modifier: Modifier = Modifier,
    onSectionClicked: (Section) -> Unit,
) {
    val screenSize = LocalScreenSize.current.asDp()

    Box(
        modifier =
            modifier.then(
                Modifier
                    .width(screenSize.width.dp)
                    .height((maxOf(TABLET_CONTENT_MIN_HEIGHT, screenSize.height) - HEADER_HEIGHT).dp)
                    .padding(horizontal = 120.dp),
            ),
    ) {
        Column(
            modifier =
                Modifier
                    .width((DESKTOP_CONTENT_WIDTH.dp / 1.5f))
                    .align(Alignment.CenterStart),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = nicknameString, lineHeight = 92.sp)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(Res.string.intro),
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                fontSize = 16.sp,
                lineHeight = 24.sp,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(36.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                GithubButton()
                MediumButton()
                BlogButton()
            }
        }

        Box(modifier = Modifier.width(DESKTOP_CONTENT_WIDTH.dp / 2).align(Alignment.CenterEnd),
            contentAlignment = Alignment.Center
        ){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    modifier = Modifier.height(100.dp),
                    painter = painterResource(
                        resource = Res.drawable.ic_android,
                    ),
                    contentDescription = "버그로이드"
                )
                GreetingAnimation()
            }
        }

        AnimatedArrow(
            modifier =
                Modifier
                    .size(128.dp)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp),
            onClick = { onSectionClicked(Section.About) },
        )
    }
}

@Composable
private fun GithubButton(modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current
    val githubUri = "https://github.com/kimmandoo"

    Button(
        onClick = { uriHandler.openUri(githubUri) },
        modifier = modifier.then(Modifier.height(56.dp)),
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
    ) {
        Icon(
            painterResource(Res.drawable.ic_github),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(Res.string.follow_github),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
        )
    }
}

@Composable
private fun MediumButton(modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current
    val blogUri = "https://kimmandoo.medium.com"

    OutlinedButton(
        onClick = { uriHandler.openUri(blogUri) },
        modifier = modifier.then(Modifier.height(56.dp)),
        colors =
            ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_medium),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(Res.string.visit_blog),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
        )
    }
}

@Composable
private fun BlogButton(modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current
    val blogUri = "https://kimmandoo.vercel.app"

    OutlinedButton(
        onClick = { uriHandler.openUri(blogUri) },
        modifier = modifier.then(Modifier.height(56.dp)),
        colors =
            ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_blog),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(Res.string.visit_blog2),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
        )
    }
}