package com.example.songhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color(0xFF040723)) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(modifier = Modifier.padding(innerPadding), onLoginClick = {
                                navController.navigate("main")
                            }, onRegisterClick = {
                                navController.navigate("register")
                            })
                        }
                        composable("register") {
                            RegisterScreen(modifier = Modifier.padding(innerPadding), onLoginClick = {
                                navController.navigate("login")
                            }, onRegisterSuccess = {
                                navController.navigate("login")
                            })
                        }
                        composable("main") {
                            MainScreen(modifier = Modifier.padding(innerPadding), navController = navController)
                        }
                        composable("userArea") {
                            UserAreaScreen(modifier = Modifier.padding(innerPadding), navController = navController)
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SonghubTheme {}
}