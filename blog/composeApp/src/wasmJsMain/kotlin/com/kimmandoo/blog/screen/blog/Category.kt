package com.kimmandoo.blog.screen.blog

enum class Category(val displayName: String) {
    Android("Android"),
    Compose("Compose"),
    Kotlin("Kotlin"),
    Project("Project"),
    ETC("ETC")
    ;

    companion object {
        fun fromUrl(url: String): Category {
            return entries.find { it.name.lowercase() == url.lowercase() } ?: Android
        }
    }
}