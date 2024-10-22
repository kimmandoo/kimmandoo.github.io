package com.kimmandoo.portfolio

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PortfolioTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = lightColors(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC5),
            background = Color(0xFFF6F6F6),
            onPrimary = Color.White,
            onBackground = Color.Black
        ),
        typography = Typography(
            subtitle1 = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
            subtitle2 = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
            body2 = TextStyle(fontSize = 16.sp)
        ),
        shapes = Shapes(
            medium = RoundedCornerShape(8.dp)
        ),
        content = content
    )
}