package com.example.myapplication.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.models.Saving
import com.example.myapplication.navigation.Pantallas
import com.example.myapplication.viewmodel.ViewModelDatabase
import java.util.*

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.material3.TextField

@Composable
fun CreateSavingScreen(
    viewModel: ViewModelDatabase,
    innerPadding: PaddingValues,
    navController: NavController
) {
    val context = LocalContext.current

    // Estados para los campos editables
    var savingTitle by remember { mutableStateOf("") }
    var shortDescription by remember { mutableStateOf("") }
    var longDescription by remember { mutableStateOf("") }
    var targetAmount by remember { mutableStateOf("") }
    var currentAmount by remember { mutableStateOf("") }
    var selectedFrequency by remember { mutableStateOf("Diario") }
    var selectedStartDate by remember { mutableStateOf("Selecciona la fecha") }
    var selectedEndDate by remember { mutableStateOf("Selecciona la fecha") }
    var selectedTime by remember { mutableStateOf("Selecciona la hora") }
    var expanded by remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedStartDate = "$dayOfMonth-${month + 1}-$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val endDatePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedEndDate = "$dayOfMonth-${month + 1}-$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            selectedTime = String.format("%02d:%02d", hourOfDay, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    )

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color(0xFFF4E0E1)) // Fondo suave
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header with title and image
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
                    value = savingTitle,
                    onValueChange = { savingTitle = it },
                    decorationBox = { innerTextField ->
                        if (savingTitle.isEmpty()) {
                            Text(
                                text = "Título",
                                style = TextStyle(color = Color.Gray, fontSize = 18.sp)
                            )
                        }
                        innerTextField()
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                BasicTextField(
                    value = shortDescription,
                    onValueChange = { shortDescription = it },
                    decorationBox = { innerTextField ->
                        if (shortDescription.isEmpty()) {
                            Text(
                                text = "Descripción Corta",
                                style = TextStyle(color = Color.Gray, fontSize = 14.sp)
                            )
                        }
                        innerTextField()
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
                )
            }

            IconButton(onClick = {
                Toast.makeText(context, "Cerrando...", Toast.LENGTH_SHORT).show()
                navController.navigate(Pantallas.SavingScreen.route)
            }) {
                Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color.Black)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))


        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = longDescription,
            onValueChange = { longDescription = it },
            label = { Text("Descripción larga") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = targetAmount,
            onValueChange = { targetAmount = it },
            label = { Text("Monto objetivo") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = currentAmount,
            onValueChange = { currentAmount = it },
            label = { Text("Monto actual") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown de frecuencia
        Text("Frecuencia", fontSize = 16.sp)
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

        // Selección de fechas
        Text("Fecha Inicial", fontSize = 16.sp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() }
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Text(text = selectedStartDate, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Fecha Límite", fontSize = 16.sp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { endDatePickerDialog.show() }
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Text(text = selectedEndDate, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Seleccionar Hora de recordatorio para el ahorro", fontSize = 16.sp)
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

        // Botón de guardar
        Button(
            onClick = {
                if (
                    savingTitle.isNotBlank() && shortDescription.isNotBlank() &&
                    selectedStartDate != "Selecciona la fecha" && selectedEndDate != "Selecciona la fecha"
                ) {
                    val newSaving = Saving(
                        titulo = savingTitle,
                        imagen = "",
                        descripcion_corta = shortDescription,
                        descripcion_larga = longDescription,
                        frecuencia = selectedFrequency,
                        monto_objetivo = targetAmount,
                        monto_actual = currentAmount,
                        fecha_limite = selectedEndDate,
                        fecha_inicial = selectedStartDate,
                        hora = selectedTime,
                        favorito = false,
                        borrado = false
                    )
                    viewModel.agregarAhorro(newSaving)
                    Toast.makeText(context, "Ahorro creado", Toast.LENGTH_SHORT).show()
                    navController.navigate(Pantallas.SavingScreen.route)
                } else {
                    Toast.makeText(context, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Guardar")
        }
    }
}

