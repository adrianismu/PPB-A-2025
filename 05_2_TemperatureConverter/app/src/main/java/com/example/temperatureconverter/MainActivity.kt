package com.example.temperatureconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import com.example.temperatureconverter.ui.theme.TemperatureConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TemperatureConverterTheme {
                TempConverterApp()
            }
        }
    }
}

@Composable
fun TempConverterApp() {
    var input by remember { mutableStateOf("") }
    var fromUnit by remember { mutableStateOf("Celsius") }
    var toUnit by remember { mutableStateOf("Fahrenheit") }
    var result by remember { mutableStateOf("") }

    val units = listOf("Celsius", "Fahrenheit", "Reamur", "Kelvin")

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Temperature Converter",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Enter value") },
                singleLine = true
            )

            DropdownSelector(label = "From", selectedUnit = fromUnit, units = units) {
                fromUnit = it
            }

            DropdownSelector(label = "To", selectedUnit = toUnit, units = units) {
                toUnit = it
            }

            Text(
                text = "Result: $result",
                style = MaterialTheme.typography.headlineSmall
            )

            Button(
                onClick = {
                    result = convertTemperature(input, fromUnit, toUnit)
                }
            ) {
                Text("Convert")
            }

            Button(
                onClick = {
                    input = ""
                    result = ""
                },
                modifier = Modifier.fillMaxWidth(0.5f),
            ) {
                Text("AC")
            }
        }
    }
}

@Composable
fun DropdownSelector(label: String, selectedUnit: String, units: List<String>, onUnitSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label)
        Box {
            OutlinedTextField(
                value = selectedUnit,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.width(200.dp),
                label = { Text(label) },
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                units.forEach { unit ->
                    DropdownMenuItem(
                        text = { Text(unit) },
                        onClick = {
                            onUnitSelected(unit)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

fun convertTemperature(input: String, from: String, to: String): String {
    val value = input.toDoubleOrNull() ?: return "Invalid input"

    val celsius = when (from) {
        "Celsius" -> value
        "Fahrenheit" -> (value - 32) * 5 / 9
        "Reamur" -> value * 5 / 4
        "Kelvin" -> value - 273.15
        else -> return "Invalid unit"
    }

    val result = when (to) {
        "Celsius" -> celsius
        "Fahrenheit" -> celsius * 9 / 5 + 32
        "Reamur" -> celsius * 4 / 5
        "Kelvin" -> celsius + 273.15
        else -> return "Invalid unit"
    }

    return if (result % 1.0 == 0.0) result.toInt().toString() else "%.2f".format(result)
}

@Preview(showBackground = true)
@Composable
fun TempPreview() {
    TemperatureConverterTheme {
        TempConverterApp()
    }
}
