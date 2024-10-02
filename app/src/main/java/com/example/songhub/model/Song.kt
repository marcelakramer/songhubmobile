package com.example.songhub.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class Song(
    @PrimaryKey
    var id: String = "",
    var title: String = "",
    var artist: String = "",
    var album: String = "",
    var duration: String = "",
    var year: String = "",
    var imageUrl: String? = null,
    var isLocal: Boolean = false
)
