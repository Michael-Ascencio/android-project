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
import com.example.myapplication.navigation.Pantallas
import java.util.*

@Composable
fun CreateSavingScreen(innerPadding: PaddingValues, navController: NavController) {
    val context = LocalContext.current

    // Variables de estado
    var selectedFrequency by remember { mutableStateOf("Diario") }
    var expanded by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("Selecciona la fecha") }
    var selectedTime by remember { mutableStateOf("Selecciona la hora") }

    var recordatorioTitulo by remember { mutableStateOf("") }
    var shortDescription by remember { mutableStateOf("") }
    var longDescription by remember { mutableStateOf("") }
    var reminderRepeat by remember { mutableStateOf(false) } // Estado de la Checkbox

    val calendar = Calendar.getInstance()

    // Dialogo para seleccionar la fecha
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Dialogo para seleccionar la hora
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            selectedTime = String.format("%02d:%02d", hourOfDay, minute)
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false
    )

    // Estructura de la pantalla
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color(0xFFF4E0E1))  // Fondo rosa pálido
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cabecera (Imagen, Título, Descripción Corta, Botón de cerrar)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botón de "Añadir Imagen"
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
                // Título
                BasicTextField(
                    value = recordatorioTitulo,
                    onValueChange = { recordatorioTitulo = it },
                    decorationBox = { innerTextField ->
                        Row(
                            Modifier
                                .background(Color.Transparent)
                                .padding(4.dp)
                        ) {
                            if (recordatorioTitulo.isEmpty()) {
                                Text("Título", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
                )

                // Descripción corta
                BasicTextField(
                    value = shortDescription,
                    onValueChange = { shortDescription = it },
                    decorationBox = { innerTextField ->
                        Row(
                            Modifier
                                .background(Color.Transparent)
                                .padding(4.dp)
                        ) {
                            if (shortDescription.isEmpty()) {
                                Text("Descripción Corta", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp, color = Color.Gray)
                )
            }

            // Botón de "Cerrar" (X)
            IconButton(onClick = {
                Toast.makeText(context, "Cerrando...", Toast.LENGTH_SHORT).show()
                navController.navigate(Pantallas.SavingScreen.route)
            }) {
                Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Caja para la descripción larga del recordatorio
        TextField(
            value = longDescription,
            onValueChange = { longDescription = it },
            label = { Text("Esta es una breve descripción...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Checkbox para repetir el recordatorio
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = reminderRepeat,
                onCheckedChange = { reminderRepeat = it }
            )
            Text("¿Desea que el recordatorio suene más de una vez?")
        }

        if (reminderRepeat) {
            // Dropdown para seleccionar frecuencia (si la checkbox está seleccionada)
            Text("Seleccione cada cuanto quiere que suene el recordatorio.", fontSize = 16.sp)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
                    .background(Color.LightGray)
                    .padding(8.dp)
            ) {
                Text(text = selectedFrequency, fontSize = 16.sp)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listOf("Diario", "Semanal", "Quincenal", "Mensual").forEach { frequency ->
                    DropdownMenuItem(
                        text = { Text(text = frequency) },
                        onClick = {
                            selectedFrequency = frequency
                            expanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Selección de fecha
        Text("Selecciona la fecha y hora inicial del recordatorio.", fontSize = 16.sp)

        // Fecha
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() }
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Text(text = selectedDate, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Selección de hora
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { timePickerDialog.show() }
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Text(text = selectedTime, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de Crear
        Button(
            onClick = {
                Toast.makeText(context, "Recordatorio creado", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Crear")
        }
    }
}
