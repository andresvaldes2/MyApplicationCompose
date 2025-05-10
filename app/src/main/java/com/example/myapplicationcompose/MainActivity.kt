package com.example.myapplicationcompose

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplicationcompose.ui.theme.MyApplicationComposeTheme
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black // Fondo negro
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Greeting(name = "Andrés Valdés 6-726-1938")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val contexto = LocalContext.current

    // Imagen sin borde
    Image(
        painter = painterResource(id = R.drawable.login),
        contentDescription = "Logo",
        modifier = Modifier
            .padding(16.dp)
            .size(250.dp)
    )

    Text(
        text = "Bienvenido",
        style = MaterialTheme.typography.headlineMedium,
        color = Color(0xFFFF9800) // Título en naranja
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = name, color = Color.White)

    Spacer(modifier = Modifier.height(16.dp))

    StyledLoginSection()
}

@Composable
fun StyledLoginSection() {
    val contexto = LocalContext.current

    var usuario by rememberSaveable { mutableStateOf("") }
    var contraseña by rememberSaveable { mutableStateOf("") }
    var mostrarFecha by remember { mutableStateOf(false) }

    // Usuario
    OutlinedTextField(
        value = usuario,
        onValueChange = { usuario = it },
        label = { Text("Usuario: Andrés") },
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    // Contraseña
    OutlinedTextField(
        value = contraseña,
        onValueChange = { contraseña = it },
        label = { Text("Contraseña: 0306Aa_a") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth()
    )

    val esContraseñaValida = remember(contraseña) {
        contraseña.length > 7 &&
                contraseña.any { it.isUpperCase() } &&
                contraseña.any { it.isLowerCase() } &&
                contraseña.any { it.isDigit() } &&
                '_' in contraseña
    }

    Text(
        text = if (esContraseñaValida) "Formato de contraseña válido" else "La contraseña debe tener más de 8 caracteres, incluir mayúscula, minúscula, número y guión bajo",
        color = if (esContraseñaValida) Color.Green else Color.Red,
        modifier = Modifier.padding(top = 4.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Botón Acceder
    Button(
        onClick = {
            if (usuario == "Andrés" && contraseña == "0306Aa_a") {
                val intent = Intent(contexto, MainActivity2::class.java)
                intent.putExtra("firstKeyName", usuario)
                intent.putExtra("secondKeyName", contraseña)
                contexto.startActivity(intent)
            } else {
                Toast.makeText(contexto, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show()
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red,
            contentColor = Color.White
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Acceder")
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Botón mostrar hora y fecha
    Button(
        onClick = { mostrarFecha = true },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red,
            contentColor = Color.White
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Mostrar hora y fecha")
    }

    if (mostrarFecha) {
        val currentDateTime = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())
        Text("Fecha y hora actual: $currentDateTime", modifier = Modifier.padding(top = 8.dp), color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Greeting(name = "Andrés Valdés 6-726-1938")
            }
        }
    }
}
