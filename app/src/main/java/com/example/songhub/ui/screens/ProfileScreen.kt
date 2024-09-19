package com.example.songhub.ui.screens

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.songhub.R
import com.example.songhub.model.User
import com.example.songhub.DAO.UserDAO
import com.example.songhub.model.UserSession
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.output.ByteArrayOutputStream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val context = LocalContext.current

    val cameraPermissionGranted = remember { mutableStateOf(false) }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        cameraPermissionGranted.value = isGranted
    }

    val userDAO = UserDAO()
    val user = UserSession.loggedInUser
    val imageBitmap = remember { mutableStateOf<Bitmap?>(null) }

    var showDialog by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            loadBitmapFromUri(context, it) { bitmap ->
                imageBitmap.value = bitmap
            }
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        imageBitmap.value = bitmap
    }

    if (user != null) {
        val username = remember { mutableStateOf(user.username) }
        val email = remember { mutableStateOf(user.email) }
        val password = remember { mutableStateOf(user.password) }

        Column(
            modifier = modifier
                .wrapContentSize()
                .background(Color(0xFF060e43))
                .padding(horizontal = 18.dp, vertical = 0.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(28.dp))
            Card(
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 4.dp),
                shape = RoundedCornerShape(100),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFd9d9d9)),
            ) {
                Log.d("isser", user.toString())
                user.imageUrl?.let { Log.e("erro", it) }
//                imageBitmap.value?.let { bitmap ->
//                    Image(
//                        bitmap = bitmap.asImageBitmap(),
//                        contentDescription = "Profile Picture",
//                        modifier = Modifier.fillMaxSize()
//                    )
//                } ?: run {
//                    Icon(
//                        painter = painterResource(R.drawable.camera_off_outline),
//                        contentDescription = "Profile Picture",
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(30.dp),
//                        tint = Color(0xFFc2c2c2)
//                    )
//                }
                Image(
                    painter = rememberImagePainter(user.imageUrl),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(5.dp))
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.clickable {
                    showDialog = true
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_upload),
                    contentDescription = "Upload Icon",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Upload photo",
                    color = Color.White,
                    fontSize = 16.sp,
                )
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Selecionar Imagem") },
                    text = { Text("Escolha uma opção:") },
                    confirmButton = {
                        TextButton(onClick = {
                            galleryLauncher.launch("image/*")
                            showDialog = false
                        }) {
                            Text("Galeria")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            if (cameraPermissionGranted.value) {
                                cameraLauncher.launch(null)
                            } else {
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                            showDialog = false
                        }) {
                            Text("Câmera")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    containerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                ),
                placeholder = { Text("Username", color = Color(0xFF5A5A5A)) },
                label = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    containerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                ),
                placeholder = { Text("Email", color = Color(0xFF5A5A5A)) },
                label = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    containerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                ),
                placeholder = { Text("Password", color = Color(0xFF5A5A5A)) },
                label = null,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    onClick = { navController.navigate("main") },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFFEC5766)
                    ),
                    border = BorderStroke(1.dp, Color(0xFFEC5766)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .width(130.dp)
                        .height(45.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cancel Icon",
                        tint = Color(0xFFEC5766),
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 9.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Cancel", color = Color(0xFFEC5766))
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(
                    onClick = {

                        imageBitmap.value?.let { bitmap ->
                            val imageUri = getImageUri(context, bitmap)
                            if (imageUri != null) {
                                userDAO.uploadImage(imageUri, username.toString()) { url ->
                                    if (url != null) {
                                        userDAO.updateUser(
                                            oldEmail = user.email,
                                            newUsername = username.value,
                                            newPassword = password.value,
                                            newEmail = email.value,
                                            newImage = url
                                        ) { success, message ->
                                            if (success) {
                                                val newUser = User(
                                                    email = email.value,
                                                    username = username.value,
                                                    password = password.value,
                                                    imageUrl = url
                                                )
                                                UserSession.loggedInUser = newUser
                                                navController.navigate("main")
                                            } else {
                                                // Lidar com erro
                                            }
                                        }
                                    }
                                }
                            }
                        }


                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFF4CAF50)
                    ),
                    border = BorderStroke(1.dp, Color(0xFF4CAF50)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .width(130.dp)
                        .height(45.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save Icon",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 9.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Save", color = Color(0xFF4CAF50))
                }
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No user logged in", fontSize = 36.sp, color = Color.Red)
        }
    }
}

private fun getImageUri(context: Context, bitmap: Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
    return Uri.parse(path)
}


private fun loadBitmapFromUri(context: Context, uri: Uri, onBitmapLoaded: (Bitmap?) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        withContext(Dispatchers.Main) {
            onBitmapLoaded(bitmap)
        }
    }
}