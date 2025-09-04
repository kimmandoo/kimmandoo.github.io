package io.github.kimmandoo

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import io.github.kimmandoo.ui.adaptive.ScreenSize
import io.github.kimmandoo.ui.adaptive.ThemeMode
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import io.github.kimmandoo.screen.main.MainScreen
import io.github.kimmandoo.ui.MandooTheme
import io.github.kimmandoo.ui.adaptive.LocalScreenSize
import io.github.kimmandoo.ui.adaptive.LocalThemeMode

@Composable
fun App() {
    val isSystemInDarkTheme = isSystemInDarkTheme()
    var themeMode by remember { mutableStateOf(if (isSystemInDarkTheme) ThemeMode.Dark else ThemeMode.Light) }
    var screenSize by remember { mutableStateOf(ScreenSize()) }

    Layout(
        content = {
            AppContent(
                screenSize = screenSize,
                themeMode = themeMode,
                onThemeChanged = { mode -> themeMode = mode },
            )
        },
        measurePolicy = { measurables, constraints ->
            val width = constraints.maxWidth
            val height = constraints.maxHeight

            screenSize = ScreenSize(width, height)

            val placeables = measurePlaceables(measurables, constraints)

            layout(width, height) {
                placePlaceables(placeables)
            }
        },
    )
}

fun measurePlaceables(
    measurables: List<Measurable>,
    constraints: Constraints,
): List<Placeable> = measurables.map { measurable -> measurable.measure(constraints) }

fun Placeable.PlacementScope.placePlaceables(placeables: List<Placeable>) {
    var yPosition = 0
    placeables.forEach { placeable ->
        placeable.placeRelative(x = 0, y = yPosition)
        yPosition += placeable.height
    }
}

@Composable
fun AppContent(
    screenSize: ScreenSize,
    themeMode: ThemeMode,
    onThemeChanged: (ThemeMode) -> Unit,
) {
    CompositionLocalProvider(
        LocalScreenSize provides screenSize,
        LocalThemeMode provides themeMode,
    ) {
        MandooTheme(isDarkTheme = themeMode == ThemeMode.Light) {
            MainScreen(
                modifier = Modifier.fillMaxSize(),
                onThemeChanged = onThemeChanged,
            )
        }
    }
}