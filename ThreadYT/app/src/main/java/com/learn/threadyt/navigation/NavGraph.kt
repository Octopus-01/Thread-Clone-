package com.learn.threadyt.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.learn.thread.screens.AddThreads
import com.learn.thread.screens.BottomNav
import com.learn.thread.screens.Home
import com.learn.threadyt.screens.Login
import com.learn.thread.screens.Notification
import com.learn.thread.screens.Profile
import com.learn.thread.screens.Register
import com.learn.thread.screens.Search
import com.learn.thread.screens.Splash

@Composable
fun NavGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = Routes.Splash.route) {
        composable(Routes.Home.route) {
            Home()
        }
        composable(Routes.AddThreads.route) {
            AddThreads()
        }
        composable(Routes.Search.route) {
            Search()
        }
        composable(Routes.Notification.route) {
            Notification()
        }
        composable(Routes.Profile.route) {
            Profile(navController)
        }
        composable(Routes.Splash.route) {
            Splash(navController)
        }
        composable(Routes.BottomNav.route) {
            BottomNav(navController)
        }
        composable(Routes.Login.route) {
            Login(navController)
        }
        composable(Routes.Register.route) {
            Register(navController)

        }
    }

}