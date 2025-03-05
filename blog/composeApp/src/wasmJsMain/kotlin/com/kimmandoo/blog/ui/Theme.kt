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

const val KOR = "Pretendard-Regular.ttf"
const val JP = "PretendardJP-Regular.ttf"

@Composable
fun BlogTheme(content: @Composable () -> Unit) {
    var font by remember { mutableStateOf<FontFamily?>(null) }
    val currentFontRes = KOR
    LaunchedEffect(Unit) {
        val fontData = loadResource(currentFontRes)
        font = FontFamily(
            Font(identity = currentFontRes, data = fontData)
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
            h1 = TextStyle(fontFamily = font, fontSize = 30.sp, fontWeight = FontWeight.Bold),
            h2 = TextStyle(fontFamily = font, fontSize = 24.sp, fontWeight = FontWeight.SemiBold),
            h3 = TextStyle(fontFamily = font, fontSize = 20.sp, fontWeight = FontWeight.Medium),
            h4 = TextStyle(fontFamily = font, fontSize = 18.sp, fontWeight = FontWeight.Medium),
            h5 = TextStyle(fontFamily = font, fontSize = 16.sp, fontWeight = FontWeight.Normal),
            h6 = TextStyle(fontFamily = font, fontSize = 14.sp, fontWeight = FontWeight.Light),
            body1 = TextStyle(fontFamily = font, fontSize = 16.sp, fontWeight = FontWeight.Medium),
            body2 = TextStyle(fontFamily = font, fontSize = 14.sp),
            subtitle1 = TextStyle(fontFamily = font, fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
            subtitle2 = TextStyle(fontFamily = font, fontSize = 14.sp, fontWeight = FontWeight.Medium),
            button = TextStyle(fontFamily = font, fontSize = 14.sp, fontWeight = FontWeight.Bold),
            caption = TextStyle(fontFamily = font, fontSize = 12.sp),
            overline = TextStyle(fontFamily = font, fontSize = 10.sp)
        ),
        content = content
    )
}




@OptIn(InternalResourceApi::class)
suspend fun loadResource(path: String): ByteArray {
    return readResourceBytes(path)
}