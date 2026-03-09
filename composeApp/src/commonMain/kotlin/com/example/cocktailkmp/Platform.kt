package com.example.cocktailkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform