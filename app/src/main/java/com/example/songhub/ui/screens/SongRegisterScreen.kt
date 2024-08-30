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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.songhub.DAO.SongDAO
import com.example.songhub.R
import com.example.songhub.model.Song

val songDAO:SongDAO = SongDAO()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongRegisterScreen(modifier: Modifier = Modifier, navController: NavController) {
    var title by rememberSaveable { mutableStateOf("") }
    var artist by remember { mutableStateOf("") }
    var album by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri
    }

        Column(
            modifier = modifier
                .padding(25.dp, 15.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Column {
                Text(
                    "Add a Song",
                    color = Color.White,
                    fontSize = 35.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.W700
                )
            }
            Column {
                Spacer(modifier = Modifier.height(36.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = {title = it},
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,

                        ),
                    placeholder = { Text("Title", color = Color(0xFF5A5A5A)) },
                    label = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = artist,
                    onValueChange = {artist = it},
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,

                        ),
                    placeholder = { Text("Artist", color = Color(0xFF5A5A5A)) },
                    label = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = album,
                    onValueChange = {album = it},
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,

                        ),
                    placeholder = { Text("Album", color = Color(0xFF5A5A5A)) },
                    label = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = duration,
                    onValueChange = {duration = it},
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,

                        ),
                    placeholder = { Text("Duration", color = Color(0xFF5A5A5A)) },
                    label = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = year,
                    onValueChange = {year = it},
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,

                        ),
                    placeholder = { Text("Year", color = Color(0xFF5A5A5A)) },
                    label = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row (modifier= Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                    Icon(
                        painter = painterResource(R.drawable.upload),
                        contentDescription = "Upload cover",
                        tint = Color.White,
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        modifier = Modifier.clickable { imagePickerLauncher.launch("image/*") },
                        text = "Upload cover",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFFFFFFF),
                        fontWeight = FontWeight.W400,
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
            Column {
                Button(
                    onClick = {

                        if (title.isNotEmpty() && artist.isNotEmpty() && album.isNotEmpty() && duration.isNotEmpty() && year.isNotEmpty()) {
                            if (selectedImageUri != null) {
                                songDAO.uploadImage(selectedImageUri!!) { url ->
                                    if (url != null) {
                                        val newSong = Song(
                                            title = title,
                                            artist = artist,
                                            album = album,
                                            duration = duration,
                                            year = year,
                                            imageUrl = url
                                        )
                                        songDAO.add(newSong) { success ->
                                            if (success) {
                                                // Handle successful addition, e.g., show a message or navigate away
                                            } else {
                                                // Handle failure, e.g., show an error message
                                            }
                                        }
                                    } else {
                                        // Handle image upload failure
                                    }
                                }
                            } else {
                                val newSong = Song(
                                    title = title,
                                    artist = artist,
                                    album = album,
                                    duration = duration,
                                    year = year,
                                    imageUrl = null // Handle case where no image is uploaded
                                )
                                songDAO.add(newSong) { success ->
                                    if (success) {
                                        // Handle successful addition
                                    } else {
                                        // Handle failure
                                    }
                                }
                            }
                        }
                        navController.navigate("main")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF040723)
                    )
                ) {
                    Text("Add", color = Color(0xFFFFFFFF), fontSize = 20.sp, fontWeight = FontWeight.W400, fontFamily = FontFamily.SansSerif)
                }
            }


        }
    }
