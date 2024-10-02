package com.example.songhub.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class Song(
    @PrimaryKey
    var title: String = "",
    var artist: String = "",
    var album: String = "",
    var duration: String = "",
    var year: String = "",
    var imageUrl: String? = null,
    var url: String = "",
    var isLocal: Boolean = false
)
