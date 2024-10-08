package com.example.songhub.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.songhub.model.Song

@Dao
interface SongDAOLocal {
    @Insert
    fun insert(song: Song)

    @Update
    fun update(song: Song)

    @Query("SELECT * FROM songs WHERE title = :title")
    fun getSongByTitle(title: String): Song?

    @Query("SELECT * FROM songs WHERE id = :id")
    fun getSongById(id: String): Song?

    @Query("SELECT * FROM songs")
    fun getAllSongs(): List<Song>

    @Query("DELETE FROM songs WHERE title = :title")
    fun deleteSongByTitle(title: String)

    @Query("DELETE FROM songs WHERE id = :uri")
    fun deleteSongByUri(uri: String)
}
