package com.kimmandoo.blog

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val link = document.querySelector("link[rel~='icon']")
    	?: document.createElement("link").apply {
            setAttribute("rel", "icon")
        	document.head?.appendChild(this);
        }
    link.setAttribute("href", "https://raw.githubusercontent.com/JetBrains/kotlin-web-site/refs/heads/master/assets/images/favicon.ico");
    ComposeViewport(document.body!!) {
        App()
    }
}