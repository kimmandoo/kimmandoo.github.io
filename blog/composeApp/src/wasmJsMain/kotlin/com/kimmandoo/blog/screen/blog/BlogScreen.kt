package com.kimmandoo.blog.screen.blog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.browser.window

@Composable
fun BlogScreen() {
    var selectedCategory by remember { mutableStateOf(getCurrentCategoryFromUrl()) }

    var isWideScreen by remember { mutableStateOf(window.innerWidth > 1000) }

    // 화면 크기 감지
    LaunchedEffect(Unit) {
        window.addEventListener("resize", {
            isWideScreen = window.innerWidth > 1000
        })
    }

    // URL 변경 감지
    LaunchedEffect(Unit) {
        if (window.location.hash.isEmpty()) {
            window.location.hash = "blog/${Category.Android.name.lowercase()}"
            selectedCategory = Category.Android
        }
        window.onhashchange = {
            selectedCategory = getCurrentCategoryFromUrl()
        }
    }

    Row(modifier = Modifier.fillMaxSize()) {
        if (isWideScreen) {
            Column(
                modifier = Modifier.weight(0.2f).padding(top=100.dp, end = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Category.entries.forEach { category ->
                    Text(
                        text = category.displayName,
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier
                            .clickable {
                                window.location.hash = "blog/${category.name.lowercase()}"
                                selectedCategory = category
                            }
                            .padding(8.dp)
                            .drawBehind {
                                if (selectedCategory == category) {
                                    drawLine(
                                        color = Color.Gray,
                                        start = Offset(0f, size.height),
                                        end = Offset(size.width, size.height),
                                        strokeWidth = 2f
                                    )
                                }
                            }
                    )
                }
            }
        }

        // 본문 영역
        Column(
            modifier = Modifier.weight(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Showing posts for category: ${selectedCategory.displayName}")
            // 여기에 블로그 포스트 리스트 로직 추가 가능
        }
    }
}

fun getCurrentCategoryFromUrl(): Category {
    val hash = window.location.hash.removePrefix("#blog/")
    return Category.fromUrl(hash)
}

