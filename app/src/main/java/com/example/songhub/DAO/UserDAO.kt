package com.example.songhub.DAO

import android.util.Log
import com.example.songhub.model.User
import com.example.songhub.model.UserSession
import com.google.firebase.firestore.FirebaseFirestore

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
                            email = document.getString("email")!!
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
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        callback(false, "Email already exists")
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

    fun logout() {
        UserSession.loggedInUser = null
    }
}