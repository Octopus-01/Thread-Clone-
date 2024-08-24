package com.learn.threadyt.screens


import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.learn.threadyt.R
import com.learn.threadyt.navigation.Routes
import com.learn.threadyt.viewModel.AuthViewModel

@Composable
fun Register(navController: NavHostController){
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var imageuri by remember { mutableStateOf<Uri?>(null) }

    var permissionrequests = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        Manifest.permission.READ_MEDIA_IMAGES
    }else{
        Manifest.permission.READ_EXTERNAL_STORAGE
    }
    val context = LocalContext.current

    val authviewModel : AuthViewModel = viewModel()
    val firebaseUser by authviewModel.firebaseUser.observeAsState(null)

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        uri: Uri? ->
        imageuri = uri
    }

    val permissionLaunchers = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
            isGranted : Boolean->
            if (isGranted){
                // permision granted task
            }else{
                // Permision not granted task
            }
    }

    LaunchedEffect(firebaseUser) {
        if(firebaseUser!= null){
            navController.navigate(Routes.BottomNav.route){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }

    }


    Column (modifier = Modifier
        .fillMaxSize()
        .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){

        Text(text = "Register", style = TextStyle(fontSize = 30.sp), fontWeight = FontWeight.ExtraBold)

        Box(modifier = Modifier.height(30.dp))

        Image(painter =if ( imageuri == null) painterResource(id = R.drawable.person)
            else rememberAsyncImagePainter(model = imageuri)
            , contentDescription ="Person",
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable {
                    val isGranter = ContextCompat.checkSelfPermission(
                        context,
                        permissionrequests
                    ) == PackageManager.PERMISSION_GRANTED

                    if (isGranter) {
                        launcher.launch("image/*")
                    } else {
                        permissionLaunchers.launch(permissionrequests)
                    }
                },
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = name, onValueChange = {name = it}, label = { Text(text = "Name")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), singleLine = true,
            modifier = Modifier.fillMaxWidth())

        Box(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = username, onValueChange = {username = it}, label = { Text(text = "Username")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), singleLine = true,
            modifier = Modifier.fillMaxWidth())

        Box(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = bio, onValueChange = {bio = it}, label = { Text(text = "Bio")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), singleLine = true,
            modifier = Modifier.fillMaxWidth())

        Box(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = email, onValueChange = {email = it}, label = { Text(text = "E-mail")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), singleLine = true,
            modifier = Modifier.fillMaxWidth())

        Box(modifier = Modifier.height(10.dp))
        

        OutlinedTextField(value = password, onValueChange = {password = it}, label = { Text(text = "Password")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password ), singleLine = true,
            modifier = Modifier.fillMaxWidth())

        Box(modifier = Modifier.height(20.dp))

        ElevatedButton(onClick = {
            if (name.isEmpty() || username.isEmpty() || bio.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }else{
                authviewModel.register(email, password, name, username, bio,imageuri!!,context)
                Toast.makeText(context, "Register Successfully", Toast.LENGTH_SHORT).show()
                navController.navigate(Routes.BottomNav.route){
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }

        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Register",style = TextStyle(fontSize = 20.sp), fontWeight = FontWeight.Bold)

        }


        TextButton(onClick = {
            navController.navigate(Routes.Login.route){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }) {
            Text(text = "Alredy have Account", style = TextStyle(fontWeight = FontWeight.Bold,
                fontSize = 16.sp),
                modifier = Modifier.padding(6.dp))

        }
    }

}


