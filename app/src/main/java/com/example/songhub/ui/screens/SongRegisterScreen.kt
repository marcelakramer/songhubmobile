package com.example.songhub.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.songhub.DAO.SongDAO
import com.example.songhub.R
import com.example.songhub.model.Song
import com.example.songhub.ui.viewmodel.SongViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.UUID

val songDAO:SongDAO = SongDAO()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongRegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    song: Song? = null
) {
    val viewModel = koinViewModel<SongViewModel>()

    var title by rememberSaveable { mutableStateOf(song?.title ?: "") }
    var artist by rememberSaveable { mutableStateOf(song?.artist ?: "") }
    var album by rememberSaveable { mutableStateOf(song?.album ?: "") }
    var duration by rememberSaveable { mutableStateOf(song?.duration ?: "") }
    var year by rememberSaveable { mutableStateOf(song?.year ?: "") }


    val areFieldsFilled = title.isNotEmpty() && artist.isNotEmpty() && album.isNotEmpty() &&
            duration.isNotEmpty() && year.isNotEmpty()

    Column(
        modifier = modifier
            .padding(25.dp, 15.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            if (song != null) "Edit Song" else "Add Song",
            color = Color.White,
            fontSize = 35.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W700
        )

        Spacer(modifier = Modifier.height(36.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                containerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
            ),
            placeholder = { Text("Title", color = Color(0xFF5A5A5A)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = artist,
            onValueChange = { artist = it },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                containerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
            ),
            placeholder = { Text("Artist", color = Color(0xFF5A5A5A)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = album,
            onValueChange = { album = it },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                containerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
            ),
            placeholder = { Text("Album", color = Color(0xFF5A5A5A)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = duration,
            onValueChange = { duration = it },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                containerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
            ),
            placeholder = { Text("Duration", color = Color(0xFF5A5A5A)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = year,
            onValueChange = { year = it },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                containerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
            ),
            placeholder = { Text("Year", color = Color(0xFF5A5A5A)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                if (areFieldsFilled) {
                    val songToSave = song?.copy(
                        id= UUID.randomUUID().toString(),
                        title = title,
                        artist = artist,
                        album = album,
                        duration = duration,
                        year = year,
                        isLocal = true
                    ) ?: Song(
                        id= UUID.randomUUID().toString(),
                        title = title,
                        artist = artist,
                        album = album,
                        duration = duration,
                        year = year,
                        isLocal = true
                    )

                    saveOrUpdateSong(viewModel, song, songToSave, navController)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF040723)
            ),
            enabled = areFieldsFilled
        ) {
            Text(
                if (song != null) "Save" else "Add",
                color = Color(0xFFFFFFFF),
                fontSize = 20.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}

private fun saveOrUpdateSong(
    viewModel: SongViewModel,
    existingSong: Song?,
    songToSave: Song,
    navController: NavController
) {
    if (existingSong != null) {
        viewModel.updateSong(songToSave) { success ->
            if (success) {
                navController.navigate("main")
            }
        }
    } else {
        viewModel.addSong(songToSave) { success ->
            if (success) {
                navController.navigate("main")
            }
        }
    }
}

