package com.learn.threadyt.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.auth.FirebaseAuth
import com.learn.threadyt.R
import com.learn.threadyt.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun Splash(navController: NavHostController) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottei))
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        LottieAnimation(modifier = Modifier.size(200.dp), composition = composition)

    }

    LaunchedEffect(key1 = true) {
        delay(3000)

      if (FirebaseAuth.getInstance().currentUser!= null){
          navController.navigate(Routes.BottomNav.route){
              popUpTo(navController.graph.startDestinationId)
              launchSingleTop = true
          }
      }else{
          navController.navigate(Routes.Login.route){
              popUpTo(navController.graph.startDestinationId)
              launchSingleTop = true
          }


      }

    }


}
/*

ConstraintLayout(modifier = Modifier.fillMaxSize()) {
    val (image) = createRefs()
    Image(painter = painterResource(id = R.drawable.logo), contentDescription = null,
        modifier = Modifier
            .constrainAs(image) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .size(120.dp))

}*/
