package com.example.songhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
<<<<<<< HEAD
import com.example.songhub.ui.screens.LoginScreen
=======
import com.example.songhub.ui.screens.RegisterScreen
>>>>>>> d809a31 (refactor: making changes requested in the review)
import com.example.songhub.ui.theme.SonghubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SonghubTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color(0xFF040723)) { innerPadding ->
<<<<<<< HEAD
                    LoginScreen(
=======
                    RegisterScreen(
>>>>>>> d809a31 (refactor: making changes requested in the review)
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SonghubTheme {
    }
}