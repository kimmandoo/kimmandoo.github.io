package com.kimmandoo.portfolio

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.readResourceBytes

@Composable
fun PortfolioTheme(content: @Composable () -> Unit) {
    var fontIBMPlex by remember { mutableStateOf<FontFamily?>(null) }

    LaunchedEffect(Unit) {
        val fontData = loadResource("IBMPlexSansKR-Regular.ttf")
        fontIBMPlex = FontFamily(
            Font(identity = "IBMPlexSansKR-Regular", data = fontData)
        )
    }

    MaterialTheme(
        colors = lightColors(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC5),
            background = Color(0xFFF6F6F6),
            onPrimary = Color.White,
            onBackground = Color.Black
        ),
        typography = Typography(
            h5 = TextStyle(fontFamily = fontIBMPlex, fontWeight = FontWeight.Bold, fontSize = 24.sp ),
            subtitle1 = TextStyle(fontFamily = fontIBMPlex, fontWeight = FontWeight.Bold, fontSize = 18.sp),
            subtitle2 = TextStyle(fontFamily = fontIBMPlex, fontWeight = FontWeight.Bold, fontSize = 16.sp),
            body2 = TextStyle(fontSize = 16.sp)
        ),
        shapes = Shapes(
            medium = RoundedCornerShape(8.dp)
        ),
        content = content
    )
}




@OptIn(InternalResourceApi::class)
suspend fun loadResource(path: String): ByteArray {
    return readResourceBytes(path)
}