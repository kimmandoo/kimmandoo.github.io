package io.github.kimmandoo.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun TextWithLink(
    url: String,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Unspecified,
) {
    val uriHandler = LocalUriHandler.current

    val annotatedString = buildAnnotatedString {
        pushStringAnnotation(tag = "URL", annotation = url) // 링크 annotation 시작
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary, // 링크 색상 지정
                textDecoration = TextDecoration.Underline, // 밑줄 추가
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        ){
            append("PR LINK")
        }
    }
    Row {

    }
    Text(
        modifier = Modifier.clickable { uriHandler.openUri(url) },
        text = annotatedString,
        fontSize = fontSize,
        color = color,
        fontWeight = fontWeight
    )
}