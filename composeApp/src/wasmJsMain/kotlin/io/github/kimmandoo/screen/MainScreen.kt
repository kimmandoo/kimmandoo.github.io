package io.github.kimmandoo.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.onClick
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.kimmandoo.ui.adaptive.ThemeMode
import io.github.kimmandoo.ui.adaptive.rememberDeviceState

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onThemeChanged: (ThemeMode) -> Unit,
){
    val deviceState = rememberDeviceState()
    val listState = rememberLazyListState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    Column(modifier = modifier) {
        Button(
            onClick = {

            },
            content = {
                Text(
                    text = "나는 메인 스크린"
                )
            },
        )
    }
}