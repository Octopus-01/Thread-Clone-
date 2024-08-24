package com.learn.threadyt.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.learn.threadyt.navigation.Routes
import com.learn.threadyt.viewModel.AuthViewModel

@Composable
fun Login(navController: NavHostController){

    val authViewModel : AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState()
    val error by authViewModel.error.observeAsState()

    LaunchedEffect(firebaseUser ) {
        if(firebaseUser != null){
            navController.navigate(Routes.BottomNav.route){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }
    val context = LocalContext.current
    error?.let {
        Toast.makeText(context, "Invalied Details", Toast.LENGTH_SHORT).show()
    }


    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){

        Text(text = "Login", style = TextStyle(fontSize = 30.sp), fontWeight = FontWeight.ExtraBold)

        Box(modifier = Modifier.height(30.dp))

        OutlinedTextField(value = email, onValueChange = {email = it}, label = { Text(text = "E-mail")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), singleLine = true,
            modifier = Modifier.fillMaxWidth())

        Box(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = password, onValueChange = {password = it}, label = { Text(text = "Password")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password ), singleLine = true,
            modifier = Modifier.fillMaxWidth())

        Box(modifier = Modifier.height(10.dp))

        ElevatedButton(onClick = {
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }else authViewModel.login(email,password,context)
            Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()

        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Login",style = TextStyle(fontSize = 20.sp), fontWeight = FontWeight.Bold)

        }

        TextButton(onClick = {
            navController.navigate(Routes.Register.route){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }) {
            Text(text = "New User? Creat Account", style = TextStyle(fontWeight = FontWeight.Bold,
                fontSize = 16.sp),
                modifier = Modifier.padding(6.dp))
            
        }
    }

}

