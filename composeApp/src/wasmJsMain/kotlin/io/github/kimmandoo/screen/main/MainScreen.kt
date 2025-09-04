package io.github.kimmandoo.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.onClick
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kimmandoo.screen.main.component.GreetingAnimation
import io.github.kimmandoo.ui.adaptive.ThemeMode
import io.github.kimmandoo.ui.adaptive.rememberDeviceState
import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.img_android
import org.jetbrains.compose.resources.painterResource

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onThemeChanged: (ThemeMode) -> Unit,
){
    val deviceState = rememberDeviceState()
    val listState = rememberLazyListState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    Row(modifier = modifier.height(IntrinsicSize.Max), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        GreetingAnimation()
        Image(
            modifier = Modifier.height(100.dp),
            painter = painterResource(
                resource = Res.drawable.img_android,
            ),
            contentDescription = "버그로이드"
        )
    }
}