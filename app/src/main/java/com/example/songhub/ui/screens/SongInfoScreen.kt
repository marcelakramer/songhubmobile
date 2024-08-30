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
import androidx.navigation.NavController
import com.example.songhub.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongInfoScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val musicItem = remember {
        MusicItemMock(
            title = "Nome da Música",
            duration = "3:45",
            artist = "Nome do Artista",
            cover = R.drawable.cover,
            album = "Nome do Álbum",
            year = "2024"
        )
    }
    val scrollState = rememberScrollState()

    IconButton(
        onClick = { navController.navigate("userArea") }, // Navega para a tela "userArea"
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
            Text(
                text = musicItem.title,
                fontSize = 26.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight(700),
                color = Color(0xFFFFFFFF),
                modifier = Modifier.align(Alignment.CenterVertically)
            )

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

        musicItem.cover?.let {
            Image(
                painter = painterResource(it),
                contentDescription = "Album/Music Image",
                modifier = Modifier
                    .size(250.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.Start),
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
                fontWeight = FontWeight(500),
                color = Color(0xFFFFFFFF),
                modifier = Modifier.padding(start = 30.dp)
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
                fontWeight = FontWeight(500),
                color = Color(0xFFFFFFFF),
                modifier = Modifier.padding(start = 30.dp)
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
                fontWeight = FontWeight(500),
                color = Color(0xFFFFFFFF),
                modifier = Modifier.padding(start = 30.dp)
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
                fontWeight = FontWeight(500),
                color = Color(0xFFFFFFFF),
                modifier = Modifier.padding(start = 30.dp)
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
                shape = RoundedCornerShape(8.dp)
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
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.close),
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
    val title: String,
    val duration: String,
    val artist: String,
    val album: String,
    val year: String,
    val cover: Int?
)
