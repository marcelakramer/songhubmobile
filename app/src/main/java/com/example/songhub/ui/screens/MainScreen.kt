package com.example.songhub.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.songhub.DAO.SongDAO
import com.example.songhub.R
import com.example.songhub.model.Song

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier, navController: NavController) {
    var items by remember { mutableStateOf<List<Song>>(emptyList()) }
    val songDAO = SongDAO()

    LaunchedEffect(Unit) {
        songDAO.findAll { fetchedSongs ->
            items = fetchedSongs
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            items(items) { item ->
                MusicCard(item, navController)
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
}

@Composable
fun MusicCard(item: MusicItem, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("songinfo") }
            .padding(vertical = 4.dp),
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
                onClick = { /* TODO */ },
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

data class MusicItem(
    val title: String,
    val duration: String,
    val artist: String,
    val cover: Int?
)
