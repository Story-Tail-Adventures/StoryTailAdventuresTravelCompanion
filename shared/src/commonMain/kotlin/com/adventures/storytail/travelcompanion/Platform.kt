package com.adventures.storytail.travelcompanion

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform