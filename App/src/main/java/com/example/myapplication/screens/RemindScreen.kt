package com.example.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.myapplication.navigation.Pantallas
import com.example.myapplication.viewmodel.ViewModelReminds

@Composable
fun RemindScreen(
    viewModel: ViewModelReminds,
    innerPadding: PaddingValues,
    navController: NavController
) {
    val state = viewModel.state.value // Obtiene el estado del ViewModel

    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Imagen superior
            Image(
                painter = painterResource(id = R.drawable.recordatorioinicio),
                contentDescription = "Recordatorios",
                modifier = Modifier
                    .size(150.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )

            // Titulo de la pantalla
            Text(
                text = "Recordatorio",
                fontSize = 30.sp,
                color = Color.Black
            )

            // Condicional para mostrar recordatorios o mensaje
            if (state.listaRecordatorios.isEmpty()) {
                NoRecordatoriosView(navController)
            } else {
                ContentRemindScreen(innerPadding, navController, viewModel)
            }
        }

        // Botón flotante para agregar recordatorios
        if (state.listaRecordatorios.isNotEmpty()) {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Pantallas.CreateReminderScreen.route) {
                        // Añadir opciones para evitar la pantalla intermedia
                        launchSingleTop = true // Evita la creación de múltiples instancias
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .background(Color.Transparent) // Cambia el color del botón flotante
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Recordatorio")
            }
        }
    }
}

@Composable
fun NoRecordatoriosView(navController: NavController) {
    // Segunda imagen inferior
    Image(
        painter = painterResource(id = R.drawable.sinrecordatoriosimagen),
        contentDescription = "No tienes recordatorios",
        modifier = Modifier
            .size(250.dp)
            .padding(8.dp),
        contentScale = ContentScale.Crop
    )

    Spacer(modifier = Modifier.height(8.dp))

    // Texto informativo
    Text(
        text = "Actualmente no tienes recordatorios",
        fontSize = 18.sp,
        color = Color.Black
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Botón para crear ahorro
    Button(
        onClick = { navController.navigate(Pantallas.CreateReminderScreen.route) },
        modifier = Modifier
            .padding(8.dp)
            .width(150.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(Color(0xFFE0A53E))
    ) {
        Text(text = "Crear Uno", color = Color.White)
    }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun ContentRemindScreen(it: PaddingValues, navController: NavController, viewModel: ViewModelReminds) {
    val state = viewModel.state.value

    Column(modifier = Modifier.padding(it)) {
        LazyColumn {
            items(state.listaRecordatorios.size) { index ->
                val recordatorio = state.listaRecordatorios[index]
                val showMenu = remember { mutableStateOf(false) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color(0xFFF3E5F5), RoundedCornerShape(8.dp))
                        .clickable {
                            // Acción al hacer clic en el recordatorio
                        }
                ) {
                    // Carga de imagen usando Coil
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = recordatorio.imagen ?: R.drawable.placeholder, // Usa la imagen por defecto si `imagen` es null
                            error = painterResource(id = R.drawable.error),  // Imagen de error
                        ),

                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Gray)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = recordatorio.titulo,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        //Se debe eliminar, es para ver el valor de imagen
                        Text(
                            text = recordatorio.imagen + " Está es la ruta de imagen",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Text(
                            text = recordatorio.descripcion,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Fecha",
                                tint = Color.Gray,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${recordatorio.fecha} • ${recordatorio.hora}",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    // Ícono de favorito
                    IconButton(
                        onClick = { /* Acción para marcar como favorito */ }
                    ) {
                        Icon(
                            imageVector = if (recordatorio.favorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (recordatorio.favorito) Color.Red else Color.Gray
                        )
                    }

                    //Menú desplegable para editar y eliminar
                    IconButton(onClick = { showMenu.value = true }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Más opciones")
                    }

                    DropdownMenu(
                        expanded = showMenu.value,
                        onDismissRequest = { showMenu.value = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                showMenu.value = false
                                navController.navigate("editar_recordatorio/${recordatorio.id}/${recordatorio.titulo}/${recordatorio.descripcion}/${recordatorio.fecha}/${recordatorio.hora}")
                            },
                            text = { Text("Editar") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Edit, // Usa el ícono de edición
                                    contentDescription = "Editar",
                                    tint = Color.Gray // Cambia el color si deseas un estilo específico
                                )
                            }
                        )
                        DropdownMenuItem(
                            onClick = {
                                showMenu.value = false
                                viewModel.eliminarRecordatorio(recordatorio)
                            },
                            text = { Text("Eliminar") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Delete, // Usa el ícono de eliminación
                                    contentDescription = "Eliminar",
                                    tint = Color.Gray // Cambia el color si deseas un estilo específico
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}