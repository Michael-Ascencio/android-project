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
fun EditRemindScreen(
    viewModel: ViewModelDatabase,
    innerPadding: PaddingValues,
    navController: NavController,
    id: Int,
    titulo: String?,
    Repetir: Boolean?,
    imagen: String?,
    descripcionCorta: String?,
    descripcionLarga: String?,
    frecuencia: String?,
    fecha: String?,
    hora: String?,
    favorito: Boolean?,
    borrado: Boolean?
) {
    val context = LocalContext.current

    var recordatorioTitulo by remember { mutableStateOf(titulo ?: "") }
    var descripcionCorta by remember { mutableStateOf(descripcionCorta ?: "") }
    var descripcionLarga by remember { mutableStateOf(descripcionLarga ?: "") }
    var frecuencia by remember { mutableStateOf(frecuencia ?: "") }
    var repetir by remember { mutableStateOf(Repetir) }
    var expanded by remember { mutableStateOf(false) }
    var fechaSeleccionada by remember { mutableStateOf(fecha ?: "Selecciona la fecha") }
    var horaSeleccionada by remember { mutableStateOf(hora ?: "Selecciona la hora") }
    var favorito by remember { mutableStateOf(favorito) }
    var borrado by remember { mutableStateOf(borrado) }

    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            fechaSeleccionada = "$dayOfMonth-${month + 1}-$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            horaSeleccionada = String.format("%02d:%02d", hourOfDay, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    )

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color(0xFFF4E0E1))
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

            IconButton(onClick = {
                Toast.makeText(context, "Cerrando...", Toast.LENGTH_SHORT).show()
                navController.navigate(Pantallas.RemindScreen.route)
            }) {
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

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = repetir == true, onCheckedChange = { repetir = it })
            Text("¿Desea que el recordatorio se repita?")
        }

        if (repetir == true) {
            Text("Frecuencia:", fontSize = 16.sp)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
                    .background(Color.LightGray)
                    .padding(8.dp)
            ) {
                Text(text = frecuencia, fontSize = 16.sp)
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
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
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() }
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Text(text = fechaSeleccionada, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { timePickerDialog.show() }
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Text(text = horaSeleccionada, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val nuevoRecordatorio = Reminds(
                    id = id,
                    titulo = recordatorioTitulo,
                    repetir = Repetir == true,
                    imagen = imagen ?: "",
                    descripcion_corta = descripcionCorta,
                    descripcion_larga = descripcionLarga,
                    fecha = fechaSeleccionada,
                    hora = horaSeleccionada,
                    favorito = favorito == true,
                    borrado = borrado == true,
                )

                viewModel.actualizarRecordatorio(nuevoRecordatorio)

                Toast.makeText(context, "Recordatorio actualizado", Toast.LENGTH_SHORT).show()
                navController.navigate(Pantallas.RemindScreen.route)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Cambios")
        }
    }
}
