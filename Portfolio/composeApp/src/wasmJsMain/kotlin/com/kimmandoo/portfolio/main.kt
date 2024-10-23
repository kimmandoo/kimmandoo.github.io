package com.kimmandoo.portfolio

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
    link.setAttribute("href", "https://kimmandooo.tistory.com/favicon.ico");

    ComposeViewport(document.body!!) {
        App()
    }
}