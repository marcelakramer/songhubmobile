package com.example.songhub.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.songhub.DAO.UserDAO
import com.example.songhub.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(modifier: Modifier = Modifier, onLoginClick: () -> Unit, onRegisterSuccess: () -> Unit) {
    var username by rememberSaveable { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var revealPassword by remember { mutableStateOf(false)}
    var revealConfirmPassword by remember { mutableStateOf(false)}
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val userDAO = UserDAO()

    fun areFieldsFilled(): Boolean {
        return username.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF040723))
    ) {
        Card(
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp, 100.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF060E43)),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = modifier
                    .padding(25.dp, 20.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Column {
                    Text(
                        "Register",
                        color = Color.White,
                        fontSize = 48.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.W700
                    )
                    Text(
                        text = buildAnnotatedString {
                            append("Create an account at ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("songhub")
                            }
                        },
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFFFFFFF)
                    )
                }
                Spacer(modifier = Modifier.height(36.dp))
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                    ),
                    placeholder = { Text("Username", color = Color(0xFF5A5A5A)) },
                    label = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                    ),
                    placeholder = { Text("Email", color = Color(0xFF5A5A5A)) },
                    label = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    trailingIcon = {
                        if (revealPassword) {
                            IconButton(onClick = { revealPassword = false }) {
                                Icon(painter = painterResource(R.drawable.eye), contentDescription = null)
                            }
                        } else {
                            IconButton(onClick = { revealPassword = true }) {
                                Icon(painter = painterResource(R.drawable.eye_off), contentDescription = null)
                            }
                        }
                    },
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                    ),
                    placeholder = { Text("Password", color = Color(0xFF5A5A5A)) },
                    label = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    visualTransformation = if (revealPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    trailingIcon = {
                        if (revealConfirmPassword) {
                            IconButton(onClick = { revealConfirmPassword = false }) {
                                Icon(painter = painterResource(R.drawable.eye), contentDescription = null)
                            }
                        } else {
                            IconButton(onClick = { revealConfirmPassword = true }) {
                                Icon(painter = painterResource(R.drawable.eye_off), contentDescription = null)
                            }
                        }
                    },
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                    ),
                    placeholder = { Text("Confirm Password", color = Color(0xFF5A5A5A)) },
                    label = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    visualTransformation = if (revealConfirmPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(
                        modifier = Modifier.clickable { onLoginClick() },
                        text = buildAnnotatedString {
                            append("Already have an account? ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Login")
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFFFFFFF),
                        fontWeight = FontWeight.W400,
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Column {
                    errorMessage?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W400,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .align(Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center
                        )
                    }
                    Button(
                        onClick = {
                            if (password == confirmPassword) {
                                userDAO.registerUser(username, password, email) { success, message ->
                                    if (success) {
                                        userDAO.login(username, password) { loginSuccess ->
                                            if (loginSuccess) {
                                                onRegisterSuccess()
                                            } else {
                                                Log.e("RegisterScreen", "Failed to login after registration.")
                                            }
                                        }
                                    } else {
                                        username = ""
                                        email = ""
                                        password = ""
                                        confirmPassword = ""
                                        errorMessage = when (message) {
                                            "Email already exists" -> "Email already exists. Try another email."
                                            "Username already exists" -> "Username already exists. Try another username."
                                            else -> "Registration failed. Try again later."
                                        }
                                        Log.e("RegisterScreen", message)
                                    }
                                }
                            } else {
                                password = ""
                                confirmPassword = ""
                                errorMessage = "Passwords do not match."
                                Log.e("RegisterScreen", "Passwords do not match.")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF040723)
                        ),
                        enabled = areFieldsFilled()
                    ) {
                        Text(
                            "Register",
                            color = Color(0xFFFFFFFF),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W400,
                            fontFamily = FontFamily.SansSerif
                        )
                    }
                }
            }
        }
    }
}
