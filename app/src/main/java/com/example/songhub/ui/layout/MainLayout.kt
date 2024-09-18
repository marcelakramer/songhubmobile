package com.example.songhub.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun MainLayout(title: String, navController: NavController, content: @Composable () -> Unit) {
    val expanded = remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color(0xFF040723),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        fontSize = 23.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Top)
                    ) {
                        IconButton(onClick = { expanded.value = !expanded.value }) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Profile",
                                modifier = Modifier.size(30.dp),
                                tint = Color.White
                            )
                        }
                        Box(
                            modifier = Modifier
                                .offset(x = (26).dp, y = (26).dp)
                        ) {
                            DropdownMenu(
                                expanded = expanded.value,
                                onDismissRequest = { expanded.value = false },
                                modifier = Modifier
                                    .background(Color(0xFF060E43))
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = "Profile",
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.profile),
                                            contentDescription = "Profile",
                                            tint = Color.White
                                        )
                                    },
                                    onClick = {
                                        expanded.value = false
                                        navController.navigate("profile")
                                    }
                                )
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = "Logout",
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.logout),
                                            contentDescription = "Logout",
                                            tint = Color.White
                                        )
                                    },
                                    onClick = {
                                        expanded.value = false
                                        UserDAO().logout()
                                        navController.navigate("login") {
                                            popUpTo("main") { inclusive = true }
                                        }
                                    }
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF040723))
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(80.dp),
                containerColor = Color(0xFF040723),
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.navigate("main") }) {
                            Icon(
                                painter = painterResource(id = R.drawable.home),
                                contentDescription = "Home",
                                modifier = Modifier.size(30.dp),
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { navController.navigate("addSong") }) {
                            Icon(
                                painter = painterResource(id = R.drawable.add_song),
                                contentDescription = "Add Song",
                                modifier = Modifier.size(30.dp),
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.favorites),
                                contentDescription = "Favorites",
                                modifier = Modifier.size(30.dp),
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { navController.navigate("searchsong") }) {
                            Icon(
                                painter = painterResource(id = R.drawable.search),
                                contentDescription = "Search online",
                                modifier = Modifier.size(30.dp),
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(10.dp, 5.dp)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(Color(0xFF060E43)),
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    content()
                }
            }
        }
    }
}