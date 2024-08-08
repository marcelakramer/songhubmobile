package com.example.songhub.ui.screens

import android.widget.EditText
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp, 50.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF060E43)),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = modifier
                .padding(25.dp, 0.dp)
                .fillMaxSize(),
            horizontalAlignment = AbsoluteAlignment.Left,
            verticalArrangement = Arrangement.SpaceBetween
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
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray
                )
            }
            Column {
                Spacer(modifier = Modifier.height(36.dp))
                OutlinedTextField(
                    value = username,
                    onValueChange = {username = it},
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = Color.White,

                        ),
                    placeholder = { Text("Username", color = Color(0xFF5A5A5A)) },
                    label = null,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = {email = it},
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = Color.White,

                        ),
                    placeholder = { Text("Email", color = Color(0xFF5A5A5A)) },
                    label = null,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = {password = it},
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = Color.White,

                        ),
                    placeholder = { Text("Password", color = Color(0xFF5A5A5A)) },
                    label = null,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {confirmPassword = it},
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = Color.White,

                        ),
                    placeholder = { Text("Confirm Password", color = Color(0xFF5A5A5A)) },
                    label = null,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(48.dp))
            }

            Column {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF040723)
                    )
                ) {
                    Text("Register", color = Color(0xFFFFFFFF))
                }
            }


        }
    }
}