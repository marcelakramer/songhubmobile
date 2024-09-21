package com.example.songhub.DAO

import android.net.Uri
import android.util.Log
import com.example.songhub.model.User
import com.example.songhub.model.UserSession
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class UserDAO {

    private val db = FirebaseFirestore.getInstance()

    fun login(username: String, password: String, onResult: (Boolean) -> Unit) {
        try {
            db.collection("users")
                .whereEqualTo("username", username)
                .whereEqualTo("password", password)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        val document = documents.first()
                        val user = User(
                            username = document.getString("username")!!,
                            password = document.getString("password")!!,
                            email = document.getString("email")!!,
                            imageUrl = document.getString("imageUrl")!!
                        )
                        UserSession.loggedInUser = user
                        onResult(true)
                    } else {
                        onResult(false)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("UserDAO", "Error during login: ", exception)
                    onResult(false)
                }
        } catch (e: Exception) {
            Log.e("UserDAO", "Exception during login: ", e)
            onResult(false)
        }
    }


    fun fetchUser(username: String, password: String, callback: (User?) -> Unit) {
        try {
            db.collection("users")
                .whereEqualTo("username", username)
                .whereEqualTo("password", password)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        callback(null)
                    } else {
                        val document = documents.first()
                        val user = User(
                            username = document.getString("username")!!,
                            password = document.getString("password")!!,
                            email = document.getString("email")!!
                        )
                        callback(user)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("UserDAO", "Error fetching user: ", exception)
                    callback(null)
                }
        } catch (e: Exception) {
            Log.e("UserDAO", "Exception fetching user: ", e)
            callback(null)
        }
    }

    fun registerUser(username: String, password: String, email: String, callback: (Boolean, String) -> Unit) {
        try {
            db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener { emailDocuments ->
                    if (!emailDocuments.isEmpty) {
                        callback(false, "Email already exists")
                    } else {
                        db.collection("users")
                            .whereEqualTo("username", username)
                            .get()
                            .addOnSuccessListener { usernameDocuments ->
                                if (!usernameDocuments.isEmpty) {
                                    callback(false, "Username already exists")
                                } else {
                                    val user = hashMapOf(
                                        "username" to username,
                                        "password" to password,
                                        "email" to email
                                    )
                                    db.collection("users")
                                        .add(user)
                                        .addOnSuccessListener {
                                            callback(true, "User registered successfully")
                                        }
                                        .addOnFailureListener { exception ->
                                            Log.e("UserDAO", "Error registering user: ", exception)
                                            callback(false, "Failed to register user")
                                        }
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.e("UserDAO", "Error checking username: ", exception)
                                callback(false, "Failed to check username")
                            }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("UserDAO", "Error checking email: ", exception)
                    callback(false, "Failed to check email")
                }
        } catch (e: Exception) {
            Log.e("UserDAO", "Exception registering user: ", e)
            callback(false, "Exception occurred during registration")
        }
    }

    fun loadUsers(onResult: (List<User>) -> Unit) {
        try {
            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    val users = result.map { document ->
                        User(
                            username = document.getString("username") ?: "",
                            password = document.getString("password") ?: "",
                            email = document.getString("email") ?: ""
                        )
                    }
                    onResult(users)
                }
                .addOnFailureListener { exception ->
                    Log.e("UserDAO", "Error loading users: ", exception)
                    onResult(emptyList())
                }
        } catch (e: Exception) {
            Log.e("UserDAO", "Exception loading users: ", e)
            onResult(emptyList())
        }
    }

    fun updateUser(oldEmail: String, newUsername: String, newPassword: String, newEmail: String, newImage: String = null.toString(), callback: (Boolean, String) -> Unit) {
        try {
            db.collection("users")
                .whereEqualTo("email", oldEmail)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        callback(false, "User not found")
                    } else {
                        val document = documents.first()
                        val userRef = db.collection("users").document(document.id)

                        userRef.update(mapOf(
                            "username" to newUsername,
                            "password" to newPassword,
                            "email" to newEmail,
                            "imageUrl" to newImage
                        )).addOnSuccessListener {
                            callback(true, "User updated successfully")
                        }.addOnFailureListener { exception ->
                            Log.e("UserDAO", "Error updating user: ", exception)
                            callback(false, "Failed to update user")
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("UserDAO", "Error fetching user: ", exception)
                    callback(false, "Failed to fetch user")
                }
        } catch (e: Exception) {
            Log.e("UserDAO", "Exception during update: ", e)
            callback(false, "Exception occurred during update")
        }
    }

    fun logout() {
        UserSession.loggedInUser = null
    }

    fun addToMySongs(userId: String, trackUrl: String, callback: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()

        // Query to find the user document by username
        db.collection("users")
            .whereEqualTo("username", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    Log.e("Firestore", "No user found with username: $userId")
                    callback(false)
                    return@addOnSuccessListener
                }

                // Assuming there is only one user with the given username
                val userDoc = querySnapshot.documents.firstOrNull() ?: return@addOnSuccessListener
                val userId = userDoc.id // Get the document ID

                // Retrieve the existing favorites list or create a new one
                val mySongs = userDoc.get("mysongs") as? MutableList<String> ?: mutableListOf()

                // Add the new song URL to the favorites list if it's not already there
                if (trackUrl !in mySongs) {
                    mySongs.add(trackUrl)

                    // Update the user document with the new favorites list
                    db.collection("users").document(userId)
                        .update("mysongs", mySongs)
                        .addOnSuccessListener {
                            Log.d("Firestore", "Song added successfully")
                            callback(true)
                        }
                        .addOnFailureListener { e ->
                            Log.e("Firestore", "Error adding song", e)
                            callback(false)
                        }
                } else {
                    Log.d("Firestore", "Song URL is already in my songs")
                    callback(true)
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error querying user by username", e)
                callback(false)
            }
    }

    val storage = FirebaseStorage.getInstance().reference
    fun uploadImage(imageUri: Uri, username: String, callback: (String?) -> Unit) {
        val imageRef = storage.child("profile_pictures/${username}.jpg")

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

    fun getMySongs(userId: String, callback: (List<String>?) -> Unit) {
        db.collection("users")
            .whereEqualTo("username", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    Log.e("Firestore", "No user found with username: $userId")
                    callback(null)
                    return@addOnSuccessListener
                }

                // Assuming there is only one user with the given username
                val userDoc = querySnapshot.documents.firstOrNull() ?: return@addOnSuccessListener

                // Retrieve the existing favorites list or return an empty list
                val mySongs = userDoc.get("mysongs") as? List<String> ?: emptyList()

                callback(mySongs)
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error querying user by username", e)
                callback(null)
            }
    }

}