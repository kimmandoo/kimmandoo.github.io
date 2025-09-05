package io.github.kimmandoo.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.onClick
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import io.github.kimmandoo.model.Section
import io.github.kimmandoo.screen.main.TopBar
import io.github.kimmandoo.screen.main.component.GreetingAnimation
import io.github.kimmandoo.ui.adaptive.DeviceState
import io.github.kimmandoo.ui.adaptive.ThemeMode
import io.github.kimmandoo.ui.adaptive.rememberDeviceState
import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.img_android
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onThemeChanged: (ThemeMode) -> Unit,
){
    val deviceState = rememberDeviceState()
    val listState = rememberLazyListState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        MainContent(
            listState = listState,
            deviceState = deviceState,
            modifier = modifier,
            onThemeChanged = onThemeChanged,
            onTitleClick = {
                scope.launch {
                    listState.animateScrollToItem(Section.Home.ordinal)
                }
            },
            onSectionClicked = { section ->
                scope.launch {
                    listState.animateScrollToItem(section.ordinal)
                }
            },
            onMenuClick = {
                scope.launch {
                    drawerState.open()
                }
            },
        )
    }
}


@Composable
fun MainContent(
    listState: LazyListState,
    deviceState: DeviceState,
    modifier: Modifier = Modifier,
    onThemeChanged: (ThemeMode) -> Unit,
    onTitleClick: () -> Unit,
    onSectionClicked: (Section) -> Unit,
    onMenuClick: () -> Unit,
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopBar(
                    onThemeChanged = onThemeChanged,
                    deviceState = deviceState,
                    onTitleClick = onTitleClick,
                    onMenuClick = onMenuClick,
                    onSectionClicked = onSectionClicked,
                )
            },
        ) { innerPadding ->
            Row(modifier = modifier.then(Modifier.padding(innerPadding).fillMaxSize()),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GreetingAnimation()
                Image(
                    modifier = Modifier.height(100.dp),
                    painter = painterResource(
                        resource = Res.drawable.img_android,
                    ),
                    contentDescription = "버그로이드"
                )
            }
//            LazyColumn(
//                state = listState,
//                modifier =
//                    modifier.then(
//                        Modifier
//                            .padding(innerPadding)
//                            .background(color = MaterialTheme.colorScheme.primaryContainer)
//                            .fillMaxSize(),
//                    ),
//                horizontalAlignment = Alignment.CenterHorizontally,
//            ) {
//                item { HomeSection(onSectionClicked = onSectionClicked) }
//                item { AboutSection() }
//                item { CareerSection() }
//                item { ProjectSection(onSectionClicked = onSectionClicked) }
//                item { ExperienceSection() }
//                item { ContactSection() }
//            }
        }
    }
}
