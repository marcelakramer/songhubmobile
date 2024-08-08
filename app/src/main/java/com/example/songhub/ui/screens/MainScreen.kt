package com.example.songhub.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import com.example.songhub.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Scaffold(
        containerColor = Color(0xFF040723),
        topBar = {
            TopAppBar(
                modifier = modifier.padding(8.dp, 4.dp),
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
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            modifier = modifier.size(30.dp),
                            tint = Color(0xFFFFFFFF)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF040723))
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = modifier.height(90.dp),
                containerColor = Color(0xFF040723),
                content = {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(R.drawable.home),
                                contentDescription = "Home",
                                modifier = modifier.size(30.dp),
                                tint = Color(0xFFFFFFFF)
                            )
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(R.drawable.add_song),
                                contentDescription = "Add Song",
                                modifier = modifier.size(30.dp),
                                tint = Color(0xFFFFFFFF)
                            )
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(R.drawable.favorites),
                                contentDescription = "Favorites",
                                modifier = modifier.size(30.dp),
                                tint = Color(0xFFFFFFFF)
                            )
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(R.drawable.search),
                                contentDescription = "Search online",
                                modifier = modifier.size(30.dp),
                                tint = Color(0xFFFFFFFF)
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = modifier
                    .fillMaxSize()
                    .padding(20.dp, 10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF060E43)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = modifier
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
                    Spacer(modifier = modifier.height(80.dp))
                    Image(
                        modifier = modifier.size(220.dp),
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "Logo"
                    )
                }
            }
        }
    }
}
