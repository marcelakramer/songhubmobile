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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.SolidColor
import com.example.songhub.DAO.UserDAO
import com.example.songhub.model.Song
import com.example.songhub.model.UserSession


@Composable
fun SearchSong(modifier: Modifier = Modifier, navController: NavController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var items by remember { mutableStateOf<List<Track>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    val userId = UserSession.loggedInUser?.username ?: "" // Adjust based on how you store user ID

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(color = Color(0xFF1F1B2E), shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            if (searchQuery.text.isEmpty()) {
                Text(
                    text = "Search for songs...",
                    color = Color.LightGray,
                    fontSize = 16.sp
                )
            }
            BasicTextField(
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query
                    if (query.text.isNotEmpty()) {
                        coroutineScope.launch {
                            try {
                                val response = RetrofitInstance.api.searchTracks(
                                    method = "track.search",
                                    trackName = query.text,
                                    apiKey = "499a9407d353802f5f07166c0d8f35c2", // Replace with your API key
                                    format = "json"
                                )

                                items = response.results.trackmatches.track
                            } catch (e: Exception) {
                                Log.e("SearchSong", "Error fetching tracks: $e")
                            }
                        }
                    }
                },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp
                ),
                cursorBrush = SolidColor(Color.White),
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Display search results
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(items) { track ->
                SearchMusicCard(track = track, navController = navController, userId = userId)
            }
        }
    }
}
@Composable
fun SearchMusicCard(track: Track, navController: NavController, userId: String) {
    val userDAO = UserDAO() // Initialize the UserDAO
    var isInMySongs by remember { mutableStateOf(false) } // Track if the song is favorited

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                // Handle navigation to details if needed
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
                    userDAO.addToMySongs(userId, track.url) { success ->
                        if (success) {
                            isInMySongs = !isInMySongs
                        }
                    }
                },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(if (isInMySongs) R.drawable.add_icon else R.drawable.add_icon),
                    contentDescription = "Add song",
                    tint = Color(0xFF9B3EFF),
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

// Extension function to convert Track to Song
fun Track.toSong(): Song {
    return Song(
        id = "", // Provide an appropriate ID if necessary
        title = this.name,
        artist = this.artist,
        album = "", // Provide album if available
        duration = "", // Provide duration if available
        year = "" // Provide year if available
    )
}
