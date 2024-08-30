package com.example.songhub.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.songhub.R
import com.example.songhub.model.User
import com.example.songhub.DAO.UserDAO
import com.example.songhub.model.UserSession

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val userDAO = UserDAO()
    val user = UserSession.loggedInUser

    if (user != null) {
        val username = remember { mutableStateOf(user.username) }
        val email = remember { mutableStateOf(user.email) }
        val password = remember { mutableStateOf(user.password) }

        Column(
            modifier = modifier
                .wrapContentSize()
                .background(Color(0xFF060e43))
                .padding(horizontal = 18.dp, vertical = 0.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(28.dp))
            Card(
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 4.dp),
                shape = RoundedCornerShape(100),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFd9d9d9)),
            ) {
                Icon(
                    painter = painterResource(R.drawable.camera_off_outline),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                    tint = Color(0xFFc2c2c2)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_upload),
                    contentDescription = "Upload Icon",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Upload photo",
                    color = Color.White,
                    fontSize = 16.sp,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
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
                value = email.value,
                onValueChange = { email.value = it },
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
                    .height(55.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
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
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    onClick = { navController.navigate("main") },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFFEC5766)
                    ),
                    border = BorderStroke(1.dp, Color(0xFFEC5766)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .width(130.dp)
                        .height(45.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cancel Icon",
                        tint = Color(0xFFEC5766),
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 9.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Cancel", color = Color(0xFFEC5766))
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(
                    onClick = {
                        userDAO.updateUser(
                            oldEmail = user.email,
                            newUsername = username.value,
                            newPassword = password.value,
                            newEmail = email.value
                        ) { success, message ->
                            if (success) {
                                navController.navigate("main")
                            } else {
                                /* colocar print pra dizer que deu erro */
                            }
                        }
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFF16A085)
                    ),
                    border = BorderStroke(1.dp, Color(0xFF16A085)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .width(130.dp)
                        .height(45.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save Icon",
                        tint = Color(0xFF16A085),
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 9.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Save", color = Color(0xFF16A085))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No user logged in", fontSize = 36.sp, color = Color.Red)
        }
    }
}
