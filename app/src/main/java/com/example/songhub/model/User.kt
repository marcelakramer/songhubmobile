package com.example.songhub.model

data class User(
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val favorites: List<Song> = listOf(),
    val imageUrl: String? = ""
)
