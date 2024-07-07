package com.vodimobile

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform