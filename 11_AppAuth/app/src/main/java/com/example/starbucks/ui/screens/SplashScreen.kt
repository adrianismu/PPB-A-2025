package com.example.starbucks.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.starbucks.R
import com.example.starbucks.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController? = null, viewModel: AuthViewModel) {
    LaunchedEffect(true) {
        // Only navigate if preview is not running
        if (navController != null) {
            delay(2000)
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.starbucks_splash_screen),
            contentDescription = "Starbucks Logo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun SplashScreenPreview() {
//    SplashScreen() // navController is null here
//}