package com.example.songhub.ui.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.songhub.DAO.SongDAO
import com.example.songhub.DAO.UserDAO
import com.example.songhub.R
import com.example.songhub.model.Song
import com.example.songhub.model.User
import com.example.songhub.model.UserSession
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(modifier: Modifier = Modifier, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var user = UserSession.loggedInUser
    var userDAO = UserDAO()
    var songDAO = SongDAO()
    var songs = remember { mutableStateOf<List<Song>>(emptyList()) }

    LaunchedEffect(Unit) {
        if (user != null) {
            userDAO.getMyFavoriteSongs(user.username) { mySongs ->
                if (mySongs != null && mySongs.isNotEmpty()) {
                    songDAO.fetchTracksInfo(mySongs, "499a9407d353802f5f07166c0d8f35c2") { fetchedSongs ->
                        songs.value = fetchedSongs
                    }
                } else {
                    println("No songs found for user: ${user.username}")
                }
            }
        }
    }


    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter)
        ) {
            items(songs.value) { item ->
                if (user != null) {
                    FavoritesMusicCard(item, navController, user)
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate("addSong") },
            modifier = Modifier.size(65.dp).align(Alignment.BottomEnd),
            containerColor = Color(0xFFAAA1FF)
        ) {
            Icon(
                painter = painterResource(R.drawable.add_song),
                contentDescription = "Add Song",
                tint = Color(0xFF040723),
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun FavoritesMusicCard(item: Song, navController: NavController, user: User) {
    var userDAO = UserDAO()
    val encodedUrl = Uri.encode(item.id)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { navController.navigate("songinfo/$encodedUrl") },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF040723)),
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(5.dp))
            ) {
                if (item.imageUrl != null) {
                    Image(
                        painter = rememberImagePainter(item.imageUrl),
                        contentDescription = "Song Cover",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(5.dp))
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.music),
                        contentDescription = "Default Music Icon",
                        tint = Color.White,
                        modifier = Modifier
                            .size(45.dp)
                            .align(Alignment.Center)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = item.duration,
                    fontSize = 9.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.artist,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Thin,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    user?.let { currentUser ->
                        val trackUrl = item.id

                        userDAO.isSongFavorited(currentUser.username, trackUrl) { isFavorited ->
                            if (isFavorited) {
                                userDAO.removeFromFavoriteSongs(
                                    currentUser.username,
                                    trackUrl
                                ) { removeSuccess ->
                                    if (removeSuccess) {
                                        println("Song removed from favorites")
                                    } else {
                                        println("Failed to remove song from favorites")
                                    }
                                }
                            } else {
                                userDAO.addToFavoriteSongs(
                                    currentUser.username,
                                    trackUrl
                                ) { addSuccess ->
                                    if (addSuccess) {
                                        println("Song added to favorites")
                                    } else {
                                        println("Failed to add song to favorites")
                                    }
                                }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .size(40.dp)
                    .padding(0.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.heart_outline),
                    contentDescription = "Favorite",
                    tint = Color(0xFF9B3EFF),
                    modifier = Modifier.size(22.dp)
                )
            }

        }
    }
}
