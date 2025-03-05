package com.kimmandoo.blog

import BlogTheme
import Route
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource

import blog.composeapp.generated.resources.Res
import blog.composeapp.generated.resources.compose_multiplatform
import com.kimmandoo.blog.component.Title
import com.kimmandoo.blog.component.TopMenuBarText
import com.kimmandoo.blog.component.TopNavigationBar
import com.kimmandoo.blog.screen.AboutScreen
import com.kimmandoo.blog.screen.BlogScreen
import com.kimmandoo.blog.screen.PortfolioScreen
import kotlinx.browser.window
import kotlinx.coroutines.delay


@Composable
fun App() {
    var currentRoute: Route by remember { mutableStateOf(getCurrentRouteFromUrl()) }

    LaunchedEffect(Unit) {
        if (window.location.hash.isEmpty()) { // hash비어있으면 BLOG를 기준으로
            window.location.hash = Route.BLOG.route
            currentRoute = Route.BLOG
        }
        window.onhashchange = {
            currentRoute = getCurrentRouteFromUrl()
        }
    }

    BlogTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Title()
            TopNavigationBar(selectedRoute = currentRoute) { newRoute ->
                window.location.hash = newRoute.route // url업뎃하기
                currentRoute = newRoute
            }
            Spacer(modifier = Modifier.height(16.dp))
            when (currentRoute) {
                Route.BLOG -> BlogScreen()
                Route.ABOUT -> AboutScreen()
                Route.PORTFOLIO -> PortfolioScreen()
            }
        }
    }
}

fun getCurrentRouteFromUrl(): Route {
    val hash = window.location.hash.removePrefix("#")
    return Route.entries.find { it.name.lowercase() == hash } ?: Route.BLOG
}