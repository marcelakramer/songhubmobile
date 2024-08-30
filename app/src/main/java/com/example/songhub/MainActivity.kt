package com.example.songhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.songhub.ui.layout.MainLayout
import com.example.songhub.ui.screens.LoginScreen
import com.example.songhub.ui.screens.MainScreen
import com.example.songhub.ui.screens.RegisterScreen
import com.example.songhub.ui.screens.UserAreaScreen
import com.example.songhub.ui.theme.SonghubTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            SonghubTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
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
                            MainScreen(
                                modifier = Modifier,
                                navController = navController
                            )
                        }
                    }
                    composable("userArea") {
                        MainLayout(title = "My profile", navController = navController) {
                            UserAreaScreen(
                                modifier = Modifier,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
