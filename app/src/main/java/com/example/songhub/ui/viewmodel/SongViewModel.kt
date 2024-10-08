package com.example.songhub.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.songhub.model.Song
import com.example.songhub.repository.SongRepository
import kotlinx.coroutines.launch

class SongViewModel(val repository: SongRepository) : ViewModel() {
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

    fun updateSong(song: Song, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            Log.d("SongViewModel", "Updating song: $song")
            try {
                repository.updateSong(song)
                Log.d("SongViewModel", "Song updated successfully!")
                onResult(true)
            } catch (e: Exception) {
                Log.e("SongViewModel", "Error updating song: ${e.message}")
                onResult(false)
            }
        }
    }

    fun getSongByTitle(title: String, onResult: (Song?) -> Unit) {
        viewModelScope.launch {
            val song = repository.getSongByTitle(title)
            onResult(song)
        }
    }

    fun getSongById(id: String, onResult: (Song?) -> Unit) {
        viewModelScope.launch {
            val song = repository.getSongById(id)
            onResult(song)
        }
    }

    fun getAllSongs(onResult: (List<Song>) -> Unit) {
        viewModelScope.launch {
            val songs = repository.getAllSongs()
            onResult(songs)
        }
    }

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

    fun deleteSongByUri(uri: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                repository.deleteSongByUri(uri)
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
