package com.example.songhub.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class Song(
    @PrimaryKey
    var title: String = "",     // Changed from val to var
    var artist: String = "",    // Changed from val to var
    var album: String = "",     // Changed from val to var
    var duration: String = "",   // Changed from val to var
    var year: String = "",      // Changed from val to var
    var imageUrl: String? = null, // Changed from val to var
    var url: String = ""        // Changed from val to var
)
