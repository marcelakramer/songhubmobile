package com.example.songhub

import SearchSong
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.songhub.model.Song
import com.example.songhub.ui.layout.MainLayout
import com.example.songhub.ui.screens.FavoritesScreen
import com.example.songhub.ui.screens.LoginScreen
import com.example.songhub.ui.screens.MainScreen
import com.example.songhub.ui.screens.ProfileScreen
import com.example.songhub.ui.screens.RegisterScreen
import com.example.songhub.ui.screens.SongInfoScreen
import com.example.songhub.ui.screens.SongRegisterScreen
import com.example.songhub.ui.theme.SonghubTheme
import com.google.firebase.FirebaseApp
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            SonghubTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(onLoginClick = {
                            navController.navigate("main")
                        }, onRegisterClick = {
                            navController.navigate("register")
                        })
                    }
                    composable("register") {
                        RegisterScreen(modifier = Modifier, onLoginClick = {
                            navController.navigate("login")
                        }, onRegisterSuccess = { navController.navigate("login") })
                    }
                    composable("main") {
                        MainLayout(title = "My songs", navController = navController) {
                            MainScreen(modifier = Modifier, navController = navController)
                        }
                    }
                    composable("profile") {
                        MainLayout(title = "My profile", navController = navController) {
                            ProfileScreen(
                                modifier = Modifier,
                                navController = navController
                            )
                        }
                    }
                    composable("addSong?songJson={songJson}") { backStackEntry ->
                        MainLayout(title = "New Song", navController = navController) {
                            val songJson = backStackEntry.arguments?.getString("songJson") ?: ""
                            val song = Gson().fromJson(songJson, Song::class.java)
                            SongRegisterScreen(modifier = Modifier, navController = navController, song = song ?: null)
                        }
                    }
                    composable("songinfo/{id}") { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id") ?: ""
                        MainLayout(title = "Song Info", navController = navController) {
                            SongInfoScreen(id = id, navController = navController)
                        }
                    }
                    composable("searchsong") {
                        MainLayout(title = "Search a Song", navController = navController) {
                            SearchSong(modifier = Modifier, navController = navController)
                        }
                    }
                    composable("favorites") {
                        MainLayout(title = "My Favorites", navController = navController) {
                            FavoritesScreen(modifier = Modifier, navController = navController)
                        }
                    }


                }
            }
        }
    }
}