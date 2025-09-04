package io.github.kimmandoo.ui.adaptive

import androidx.compose.runtime.compositionLocalOf
import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.ic_dark_mode
import kimmandoo_porfolio.composeapp.generated.resources.ic_light_mode
import org.jetbrains.compose.resources.DrawableResource

enum class ThemeMode(
    val iconRes: DrawableResource,
) {
    Light(Res.drawable.ic_light_mode),
    Dark(Res.drawable.ic_dark_mode),
    ;

    fun toggle(): ThemeMode =
        when (this) {
            Light -> Dark
            Dark -> Light
        }
}

val LocalThemeMode = compositionLocalOf { ThemeMode.Light }
