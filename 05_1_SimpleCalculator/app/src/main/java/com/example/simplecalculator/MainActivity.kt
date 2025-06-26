package com.example.simplecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleCalculatorTheme {
                CalcApp()
            }
        }
    }
}

@Composable
fun CalcApp() {
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Simple Calculator",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = num1,
                onValueChange = { num1 = it },
                label = { Text("Number 1") },
                singleLine = true
            )

            OutlinedTextField(
                value = num2,
                onValueChange = { num2 = it },
                label = { Text("Number 2") },
                singleLine = true
            )

            Text(
                text = "Result: $result",
                style = MaterialTheme.typography.headlineSmall
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { result = calc(num1, num2, "+") }) {
                    Text("+")
                }
                Button(onClick = { result = calc(num1, num2, "-") }) {
                    Text("-")
                }
                Button(onClick = { result = calc(num1, num2, "*") }) {
                    Text("×")
                }
                Button(onClick = { result = calc(num1, num2, "/") }) {
                    Text("÷")
                }
            }

            Button(
                onClick = {
                    num1 = ""
                    num2 = ""
                    result = ""
                },
                modifier = Modifier.fillMaxWidth(0.5f),
            ) {
                Text("AC")
            }
        }
    }
}


fun calc(num1: String, num2: String, operator: String): String {
    if (num1.isEmpty() || num2.isEmpty()) {
        return "Invalid input"
    }
    return try {
        val result = when (operator) {
            "+" -> num1.toDouble() + num2.toDouble()
            "-" -> num1.toDouble() - num2.toDouble()
            "*" -> num1.toDouble() * num2.toDouble()
            "/" -> num1.toDouble() / num2.toDouble()
            else -> return "Invalid operator"
        }
        if (result % 1.0 == 0.0)
            result.toInt().toString()
        else
            result.toString()
    } catch (e: Exception) {
        "Error"
    }
}


@Preview(showBackground = true)
@Composable
fun CalcPreview() {
    SimpleCalculatorTheme {
        CalcApp()
    }
}
