package com.example.starbucks.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.Period

@Composable
fun HomeScreen() {
    val user = FirebaseAuth.getInstance().currentUser
    var fullName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    LaunchedEffect(user?.uid) {
        user?.uid?.let { uid ->
            FirebaseFirestore.getInstance().collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    fullName = document.getString("fullName") ?: ""
                    birthDate = document.getString("birthDate") ?: ""
                    gender = document.getString("gender") ?: ""
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome, $fullName", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Date of Birth: $birthDate", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text("Gender: $gender", fontSize = 16.sp)
    }
}
