package com.example.starbucks.ui.screens

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun LoginScreen(navController: NavController? = null, viewModel: AuthViewModel) {
    var phoneNumber by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FA))
            .padding(horizontal = 32.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        Image(
            painter = painterResource(id = R.drawable.starbucks_logo),
            contentDescription = "Starbucks Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(80.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Log in",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF00643C),
                modifier = Modifier.padding(vertical = 16.dp).align(Alignment.Start)
            )

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                placeholder = { Text("Phone", color = Color(0xFFD9DADB)) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFFFFFFF),
                    focusedContainerColor = Color(0xFFFFFFFF),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {
                    navController?.let {
                        viewModel.sendOtpForLogin(phoneNumber, context as Activity) {
                            it.navigate("otp")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00452A))
            ) {
                Text("continue", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "don't have an account? ",
                fontSize = 12.sp,
                color = Color(0XFF00452A)
            )

            Text(
                text = "create here",
                fontSize = 12.sp,
                color = Color(0XFF0075FF),
                modifier = Modifier.clickable {
                    navController?.navigate("register")
                }
            )
        }
    }
}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen()
//}