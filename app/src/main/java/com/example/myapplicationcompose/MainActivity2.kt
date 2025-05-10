package com.example.myapplicationcompose

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val firstKeyName = intent.getStringExtra("firstKeyName")
        val secondKeyName = intent.getStringExtra("secondKeyName")
        val nameComplete = "$firstKeyName $secondKeyName"

        Toast.makeText(this, nameComplete, Toast.LENGTH_LONG).show()

        setContent {
            CustomDarkTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Black
                ) { innerPadding ->
                    Greeting2(
                        name = nameComplete,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    val activity = LocalContext.current as? Activity
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val topBottomPadding = screenHeight * 0.04f

    var numero1 by remember { mutableStateOf("") }
    var numero2 by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = topBottomPadding, bottom = topBottomPadding),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Usuario: $name", color = Color.White, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Calculadora", color = Color.White, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = numero1,
                    onValueChange = { numero1 = it.filter { char -> char.isDigit() } },
                    label = { Text("Número 1", color = Color.White) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        focusedLabelColor = Color.White,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = numero2,
                    onValueChange = { numero2 = it.filter { char -> char.isDigit() } },
                    label = { Text("Número 2", color = Color.White) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        focusedLabelColor = Color.White,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            resultado = operar(numero1, numero2) { a, b -> a + b }
                        },
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Sumar", color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            resultado = operar(numero1, numero2) { a, b -> a - b }
                        },
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Restar", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            resultado = operar(numero1, numero2) { a, b -> a * b }
                        },
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Multiplicar", color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            resultado = if (numero2 != "0" && numero2 != "") {
                                operar(numero1, numero2) { a, b -> a / b }
                            } else {
                                "División por cero"
                            }
                        },
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Dividir", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Resultado: $resultado", color = Color.White, style = MaterialTheme.typography.bodyLarge)
            }
        }

        Button(
            onClick = { activity?.finish() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)) // naranja
        ) {
            Text("Salir", color = Color.White)
        }
    }
}

// Función para operar
fun operar(n1: String, n2: String, operacion: (Double, Double) -> Double): String {
    return try {
        val a = n1.toDouble()
        val b = n2.toDouble()
        val res = operacion(a, b)
        res.toString()
    } catch (e: Exception) {
        "Error en los datos"
    }
}

// Tema personalizado oscuro
@Composable
fun CustomDarkTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            background = Color.Black,
            onBackground = Color.White,
            primary = Color.Red,
            secondary = Color(0xFFFF9800)
        ),
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewGreeting2() {
    CustomDarkTheme {
        Greeting2("Andrés")
    }
}
