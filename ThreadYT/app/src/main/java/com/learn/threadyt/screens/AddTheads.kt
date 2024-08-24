package com.learn.threadyt.screens

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.learn.threadyt.R
import com.learn.threadyt.viewModel.AuthViewModel

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
@Composable
fun AddThreads() {
    val context = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var threadText by remember { mutableStateOf("") }
    var permissionrequests = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        Manifest.permission.READ_MEDIA_IMAGES
    }else{
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val authviewModel : AuthViewModel = viewModel()
    val firebaseUser by authviewModel.firebaseUser.observeAsState(null)

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            uri: Uri? ->
        imageUri = uri
    }

    val permissionLaunchers = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
            isGranted : Boolean->
        if (isGranted){
            // permision granted task
        }else{
            // Permision not granted task
        }
    }



    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        val (crossPic, text, logo, userName, editText, attachMedia,
            replyText, button, imageBox) = createRefs()

        Image(painter = painterResource(id = R.drawable.baseline_close_24),
            contentDescription = "close",
            modifier = Modifier
                .constrainAs(crossPic) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .clickable { })
        Text(text = "Add Thread", style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        ), modifier = Modifier.constrainAs(text){
            top.linkTo(crossPic.top)
            start.linkTo(crossPic.end, margin = 12.dp)
            bottom.linkTo(crossPic.bottom)

        })
        Image(painter = painterResource(id = R.drawable.person),
            contentDescription = "image",
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(text.bottom)
                    start.linkTo(parent.start)
                }
                .size(36.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Text(text = "User Name", style = TextStyle(
            fontSize = 20.sp
        ), modifier = Modifier.constrainAs(userName){
            top.linkTo(logo.top)
            start.linkTo(logo.end, margin = 12.dp)
            bottom.linkTo(logo.bottom)

        }
        )

        BasicTextFeeldWithHint(hint = "Start Thread", value = threadText, onValueChange = {threadText = it},
            modifier = Modifier
                .constrainAs(editText) {
                    top.linkTo(logo.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth())

    }
}

@Composable
fun BasicTextFeeldWithHint(hint : String, value: String, onValueChange: (String) -> Unit,
                           modifier: Modifier) {
    Box(modifier = modifier){
        if (value.isEmpty()){
            Text(text = hint, color = Color.Gray)
        }
        BasicTextField(value = value, onValueChange = onValueChange,
            textStyle = TextStyle.Default.copy(color = Color.Black),
            modifier = Modifier.fillMaxWidth())
    }
}

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
@Preview(showBackground = true)
@Composable
fun AddThreadsPreview() {
    AddThreads()
}