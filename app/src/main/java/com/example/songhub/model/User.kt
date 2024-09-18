package com.example.songhub.model

data class User(
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val favorites: List<Song> = listOf() // Add this line
)
