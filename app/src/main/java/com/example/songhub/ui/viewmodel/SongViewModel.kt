package com.example.songhub.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.songhub.model.Song
import com.example.songhub.repository.SongRepository
import kotlinx.coroutines.launch

class SongViewModel(val repository: SongRepository) : ViewModel() {

    // Adds a new song to the database
    fun addSong(song: Song, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                repository.insertSong(song)
                onResult(true)
                printAllSongs()
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    // Updates an existing song in the database
    fun updateSong(song: Song, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                // Assuming `insertSong` updates if the song already exists.
                // If a separate update method is needed, add it to the repository.
                repository.insertSong(song)
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    // Retrieves a song by title from the database
    fun getSongByTitle(title: String, onResult: (Song?) -> Unit) {
        viewModelScope.launch {
            val song = repository.getSongByTitle(title)
            onResult(song)
        }
    }

    // Retrieves all songs from the database
    fun getAllSongs(onResult: (List<Song>) -> Unit) {
        viewModelScope.launch {
            val songs = repository.getAllSongs()
            onResult(songs)
        }
    }

    // Deletes a song by title from the database
    fun deleteSongByTitle(title: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                repository.deleteSongByTitle(title)
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun printAllSongs() {
        viewModelScope.launch {
            val songs = repository.getAllSongs()
            songs.forEach { song ->
                println("Title: ${song.title}, Artist: ${song.artist}, Album: ${song.album}, Year: ${song.year}")
            }
        }
    }
}
