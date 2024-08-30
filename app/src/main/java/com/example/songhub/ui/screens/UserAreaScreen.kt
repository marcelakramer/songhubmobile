package com.example.songhub.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.songhub.DAO.UserDAO
import com.example.songhub.R
import com.example.songhub.model.UserSession

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAreaScreen(modifier: Modifier = Modifier, navController: NavController,) {
    val user = UserSession.loggedInUser

    if (user != null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = Color(0xFF060E43)),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.profile_user_default),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }

            Text(
                text = "User Profile",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFFFFF),
                fontFamily = FontFamily.SansSerif
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1A1D3A),
                    contentColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Username: ",
                            fontSize = 18.sp,
                            color = Color(0xFFFFFFFF),
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = user.username,
                            fontSize = 18.sp,
                            color = Color(0xFFFFFFFF),
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Email: ",
                            fontSize = 18.sp,
                            color = Color(0xFFFFFFFF),
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = user.email,
                            fontSize = 18.sp,
                            color = Color(0xFFFFFFFF),
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    UserDAO().logout()
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5468FF),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Logout", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No user logged in", fontSize = 20.sp, color = Color.Red)
        }
    }
}