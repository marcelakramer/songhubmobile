package com.example.songhub.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier, navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    Scaffold(
        containerColor = Color(0xFF040723),
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(8.dp, 4.dp),
                title = {
                    Text(
                        text = "Home",
                        fontSize = 23.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight(700),
                        color = Color(0xFFFFFFFF)
                    )
                },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            modifier = Modifier.size(30.dp),
                            tint = Color(0xFFFFFFFF)
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Account") },
                            onClick = {
                                expanded = false
                                navController.navigate("userArea")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Logout") },
                            onClick = {
                                expanded = false
                                UserDAO().logout()
                                navController.navigate("login") {
                                    popUpTo("main") { inclusive = true }
                                }
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF040723))
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(90.dp),
                containerColor = Color(0xFF040723),
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(R.drawable.home),
                                contentDescription = "Home",
                                modifier = Modifier.size(30.dp),
                                tint = Color(0xFFFFFFFF)
                            )
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(R.drawable.add_song),
                                contentDescription = "Add Song",
                                modifier = Modifier.size(30.dp),
                                tint = Color(0xFFFFFFFF)
                            )
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(R.drawable.favorites),
                                contentDescription = "Favorites",
                                modifier = Modifier.size(30.dp),
                                tint = Color(0xFFFFFFFF)
                            )
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(R.drawable.search),
                                contentDescription = "Search online",
                                modifier = Modifier.size(30.dp),
                                tint = Color(0xFFFFFFFF)
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp, 10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF060E43)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 70.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Welcome to",
                        fontSize = 24.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight(300),
                        color = Color(0xFFFFFFFF)
                    )
                    Text(
                        text = "songhub",
                        fontSize = 42.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight(700),
                        color = Color(0xFFFFFFFF)
                    )
                    Spacer(modifier = Modifier.height(80.dp))
                    Image(
                        modifier = Modifier.size(220.dp),
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "Logo"
                    )
                }
            }
        }
    }
}
