package com.learn.threadyt.navigation

sealed class Routes(val route: String)  {
    object Home : Routes("home")
    object AddThreads : Routes("addThreads")
    object Search : Routes("search")
    object Notification : Routes("notification")
    object Profile : Routes("profile")
    object Splash : Routes("splash")
    object BottomNav : Routes("bottomNav")
    object Login : Routes("Login")
    object Register: Routes("Register")


}