package com.kimmandoo.blog

import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.readResourceBytes

@OptIn(InternalResourceApi::class)
suspend fun readFileToString(fileName: String): String{
   return readResourceBytes(fileName).decodeToString()
}