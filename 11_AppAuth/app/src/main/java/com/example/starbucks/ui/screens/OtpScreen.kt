package com.example.starbucks.ui.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.starbucks.viewmodel.AuthViewModel

@Composable
fun OtpScreen(navController: NavController, viewModel: AuthViewModel) {
    var code by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Enter OTP", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = code,
            onValueChange = { code = it },
            label = { Text("OTP Code") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (code.length < 6) {
                Toast.makeText(context, "Please enter a valid 6-digit code", Toast.LENGTH_SHORT).show()
                return@Button
            }

            try {
                viewModel.verifyOtp(code, context as Activity) {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Verification failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }) {
            Text("Verify")
        }
    }
}