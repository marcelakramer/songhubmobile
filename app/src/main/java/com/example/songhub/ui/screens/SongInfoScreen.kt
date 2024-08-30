package com.example.songhub.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.example.songhub.DAO.SongDAO
import com.example.songhub.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongInfoScreen(
    id: String,
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val songDAO = remember {
        SongDAO()
    }


    val musicItem = remember {
        MusicItemMock(
            id = "",
            title = "Nome da Música",
            duration = "3:45",
            artist = "Nome do Artista",
            cover = null,
            album = "Nome do Álbum",
            year = "2024"
        )
    }

    LaunchedEffect(id) {
        songDAO.findById(id) {
            song -> musicItem
        }
    }

    val scrollState = rememberScrollState()

    IconButton(
        onClick = { navController.navigate("main") },
        modifier = Modifier.size(24.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.arrow_back),
            contentDescription = "Back Icon",
            tint = Color(0xFFFFFFFF)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 30.dp)
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.size(26.dp))
            Text(
                text = musicItem.title,
                fontSize = 28.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight(700),
                color = Color(0xFFFFFFFF),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.size(16.dp))

            IconButton(
                onClick = { /*TODO: Handle edit action*/ },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.edit),
                    contentDescription = "Edit Icon",
                    tint = Color(0xFFFFFFFF)
                )
            }
        }

        Box(
            modifier = Modifier
                .size(230.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(12.dp))
        ) {
            musicItem.cover?.let {
                Image(
                    painter = painterResource(it),
                    contentDescription = "Album Cover",
                    modifier = Modifier.fillMaxSize()
                )
            } ?: Icon(
                painter = painterResource(R.drawable.music),
                contentDescription = "Music Icon",
                tint = Color.White,
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Artist: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(musicItem.artist)
                    }
                },
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color(0xFFFFFFFF),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = buildAnnotatedString {
                    append("Album: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(musicItem.album)
                    }
                },
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color(0xFFFFFFFF),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = buildAnnotatedString {
                    append("Duration: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(musicItem.duration)
                    }
                },
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color(0xFFFFFFFF),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = buildAnnotatedString {
                    append("Year: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(musicItem.year)
                    }
                },
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color(0xFFFFFFFF),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { /*TODO: Add to favorites*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF9B3EFF)
                ),
                border = BorderStroke(1.dp, Color(0xFF9B3EFF)),
                shape = RoundedCornerShape(10.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.heart),
                    contentDescription = "Favorite Icon",
                    tint = Color(0xFF9B3EFF),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Favorite", color = Color(0xFF9B3EFF))
            }
            Button(
                onClick = { /*TODO: Delete song*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFFF54D4D)
                ),
                border = BorderStroke(1.dp, Color(0xFFF54D4D)),
                shape = RoundedCornerShape(10.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.delete),
                    contentDescription = "Delete Icon",
                    tint = Color(0xFFF54D4D),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Delete", color = Color(0xFFF54D4D))
            }
        }
    }
}

data class MusicItemMock(
    val id: String,
    val title: String,
    val duration: String,
    val artist: String,
    val album: String,
    val year: String,
    val cover: Int?
)
