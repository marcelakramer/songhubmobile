import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.songhub.R
import com.example.songhub.model.Track
import com.example.songhub.network.RetrofitInstance
import kotlinx.coroutines.launch
import android.util.Log
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontFamily
import com.example.songhub.DAO.UserDAO
import com.example.songhub.model.Song
import com.example.songhub.model.UserSession


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSong(modifier: Modifier = Modifier, navController: NavController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var items by remember { mutableStateOf<List<Track>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()
    var snackbarHostState = remember { SnackbarHostState() }

    val userId = UserSession.loggedInUser?.username ?: ""

    Column(
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Search a Song",
            color = Color.White,
            fontSize = 35.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W700,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(36.dp))

        OutlinedTextField(
            value = searchQuery.text,
            onValueChange = { query ->
                searchQuery = TextFieldValue(query)
                if (query.isNotEmpty()) {
                    coroutineScope.launch {
                        try {
                            val response = RetrofitInstance.api.searchTracks(
                                method = "track.search",
                                trackName = query,
                                apiKey = "499a9407d353802f5f07166c0d8f35c2",
                                format = "json"
                            )

                            items = response.results.trackmatches.track
                        } catch (e: Exception) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Error searching songs.")
                            }
                        }
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
            placeholder = { Text("Title, artist, album...", color = Color(0xFF5A5A5A)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(36.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(items) { track ->
                SearchMusicCard(track = track, navController = navController, userId = userId, snackbarHostState)
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
}

@Composable
fun SearchMusicCard(track: Track, navController: NavController, userId: String, snackbarHostState: SnackbarHostState) {
    val userDAO = UserDAO()
    var isInMySongs = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(track.url) {
        userDAO.isSongAdded(userId, track.url) { isAdded ->
            isInMySongs.value = isAdded
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {

            },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF040723)),
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = track.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = track.artist,
                    fontSize = 14.sp,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(
                onClick = {
                    if (isInMySongs.value) {
                        userDAO.removeFromMySongs(userId, track.url) { success ->
                            if (success) {
                                isInMySongs.value = false
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Song removed from library.")
                                }
                            } else {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Failed to remove song.")
                                }
                            }
                        }
                    } else {
                        userDAO.addToMySongs(userId, track.url) { success ->
                            if (success) {
                                isInMySongs.value = true
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Song added to library.")
                                }
                            } else {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Failed to add song.")
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(if (isInMySongs.value) R.drawable.close else R.drawable.add_icon),
                    contentDescription = "Add song",
                    tint = Color(if (isInMySongs.value) 0xFFF54D4D else 0xFF5EAF76),
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

fun Track.toSong(): Song {
    return Song(
        title = this.name,
        artist = this.artist,
        album = "",
        duration = "",
        year=""
    )
}