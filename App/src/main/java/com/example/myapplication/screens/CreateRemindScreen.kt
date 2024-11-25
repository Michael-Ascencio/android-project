package com.example.myapplication.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.models.Reminds
import com.example.myapplication.navigation.Pantallas
import com.example.myapplication.viewmodel.ViewModelDatabase
import java.util.*

@Composable
fun CreateRemindScreen(
    viewModel: ViewModelDatabase,
    innerPadding: PaddingValues,
    navController: NavController
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color(0xFFFFF4FC)) // Fondo rosado
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CreateRemindForm(
            navController,
            viewModel,
            onClose = {
                Toast.makeText(context, "Cerrando...", Toast.LENGTH_SHORT).show()
                navController.navigate(Pantallas.RemindScreen.route)
            },
            onCreate = { nombre, repetir, descripcionCorta, descripcionLarga, frecuencia, fecha, hora ->
                if (nombre.isNotBlank() && descripcionCorta.isNotBlank() && fecha != "Selecciona la fecha" && hora != "Selecciona la hora") {
                    val nuevoRecordatorio = Reminds(
                        titulo = nombre,
                        repetir = repetir,
                        imagen = "",
                        descripcion_corta = descripcionCorta,
                        descripcion_larga = descripcionLarga,
                        frecuencia = frecuencia,
                        fecha = fecha,
                        hora = hora,
                        favorito = false,
                        borrado = false
                    )
                    viewModel.agregarRecordatorio(nuevoRecordatorio)
                    Toast.makeText(context, "Recordatorio creado", Toast.LENGTH_SHORT).show()
                    navController.navigate(Pantallas.RemindScreen.route)
                } else {
                    Toast.makeText(context, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}

@Composable
fun CreateRemindForm(
    navController: NavController,
    viewModel: ViewModelDatabase,
    onClose: () -> Unit,
    onCreate: (String, Boolean, String, String, String, String, String) -> Unit
) {
    val context = LocalContext.current

    // Variables de estado
    var nombre by remember { mutableStateOf("") }
    var descripcionCorta by remember { mutableStateOf("") }
    var descripcionLarga by remember { mutableStateOf("") }
    var repetir by remember { mutableStateOf(false) }
    var frecuencia by remember { mutableStateOf("Diario") }
    var expanded by remember { mutableStateOf(false) }
    var fecha by remember { mutableStateOf("Selecciona la fecha") }
    var hora by remember { mutableStateOf("Selecciona la hora") }

    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            fecha = "$dayOfMonth-${month + 1}-$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            hora = String.format("%02d:%02d", hourOfDay, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    )

    Column(
        modifier = Modifier
            .padding()
            .fillMaxSize()
            .background(Color(0xFFF2DEDF)) // Fondo rosado
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.LightGray, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Añadir\nImagen", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                BasicTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    decorationBox = { innerTextField ->
                        Row(
                            Modifier
                                .background(Color.Transparent)
                                .padding(4.dp)
                        ) {
                            if (nombre.isEmpty()) {
                                Text("Título", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
                )

                BasicTextField(
                    value = descripcionCorta,
                    onValueChange = { descripcionCorta = it },
                    decorationBox = { innerTextField ->
                        Row(
                            Modifier
                                .background(Color.Transparent)
                                .padding(4.dp)
                        ) {
                            if (descripcionCorta.isEmpty()) {
                                Text("Descripción Corta", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp, color = Color.Gray)
                )
            }

            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = descripcionLarga,
            onValueChange = { descripcionLarga = it },
            label = { Text("Descripción larga") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = repetir,
                onCheckedChange = { repetir = it }
            )
            Text("¿Desea que el recordatorio suene más de una vez?")
        }

        if (repetir) {
            Text("Seleccione la frecuencia", fontSize = 16.sp)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
                    .background(Color.LightGray)
                    .padding(8.dp)
            ) {
                Text(text = frecuencia, fontSize = 16.sp)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listOf("Diario", "Semanal", "Quincenal", "Mensual").forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(text = opcion) },
                        onClick = {
                            frecuencia = opcion
                            expanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() }
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Text(text = fecha, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { timePickerDialog.show() }
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Text(text = hora, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onCreate(nombre, repetir, descripcionCorta, descripcionLarga, frecuencia, fecha, hora)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Crear")
        }
    }
}
