package com.example.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyConverterTheme {
                CurrencyConverterApp()
            }
        }
    }
}

@Composable
fun CurrencyConverterApp() {
    var inputAmount by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf("IDR") }
    var toCurrency by remember { mutableStateOf("USD") }
    var result by remember { mutableStateOf("") }

    val currencies = listOf("IDR", "USD", "JPY", "KRW", "SAR")

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Currency Converter",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
            )

            OutlinedTextField(
                value = inputAmount,
                onValueChange = { inputAmount = it },
                label = { Text("Amount") },
                singleLine = true
            )

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                CurrencyDropdown(label = "From", selected = fromCurrency, options = currencies) {
                    fromCurrency = it
                }
                CurrencyDropdown(label = "To", selected = toCurrency, options = currencies) {
                    toCurrency = it
                }
            }

            Button(onClick = {
                result = convertCurrency(inputAmount, fromCurrency, toCurrency)
            }) {
                Text("Convert")
            }

            Text(
                text = "Result: $result",
                style = MaterialTheme.typography.headlineSmall
            )

            Button(onClick = {
                inputAmount = ""
                result = ""
            }) {
                Text("Clear")
            }
        }
    }
}

@Composable
fun CurrencyDropdown(label: String, selected: String, options: List<String>, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(label)
        Box {
            OutlinedTextField(
                value = selected,
                onValueChange = {},
                modifier = Modifier.width(150.dp),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                },
                label = { Text(label) }
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onSelect(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

fun convertCurrency(amountStr: String, from: String, to: String): String {
    val rates = mapOf(
        "IDR" to 1.0,
        "USD" to 0.000065,
        "JPY" to 0.0096,
        "KRW" to 0.087,
        "SAR" to 0.00024
    )

    val amount = amountStr.toDoubleOrNull() ?: return "Invalid input"
    val fromRate = rates[from] ?: return "Unknown currency"
    val toRate = rates[to] ?: return "Unknown currency"

    val result = (amount / fromRate) * toRate
    return if (result % 1.0 == 0.0) result.toInt().toString() else "%.2f".format(result)
}

@Preview(showBackground = true)
@Composable
fun PreviewCurrencyConverter() {
    CurrencyConverterTheme {
        CurrencyConverterApp()
    }
}
