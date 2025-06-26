package com.example.starbucks.ui.screens

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.starbucks.R
import com.example.starbucks.viewmodel.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegisterScreen(navController: NavController, viewModel: AuthViewModel) {
    var phoneNumber by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FA))
            .padding(horizontal = 32.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Image(
            painter = painterResource(id = R.drawable.starbucks_logo),
            contentDescription = "Starbucks Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(80.dp))

        Text(
            text = "Sign Up",
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF00643C),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.Start)
        )

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            placeholder = { Text("Full Name", color = Color(0xFFD9DADB)) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = birthDate,
            onValueChange = { birthDate = it },
            placeholder = { Text("Birth Date", color = Color(0xFFD9DADB)) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = gender,
            onValueChange = { gender = it },
            placeholder = { Text("Gender (Male/Female)", color = Color(0xFFD9DADB)) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            placeholder = { Text("Phone", color = Color(0xFFD9DADB)) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                viewModel.sendOtp(
                    phone = phoneNumber,
                    fullName = fullName,
                    birthDate = birthDate,
                    gender = gender,
                    activity = context as Activity
                ) {
                    navController.navigate("otp")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00452A))
        ) {
            Text("Send OTP", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "already have an account? ",
                fontSize = 12.sp,
                color = Color(0XFF00452A)
            )

            Text(
                text = "log in here",
                fontSize = 12.sp,
                color = Color(0XFF0075FF),
                modifier = Modifier.clickable {
                    navController.navigate("login")
                }
            )
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun RegisterScreenPreview() {
//    RegisterScreen()
//}