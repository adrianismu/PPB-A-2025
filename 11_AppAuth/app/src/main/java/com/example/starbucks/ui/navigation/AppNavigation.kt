package com.example.starbucks.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.starbucks.ui.screens.HomeScreen
import com.example.starbucks.ui.screens.SplashScreen
import com.example.starbucks.ui.screens.LoginScreen
import com.example.starbucks.ui.screens.OtpScreen
import com.example.starbucks.ui.screens.RegisterScreen
import com.example.starbucks.viewmodel.AuthViewModel

@Composable
fun AppNavigation(viewModel: AuthViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, viewModel) }
        composable("register") { RegisterScreen(navController, viewModel) }
        composable("otp") { OtpScreen(navController, viewModel) }
        composable("home") { HomeScreen() }
    }
}