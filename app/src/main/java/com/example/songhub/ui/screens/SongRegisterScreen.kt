package com.example.songhub.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.songhub.DAO.SongDAO
import com.example.songhub.model.Song
import com.example.songhub.ui.viewmodel.SongViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar
import java.util.UUID

val songDAO: SongDAO = SongDAO()

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

    val validationErrors = remember { mutableStateOf(SongValidationErrors()) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .padding(25.dp, 15.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = if (song != null) "Edit Song" else "Add Song",
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
                unfocusedTextColor = Color.Black
            ),
            placeholder = { Text("Title", color = Color(0xFF5A5A5A)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )
        Text(
            text = validationErrors.value.titleError ?: "",
            color = Color.Red,
            fontSize = 13.sp,
            modifier = Modifier.padding(start = 8.dp)
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
                unfocusedTextColor = Color.Black
            ),
            placeholder = { Text("Artist", color = Color(0xFF5A5A5A)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )
        Text(
            text = validationErrors.value.artistError ?: "",
            color = Color.Red,
            fontSize = 13.sp,
            modifier = Modifier.padding(start = 8.dp)
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
                unfocusedTextColor = Color.Black
            ),
            placeholder = { Text("Album", color = Color(0xFF5A5A5A)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )
        Text(
            text = validationErrors.value.albumError ?: "",
            color = Color.Red,
            fontSize = 13.sp,
            modifier = Modifier.padding(start = 8.dp)
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
                unfocusedTextColor = Color.Black
            ),
            placeholder = { Text("Duration", color = Color(0xFF5A5A5A)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )
        Text(
            text = validationErrors.value.durationError ?: "",
            color = Color.Red,
            fontSize = 13.sp,
            modifier = Modifier.padding(start = 8.dp)
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
                unfocusedTextColor = Color.Black
            ),
            placeholder = { Text("Year", color = Color(0xFF5A5A5A)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )
        Text(
            text = validationErrors.value.yearError ?: "",
            color = Color.Red,
            fontSize = 13.sp,
            modifier = Modifier.padding(start = 8.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                if (validateFields(title, artist, album, duration, year, validationErrors)) {
                    val songToSave = song?.copy(
                        id = song.id,
                        title = title,
                        artist = artist,
                        album = album,
                        duration = duration,
                        year = year,
                        isLocal = true
                    ) ?: Song(
                        id = UUID.randomUUID().toString(),
                        title = title,
                        artist = artist,
                        album = album,
                        duration = duration,
                        year = year,
                        isLocal = true
                    )

                    saveOrUpdateSong(viewModel, song, songToSave, navController, coroutineScope, snackbarHostState)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF040723)
            )
        ) {
            Text(
                text = if (song != null) "Save" else "Add",
                color = Color(0xFFFFFFFF),
                fontSize = 20.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily.SansSerif
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

// Objeto de dados para erros de validação
data class SongValidationErrors(
    var titleError: String? = null,
    var artistError: String? = null,
    var albumError: String? = null,
    var durationError: String? = null,
    var yearError: String? = null
)

// Funções de validação (com lógica mais complexa)
private fun validateTitle(title: String): String? {
    if (title.isBlank()) {
        return "Title is required"
    } else if (title.length < 3) {
        return "Title must be at least 3 characters long"
    }
    return null
}

private fun validateArtist(artist: String): String? {
    if (artist.isBlank()) {
        return "Artist is required"
    } else if (artist.length < 2) {
        return "Artist name must be at least 2 characters long"
    }
    return null
}

private fun validateAlbum(album: String): String? {
    if (album.isBlank()) {
        return "Album is required"
    } else if (album.length < 2) {
        return "Album title must be at least 2 characters long"
    }
    return null
}

private fun validateDuration(duration: String): String? {
    if (duration.isBlank()) {
        return "Duration is required"
    }
    val durationRegex = Regex("^(\\d{1,2}):(\\d{2})(:(\\d{2}))?\$")
    if (!durationRegex.matches(duration)) {
        return "Duration must be in the format mm:ss or hh:mm:ss"
    }
    return null
}

private fun validateYear(year: String): String? {
    if (year.isBlank()) {
        return "Year is required"
    }
    if (year.length != 4 || !year.all { it.isDigit() }) {
        return "Year must be a 4-digit number"
    }
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val yearInt = year.toIntOrNull() ?: 0
    if (yearInt < 1900 || yearInt > currentYear) {
        return "Year must be between 1900 and $currentYear"
    }
    return null
}

// Função para validar todos os campos
private fun validateFields(
    title: String,
    artist: String,
    album: String,
    duration: String,
    year: String,
    validationErrors: MutableState<SongValidationErrors>
): Boolean {
    var isValid = true

    validationErrors.value = SongValidationErrors(
        titleError = validateTitle(title),
        artistError = validateArtist(artist),
        albumError = validateAlbum(album),
        durationError = validateDuration(duration),
        yearError = validateYear(year)
    )

    isValid = validationErrors.value.titleError == null &&
            validationErrors.value.artistError == null &&
            validationErrors.value.albumError == null &&
            validationErrors.value.durationError == null &&
            validationErrors.value.yearError == null

    return isValid
}

private fun saveOrUpdateSong(
    viewModel: SongViewModel,
    existingSong: Song?,
    songToSave: Song,
    navController: NavController,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    if (existingSong != null) {
        viewModel.updateSong(songToSave) { success ->
            if (success) {
                navController.navigate("main") // Navega somente se a atualização for bem-sucedida
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Song info updated.")
                }
            } else {
                // Lidar com a falha na atualização (ex: exibir Snackbar de erro)
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Failed to update song.")
                }
            }
        }
    } else {
        viewModel.addSong(songToSave) { success ->
            if (success) {
                navController.navigate("main")
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Song added to library.")
                }
            }
        }
    }
}
