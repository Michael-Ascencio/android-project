package com.example.myapplication.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.models.Reminds
import com.example.myapplication.navigation.Pantallas
import com.example.myapplication.viewmodel.ViewModelReminds
import java.util.*

@Composable
fun CreateRemindScreen(
    viewModel: ViewModelReminds,
    innerPadding: PaddingValues,
    navController: NavController
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color(0xFFFFF4FC))  // Rosado
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CreateRemindForm(
            navController, viewModel,
            onClose = {
                Toast.makeText(context, "Cerrando...", Toast.LENGTH_SHORT).show()
                navController.navigate(Pantallas.RemindScreen.route)
            },
            onCreate = { nombreAhorro, shortDescription, longDescription, selectedDate, selectedTime ->
                if (nombreAhorro.isNotBlank() && shortDescription.isNotBlank() && selectedDate != "Selecciona la fecha" && selectedTime != "Selecciona la hora") {
                    val nuevoRecordatorio = Reminds(
                        titulo = nombreAhorro,
                        descripcion = longDescription,
                        fecha = selectedDate,
                        hora = selectedTime,
                        favorito = false,
                        borrado = false
                    )
                    viewModel.agregarRecordatorio(nuevoRecordatorio)
                    Toast.makeText(context, "Recordatorio creado", Toast.LENGTH_SHORT).show()
                    navController.navigate(Pantallas.RemindScreen.route)
                } else {
                    Toast.makeText(
                        context,
                        "Por favor, completa todos los campos.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            recordatorio = null
        )
    }
}

@Composable
fun CreateRemindForm(
    navController: NavController,
    viewModel: ViewModelReminds,
    onClose: () -> Unit,
    onCreate: (String, String, String, String, String) -> Unit,
    recordatorio: Reminds?
) {
    val context = LocalContext.current

    var selectedFrequency by remember { mutableStateOf("Diario") }
    var expanded by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("Selecciona la fecha") }
    var selectedTime by remember { mutableStateOf("Selecciona la hora") }

    var nombreAhorro by remember { mutableStateOf("") }
    var shortDescription by remember { mutableStateOf("") }
    var longDescription by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
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
        verticalArrangement = Arrangement.spacedBy(16.dp)
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
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                    .clickable { /* Manejar el clic para añadir imagen */ },
                contentAlignment = Alignment.Center
            ) {
                if (recordatorio?.imagen != null) {
                    Image(
                        painter = rememberAsyncImagePainter(model = recordatorio.imagen),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.placeholder),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                    Text("Añadir\nImagen", fontSize = 16.sp, color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                BasicTextField(
                    value = nombreAhorro,
                    onValueChange = { nombreAhorro = it },
                    decorationBox = { innerTextField ->
                        Row(
                            Modifier
                                .background(Color.Transparent)
                                .padding(4.dp)
                        ) {
                            if (nombreAhorro.isEmpty()) {
                                Text("Título", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
                )

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

            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color.Black)
            }
        }

        TextField(
            value = longDescription,
            onValueChange = { longDescription = it },
            label = { Text("Descripción de la meta de ahorro") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Selecciona cada cuanto quieres que suene el recordatorio.", fontSize = 16.sp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .background(Color.Gray)
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

        Text("Selecciona la fecha y hora inicial del recordatorio.", fontSize = 16.sp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() }
                .background(Color.Gray)
                .padding(8.dp)
        ) {
            Text(text = selectedDate, fontSize = 16.sp)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { timePickerDialog.show() }
                .background(Color.Gray)
                .padding(8.dp)
        ) {
            Text(text = selectedTime, fontSize = 16.sp)
        }

        Button(
            onClick = { onCreate(nombreAhorro, shortDescription, longDescription, selectedDate, selectedTime) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Crear")
        }
    }
}
