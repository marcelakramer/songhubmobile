package com.example.songhub.ui.screens

import android.net.Uri
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
import com.example.songhub.model.Song
import coil.compose.rememberImagePainter
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongInfoScreen(
    id: String,
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val songDAO = remember { SongDAO() }
    var song by remember { mutableStateOf<Song?>(null) }

    LaunchedEffect(id) {
        songDAO.findById(id) { fetchedSong ->
            song = fetchedSong
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
            song?.title?.let {
                Text(
                    text = it,
                    fontSize = 28.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight(700),
                    color = Color(0xFFFFFFFF),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Spacer(modifier = Modifier.size(16.dp))

            IconButton(
                onClick = {
                    val songJson = Uri.encode(Gson().toJson(song))
                    navController.navigate("addSong?songJson=$songJson")  },
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
            song?.imageUrl?.let {
                Image(
                    painter = rememberImagePainter(it),
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
                        append(song?.artist)
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
                        append(song?.album)
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
                        append(song?.duration)
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
                        append(song?.year)
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
            OutlinedButton(
                onClick = { },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF9B3EFF)
                ),
                border = BorderStroke(1.dp, Color(0xFF9B3EFF)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .width(130.dp)
                    .height(45.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.heart),
                    contentDescription = "Favorite Icon",
                    tint = Color(0xFF9B3EFF),
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 9.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Favorite", color = Color(0xFF9B3EFF))
            }

            OutlinedButton(
                onClick = {
                    song?.let {
                        songDAO.delete(it) { success ->
                            if (success) {
                                navController.navigate("main")
                            } else {
                                // Handle deletion failure
                            }
                        }
                    }
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFFF54D4D)
                ),
                border = BorderStroke(1.dp, Color(0xFFF54D4D)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .width(130.dp)
                    .height(45.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.delete),
                    contentDescription = "Delete Icon",
                    tint = Color(0xFFF54D4D),
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 9.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Delete", color = Color(0xFFF54D4D))
                }
            }
    }
}
