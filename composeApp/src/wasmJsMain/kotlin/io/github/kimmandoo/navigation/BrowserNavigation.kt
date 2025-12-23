package io.github.kimmandoo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.kimmandoo.model.Project
import kotlinx.browser.window
import org.w3c.dom.HashChangeEvent
import org.w3c.dom.events.Event

/**
 * 브라우저 해시 기반 네비게이션을 관리하는 상태 클래스
 */
class NavigationState {
    var selectedProject by mutableStateOf<Project?>(null)
        private set

    fun navigateToProject(project: Project) {
        selectedProject = project
        window.location.hash = "project/${project.name.lowercase()}"
    }

    fun navigateBack() {
        selectedProject = null
        window.history.back()
    }

    fun clearSelection() {
        selectedProject = null
    }

    fun parseHashAndNavigate(hash: String) {
        val cleanHash = hash.removePrefix("#")
        
        when {
            cleanHash.startsWith("project/") -> {
                val projectName = cleanHash.removePrefix("project/")
                val project = Project.entries.find { 
                    it.name.lowercase() == projectName.lowercase() 
                }
                selectedProject = project
            }
            cleanHash.isEmpty() || cleanHash == "/" -> {
                selectedProject = null
            }
            else -> {
                selectedProject = null
            }
        }
    }
}

@Composable
fun rememberNavigationState(): NavigationState {
    val navigationState = remember { NavigationState() }

    // 초기 해시 파싱
    LaunchedEffect(Unit) {
        val initialHash = window.location.hash
        if (initialHash.isNotEmpty()) {
            navigationState.parseHashAndNavigate(initialHash)
        }
    }

    // hashchange 이벤트 리스너
    DisposableEffect(Unit) {
        val listener: (Event) -> Unit = { event ->
            val hashEvent = event as? HashChangeEvent
            val newHash = window.location.hash
            navigationState.parseHashAndNavigate(newHash)
        }

        window.addEventListener("hashchange", listener)

        onDispose {
            window.removeEventListener("hashchange", listener)
        }
    }

    // popstate 이벤트 리스너 (뒤로가기 버튼)
    DisposableEffect(Unit) {
        val listener: (Event) -> Unit = { _ ->
            val currentHash = window.location.hash
            navigationState.parseHashAndNavigate(currentHash)
        }

        window.addEventListener("popstate", listener)

        onDispose {
            window.removeEventListener("popstate", listener)
        }
    }

    return navigationState
}

/**
 * 메인 페이지로 해시 초기화
 */
fun clearHashNavigation() {
    if (window.location.hash.isNotEmpty()) {
        window.history.pushState(null, "", window.location.pathname)
    }
}
