package com.kimmandoo.blog.component

import Route
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TopMenuBarText(onNavigate: (Route) -> Unit, route: Route, selectedRoute: Route){
    Column(modifier = Modifier.noRippleClickable { onNavigate(route) }) {
        Text(
            text = route.route,
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.W600),
            modifier = Modifier.drawBehind { // 텍스트 아래쪽에 그리기
                if (route == selectedRoute) {
                    val strokeWidth = 2.dp.toPx()
                    drawLine(
                        color = Color.Gray,
                        start = androidx.compose.ui.geometry.Offset(0f, size.height + strokeWidth / 2),
                        end = androidx.compose.ui.geometry.Offset(size.width, size.height + strokeWidth / 2),
                        strokeWidth = strokeWidth
                    )
                }
            }
        )
    }
}

@Composable
fun TopNavigationBar(selectedRoute: Route, onNavigate: (Route) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Center) {
        TopMenuBarText(route = Route.BLOG, selectedRoute = selectedRoute, onNavigate = onNavigate)
        Spacer(modifier = Modifier.width(16.dp))
        TopMenuBarText(route = Route.PORTFOLIO, selectedRoute = selectedRoute, onNavigate = onNavigate)
        Spacer(modifier = Modifier.width(16.dp))
        TopMenuBarText(route = Route.ABOUT, selectedRoute = selectedRoute, onNavigate = onNavigate)
    }
}