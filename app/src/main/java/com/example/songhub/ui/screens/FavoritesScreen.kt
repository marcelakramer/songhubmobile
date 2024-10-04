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
    var snackbarHostState = remember { SnackbarHostState() }

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
                    FavoritesMusicCard(item, navController, user, snackbarHostState)
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

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.fillMaxWidth(),
        snackbar = { data ->
            Snackbar(
                snackbarData = data,
                shape = RoundedCornerShape(8.dp),
                containerColor = Color(0xFF212EC0),
                contentColor = Color.White,
            )
        }
    )

}

@Composable
fun FavoritesMusicCard(item: Song, navController: NavController, user: User, snackbarHostState: SnackbarHostState) {
    var userDAO = UserDAO()
    val encodedUrl = Uri.encode(item.id)
    val coroutineScope = rememberCoroutineScope()

    var isFavorited = remember { mutableStateOf(true) }

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
                        if (isFavorited.value) {
                            userDAO.removeFromFavoriteSongs(currentUser.username, trackUrl) { success ->
                                if (success) {
                                    isFavorited.value = false
                                    navController.navigate("favorites")
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Song removed from Favorites.")
                                    }
                                }
                            }
                        } else {
                            userDAO.addToFavoriteSongs(currentUser.username, trackUrl) { success ->
                                if (success) {
                                    isFavorited.value = true
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Song added to Favorites.")
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
                val iconResource = R.drawable.heart_off_outline

                Icon(
                    painter = painterResource(iconResource),
                    contentDescription = "Favorite",
                    tint = Color(0xFFF54D4D),
                    modifier = Modifier.size(22.dp)
                )
            }

        }
    }
}
