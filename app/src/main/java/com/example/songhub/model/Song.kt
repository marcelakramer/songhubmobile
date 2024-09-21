package com.example.songhub.model

data class Song(
    var id: String? = null, // ID do documento Firestore
    val title: String = "",
    val artist: String = "",
    val album: String = "",
    val duration: String = "",
    val year: String = "",
    val imageUrl: String? = null, // URL da imagem
    val url: String = ""
)