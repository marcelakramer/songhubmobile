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
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.songhub.DAO.SongDAO
import com.example.songhub.DAO.UserDAO
import com.example.songhub.R
import com.example.songhub.model.Song
import com.example.songhub.model.User
import com.example.songhub.model.UserSession
import com.example.songhub.ui.viewmodel.SongViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var user = UserSession.loggedInUser
    var userDAO = UserDAO()
    var songDAO = SongDAO()
    var songs = remember { mutableStateOf<List<Song>>(emptyList()) }
    var snackbarHostState = remember { SnackbarHostState() }

    val songViewModel = koinViewModel<SongViewModel>()

    LaunchedEffect(Unit) {
        if (user != null) {
            songViewModel.getAllSongs { viewModelSongs ->
                userDAO.getMySongs(user.username) { mySongs ->
                    if (mySongs != null && mySongs.isNotEmpty()) {
                        songDAO.fetchTracksInfo(mySongs, "499a9407d353802f5f07166c0d8f35c2") { fetchedSongs ->
                            songs.value = viewModelSongs + fetchedSongs
                        }
                    } else {
                        println("No songs found for user: ${user.username}")
                        songs.value = viewModelSongs
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.TopCenter)
        ) {
            if (songs.value.isNotEmpty()) {
                items(songs.value) { item ->
                    if (user != null) {
                        MusicCard(item, navController, user, snackbarHostState)
                    }
                }
            } else {
                item {
                    Box(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.music_note_off),
                                contentDescription = "No songs",
                                tint = Color.White.copy(alpha = 0.5f),
                                modifier = Modifier.size(85.dp)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Your library is empty. Add or search a song.",
                                color = Color.White.copy(alpha = 0.5f),
                                fontSize = 20.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.W500,
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate("addSong") },
            modifier = Modifier
                .size(65.dp)
                .align(Alignment.BottomEnd),
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
fun MusicCard(item: Song, navController: NavController, user: User, snackbarHostState: SnackbarHostState) {
    var userDAO = UserDAO()
    val encodedUrl = Uri.encode(item.id)
    val coroutineScope = rememberCoroutineScope()

    val route = if (item.isLocal) item.id else encodedUrl

    var isFavorited = remember { mutableStateOf(false) }

    LaunchedEffect(item.id) {
        userDAO.isSongFavorited(user.username, item.id) { isFavored ->
            isFavorited.value = isFavored
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { navController.navigate("songinfo/$route") },
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

            if(!item.isLocal) {
                IconButton(
                    onClick = {
                        user?.let { currentUser ->
                            val trackUrl = item.id
                            if (isFavorited.value) {
                                userDAO.removeFromFavoriteSongs(currentUser.username, trackUrl) { success ->
                                    if (success) {
                                        isFavorited.value = false
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
                    val iconResource = if (isFavorited.value) {
                        R.drawable.heart_svgrepo_com
                    } else {
                        R.drawable.heart_outline
                    }
                    Icon(
                        painter = painterResource(iconResource),
                        contentDescription = "Favorite",
                        tint = Color(0xFF9B3EFF),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

        }
    }
}
