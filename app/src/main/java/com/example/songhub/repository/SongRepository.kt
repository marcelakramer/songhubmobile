package com.example.songhub.repository

import com.example.songhub.DAO.SongDAOLocal
import com.example.songhub.model.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SongRepository(private val songDao: SongDAOLocal) {

    // Inserts a song into the database
    suspend fun insertSong(song: Song) {
        withContext(Dispatchers.IO) {
            songDao.insert(song)
        }
    }

    // Retrieves a song by title from the database
    suspend fun getSongByTitle(title: String): Song? {
        return withContext(Dispatchers.IO) {
            songDao.getSongByTitle(title)
        }
    }

    // Retrieves all songs from the database
    suspend fun getAllSongs(): List<Song> {
        return withContext(Dispatchers.IO) {
            songDao.getAllSongs()
        }
    }

    // Deletes a song by title from the database
    suspend fun deleteSongByTitle(title: String) {
        withContext(Dispatchers.IO) {
            songDao.deleteSongByTitle(title)
        }
    }
}
