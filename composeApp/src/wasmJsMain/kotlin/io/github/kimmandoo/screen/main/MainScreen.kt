package io.github.kimmandoo.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import io.github.kimmandoo.model.Project
import io.github.kimmandoo.model.Section
import io.github.kimmandoo.navigation.clearHashNavigation
import io.github.kimmandoo.navigation.rememberNavigationState
import io.github.kimmandoo.screen.about.AboutScreen
import io.github.kimmandoo.screen.carex.CareerExperienceScreen
import io.github.kimmandoo.screen.home.HomeScreen
import io.github.kimmandoo.screen.project.ProjectDetailScreen
import io.github.kimmandoo.screen.project.ProjectScreen
import io.github.kimmandoo.ui.adaptive.Device
import io.github.kimmandoo.ui.adaptive.DeviceState
import io.github.kimmandoo.ui.adaptive.ThemeMode
import io.github.kimmandoo.ui.adaptive.rememberDeviceState
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onThemeChanged: (ThemeMode) -> Unit,
){
    val deviceState = rememberDeviceState()
    val listState = rememberLazyListState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // 브라우저 네비게이션 상태 (해시 기반)
    val navigationState = rememberNavigationState()

    LaunchedEffect(deviceState.value) {
        if (deviceState.value == Device.DESKTOP) {
            drawerState.close()
        }
    }

    // 프로젝트 상세 페이지가 선택되었을 때
    if (navigationState.selectedProject != null) {
        ProjectDetailScreen(
            project = navigationState.selectedProject!!,
            onBackClick = { 
                clearHashNavigation()
                navigationState.clearSelection()
            },
            modifier = modifier,
        )
        return
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            gesturesEnabled = false,
            drawerContent = {
                // 내부에 표시될 컨텐츠
                HomeDrawer(
                    onClickItem = {
                        scope.launch {
                            drawerState.close()
                            listState.animateScrollToItem(it.ordinal)
                        }
                    },
                    onDismissRequest = {
                        scope.launch {
                            drawerState.close()
                        }
                    },
                )
            },
            drawerState = drawerState,
            ) {
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
                onProjectClick = { project ->
                    navigationState.navigateToProject(project)
                },
            )
        }
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
    onProjectClick: (Project) -> Unit,
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
            HomeScreen(onSectionClicked = onSectionClicked)
            LazyColumn(
                state = listState,
                modifier =
                    modifier.then(
                        Modifier
                            .padding(innerPadding)
                            .background(color = MaterialTheme.colorScheme.primaryContainer)
                            .fillMaxSize(),
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item { HomeScreen(onSectionClicked = onSectionClicked) }
                item { AboutScreen() }
                item { CareerExperienceScreen() }
                item { ProjectScreen(onProjectClick = onProjectClick) }
            }
        }
    }
}
