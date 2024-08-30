package com.example.songhub.DAO

import android.net.Uri
import com.example.songhub.model.Song
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage

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
            .addOnSuccessListener { document ->
                val songs = document.toObjects<Song>()
                callback(songs)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }

    fun add(song: Song, callback: (Boolean) -> Unit) {
        db.collection("songs").add(song)
            .addOnSuccessListener { documentReference ->
                song.id = documentReference.id
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    fun update(song: Song, callback: (Boolean) -> Unit) {
        song.id?.let { id ->
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
        song.id?.let { id ->
            db.collection("songs").document(id).delete()
                .addOnSuccessListener {
                    callback(true)
                }
                .addOnFailureListener {
                    callback(false)
                }
        } ?: callback(false)
    }
}