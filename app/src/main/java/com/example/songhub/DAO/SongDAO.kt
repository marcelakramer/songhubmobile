package com.example.songhub.DAO

import android.net.Uri
import com.example.songhub.model.Song
import com.github.kittinunf.fuel.Fuel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SongDAO {
    val db = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance().reference

    fun uploadImage(imageUri: Uri, callback: (String?) -> Unit) {
        val imageRef = storage.child("song_images/${System.currentTimeMillis()}.jpg")

        imageRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    callback(uri.toString())
                }
            }
            .addOnFailureListener {
                callback(null)
            }
    }

    fun findAll(callback: (List<Song>) -> Unit) {
        db.collection("songs").get()
            .addOnSuccessListener { result ->
                val songs = mutableListOf<Song>()
                for (document in result) {
                    val song = document.toObject(Song::class.java)
                    song.title = document.id
                    songs.add(song)
                }
                callback(songs)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }

    fun add(song: Song, callback: (Boolean) -> Unit) {
        db.collection("songs").add(song)
            .addOnSuccessListener { documentReference ->
                song.title = documentReference.id
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    fun update(song: Song, callback: (Boolean) -> Unit) {
        song.title?.let { id ->
            db.collection("songs").document(id).set(song)
                .addOnSuccessListener {
                    callback(true)
                }
                .addOnFailureListener {
                    callback(false)
                }
        } ?: callback(false)
    }

    fun delete(song: Song, callback: (Boolean) -> Unit) {
        song.title?.let { id ->
            db.collection("songs").document(id).delete()
                .addOnSuccessListener {
                    callback(true)
                }
                .addOnFailureListener {
                    callback(false)
                }
        } ?: callback(false)
    }


    fun findById(id: String, callback: (Song?) -> Unit) {
        db.collection("songs").document(id).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val song = document.toObject(Song::class.java)
                    song?.title = document.id
                    callback(song)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                callback(null)
            }
    }

    private fun extractArtistAndTrack(url: String): Pair<String, String> {
        val parts = url.split("/_/")
        if (parts.size == 2) {
            val artist = parts[0].substringAfterLast("/").replace("+", "%20")
            val track = parts[1].replace("+", "%20")
            return artist to track
        }
        throw IllegalArgumentException("malformed url: $url")
    }

    private fun getTrackInfo(artist: String, track: String, apiKey: String): JsonObject? {
        val url = "https://ws.audioscrobbler.com/2.0/?method=track.getInfo&artist=$artist&track=$track&api_key=$apiKey&format=json"

        val (request, response, result) = Fuel.get(url).responseString()

        return when (result) {
            is com.github.kittinunf.result.Result.Success -> {
                val gson = Gson()
                gson.fromJson(result.value, JsonObject::class.java)
            }
            is com.github.kittinunf.result.Result.Failure -> {
                println("Erro na requisição: ${result.getException()}")
                null
            }
        }
    }

    fun fetchTracksInfo(urls: List<String>, apiKey: String, callback: (List<Song>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val results = urls.map { url ->
                val (artist, track) = extractArtistAndTrack(url)
                getTrackInfo(artist, track, apiKey)
            }

            val songs = results.mapNotNull { trackInfo ->
                trackInfo?.let { info ->
                    val track = info.getAsJsonObject("track")
                    val artistName = track.getAsJsonObject("artist").get("name").asString
                    val trackName = track.get("name").asString
                    val album = track.getAsJsonObject("album").get("title").asString
                    val url = track.get("url").asString

                    var duration = track.get("duration").asString.toLong()
                    val seconds = (duration / 1000) % 60
                    val minutes = (duration / 1000) / 60
                    val formattedDuration = String.format("%d:%02d", minutes, seconds)

                    val publishedDate = track.getAsJsonObject("wiki")?.get("published")?.asString
                    val year = publishedDate?.split(" ")?.get(2)?.trim(',') ?: "Desconhecido"

                    val imagesArray = track.getAsJsonObject("album").getAsJsonArray("image")
                    val imageUrl = if (imagesArray.size() > 3) {
                        imagesArray[3].asJsonObject.get("#text").asString
                    } else {
                        null
                    }

                    Song(
                        title = trackName,
                        artist = artistName,
                        imageUrl = imageUrl,
                        id = url,
                        album = album,
                        duration = formattedDuration,
                        year = year,
                        isLocal = false
                    )
                }
            }

            withContext(Dispatchers.Main) {
                callback(songs)
            }
        }
    }



}