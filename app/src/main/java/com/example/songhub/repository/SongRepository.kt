package com.example.songhub.repository

import com.example.songhub.DAO.SongDAOLocal
import com.example.songhub.model.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SongRepository(private val songDao: SongDAOLocal) {
    suspend fun insertSong(song: Song) {
        withContext(Dispatchers.IO) {
            songDao.insert(song)
        }
    }
    suspend fun getSongByTitle(title: String): Song? {
        return withContext(Dispatchers.IO) {
            songDao.getSongByTitle(title)
        }
    }
    suspend fun getSongById(id: String): Song? {
        return withContext(Dispatchers.IO) {
            songDao.getSongById(id)
        }
    }
    suspend fun getAllSongs(): List<Song> {
        return withContext(Dispatchers.IO) {
            songDao.getAllSongs()
        }
    }
    suspend fun deleteSongByTitle(title: String) {
        withContext(Dispatchers.IO) {
            songDao.deleteSongByTitle(title)
        }
    }
}
