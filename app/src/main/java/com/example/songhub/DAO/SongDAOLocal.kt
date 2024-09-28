package com.example.songhub.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.songhub.model.Song

@Dao
interface SongDAOLocal {
    @Insert
    fun insert(song: Song)

    @Query("SELECT * FROM songs WHERE title = :title")
    fun getSongByTitle(title: String): Song?

    @Query("SELECT * FROM songs")
    fun getAllSongs(): List<Song>

    @Query("DELETE FROM songs WHERE title = :title")
    fun deleteSongByTitle(title: String)
}
