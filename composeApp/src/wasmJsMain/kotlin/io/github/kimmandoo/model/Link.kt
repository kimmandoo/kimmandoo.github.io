package io.github.kimmandoo.model

import org.jetbrains.compose.resources.StringResource

data class Link(
    val title: StringResource,
    val url: String,
    val icon: String = "",
)

infix fun StringResource.to(url: String): Link = Link(title = this, url = url)
