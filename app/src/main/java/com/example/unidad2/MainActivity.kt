package com.example.unidad2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unidad2.ui.theme.Unidad2Theme

class MainActivity : ComponentActivity() {

    private val tag = "LifecycleTrack"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(tag, "onCreate")
        enableEdgeToEdge()
        setContent {
            Unidad2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.background_custom)
                ) {
                    ExamenApp()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.v(tag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.v(tag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.v(tag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.v(tag, "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.v(tag, "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(tag, "onDestroy")
    }
}

@Composable
fun ExamenApp() {
    val context = LocalContext.current

    // rememberSaveable mantiene los datos al rotar la pantalla
    var dni by rememberSaveable { mutableStateOf("") }
    var apellidos by rememberSaveable { mutableStateOf("") }
    var nombre by rememberSaveable { mutableStateOf("") }
    var nacimiento by rememberSaveable { mutableStateOf("") }
    var sexo by rememberSaveable { mutableStateOf("") }
    var validez by rememberSaveable { mutableStateOf("") }

    // LazyColumn para organizar la estructura visual y permitir scroll
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            // Etiqueta Nombre del Alumno desde recursos
            Text(
                text = stringResource(id = R.string.alumno_nombre),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
        }

        item {
            // Imagen desde recursos
            Image(
                painter = painterResource(id = R.drawable.perfil),
                contentDescription = "Foto Examen",
                modifier = Modifier.size(120.dp)
            )
        }

        // Campos de Texto (OutlinedTextField)
        item { CustomInput(dni, { dni = it }, R.string.label_dni) }
        item { CustomInput(apellidos, { apellidos = it }, R.string.label_apellidos) }
        item { CustomInput(nombre, { nombre = it }, R.string.label_nombre) }
        item { CustomInput(nacimiento, { nacimiento = it }, R.string.label_nacimiento) }
        item { CustomInput(sexo, { sexo = it }, R.string.label_sexo) }
        item { CustomInput(validez, { validez = it }, R.string.label_validez) }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            // Botón Azul con Intent de Correo
            Button(
                onClick = {
                    val message = """
                        Datos Capturados:
                        DNI: $dni
                        Apellidos: $apellidos
                        Nombre: $nombre
                        Nacimiento: $nacimiento
                        Sexo: $sexo
                        Validez: $validez
                    """.trimIndent()

                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:")
                        putExtra(Intent.EXTRA_SUBJECT, "Datos del Formulario - Examen")
                        putExtra(Intent.EXTRA_TEXT, message)
                    }

                    // Verificación básica para lanzar el intent
                    context.startActivity(Intent.createChooser(intent, "Enviar correo..."))
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.btn_send), color = Color.White)
            }
        }
    }
}

@Composable
fun CustomInput(value: String, onValueChange: (String) -> Unit, labelRes: Int) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(id = labelRes)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedLabelColor = Color.LightGray,
            focusedLabelColor = Color.Cyan,
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color.Cyan
        )
    )
}
