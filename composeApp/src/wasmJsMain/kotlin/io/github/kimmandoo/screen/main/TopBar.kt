package io.github.kimmandoo.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kimmandoo.component.LogoImage
import io.github.kimmandoo.model.Section
import io.github.kimmandoo.ui.adaptive.Device
import io.github.kimmandoo.ui.adaptive.DeviceState
import io.github.kimmandoo.ui.adaptive.LocalThemeMode
import io.github.kimmandoo.ui.adaptive.ThemeMode
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import io.github.kimmandoo.HEADER_HEIGHT
import org.jetbrains.compose.resources.stringResource

@Composable
fun TopBar(
    deviceState: DeviceState,
    modifier: Modifier = Modifier,
    onThemeChanged: (ThemeMode) -> Unit,
    onTitleClick: () -> Unit,
    onSectionClicked: (Section) -> Unit,
    onMenuClick: () -> Unit,
) {
    when (deviceState.value) {
        Device.DESKTOP ->
            DesktopTopBar(
                modifier = modifier,
                onThemeChanged = onThemeChanged,
                onSectionClicked = onSectionClicked,
            )

        Device.TABLET, Device.MOBILE ->
            MobileTopBar(
                modifier = modifier,
                onTitleClick = onTitleClick,
                onMenuClick = onMenuClick,
                onThemeChanged = onThemeChanged,
            )

        Device.UNKNOWN -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MobileTopBar(
    modifier: Modifier = Modifier,
    onTitleClick: () -> Unit,
    onMenuClick: () -> Unit,
    onThemeChanged: (ThemeMode) -> Unit,
) {
    val themeMode = LocalThemeMode.current

    TopAppBar(
        title = {
            LogoImage(onClick = onTitleClick)
        },
        actions = {
            IconButton(onClick = { onThemeChanged(themeMode.toggle()) }) {
                Icon(
                    painter = painterResource(themeMode.iconRes),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.size(24.dp),
                )
            }
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    contentDescription = null,
                )
            }
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
        modifier = modifier.padding(end = 8.dp),
        expandedHeight = HEADER_HEIGHT.dp,
    )
}

@Composable
fun DesktopTopBar(
    modifier: Modifier = Modifier,
    onThemeChanged: (ThemeMode) -> Unit,
    onSectionClicked: (Section) -> Unit,
) {
    val themeMode = LocalThemeMode.current

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(HEADER_HEIGHT.dp)
                .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        LogoImage(onClick = { onSectionClicked(Section.Home) })
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Section.entries.forEach { section ->
                TextButton(
                    onClick = { onSectionClicked(section) }
                ) {
                    Text(
                        text = stringResource(section.title),
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontSize = 16.sp,
                    )
                }
            }
            IconButton(onClick = { onThemeChanged(themeMode.toggle()) }) {
                Icon(
                    painter = painterResource(themeMode.iconRes),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.size(24.dp),
                )
            }
        }
    }
}
