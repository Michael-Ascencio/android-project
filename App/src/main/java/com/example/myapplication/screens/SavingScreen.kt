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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.navigation.Pantallas
import com.example.myapplication.viewmodel.ViewModelDatabase

@Composable
fun SavingScreen(
    viewModel: ViewModelDatabase,
    innerPadding: PaddingValues,
    navController: NavController
) {
    val state = viewModel.savingState.value // Obtiene el estado del ViewModel para ahorros

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
                painter = painterResource(id = R.drawable.ahorroinicio),
                contentDescription = "Ahorros",
                modifier = Modifier
                    .size(150.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )

            // Título de la pantalla
            Text(
                text = "Ahorros",
                fontSize = 30.sp,
                color = Color.Black
            )

            // Condicional para mostrar ahorros o mensaje
            if (state.listaAhorros.isEmpty()) {
                NoAhorrosView(navController)
            } else {
                ContentSavingScreen(innerPadding, navController, viewModel)
            }
        }

        // Botón flotante para agregar ahorros
        FloatingActionButton(
            onClick = {
                navController.navigate(Pantallas.CreateSavingScreen.route) {
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Agregar Ahorro")
        }
    }
}

@Composable
fun NoAhorrosView(navController: NavController) {
    Image(
        painter = painterResource(id = R.drawable.sinahorrosimagen),
        contentDescription = "No tienes ahorros",
        modifier = Modifier
            .size(250.dp)
            .padding(8.dp),
        contentScale = ContentScale.Crop
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "Actualmente no tienes ahorros",
        fontSize = 18.sp,
        color = Color.Black
    )

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = { navController.navigate(Pantallas.CreateSavingScreen.route) },
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
fun ContentSavingScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    viewModel: ViewModelDatabase
) {
    val state = viewModel.savingState.value

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(0.dp),
            verticalArrangement = Arrangement.Top
        ) {
            items(state.listaAhorros.size) { index ->
                val ahorro = state.listaAhorros[index]
                val showMenu = remember { mutableStateOf(false) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color(0xFFB2EBF2), RoundedCornerShape(8.dp))
                        .clickable {
                            // Acción al hacer clic en el ahorro
                        }
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            if (ahorro.imagen == "")
                                R.drawable.placeholder
                            else
                                ahorro.imagen
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
                            text = ahorro.titulo,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = ahorro.descripcion_corta,
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
                                text = "${ahorro.fecha_inicial} • ${ahorro.hora}",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            val nuevoAhorro = ahorro.copy(favorito = !ahorro.favorito)
                            viewModel.actualizarAhorro(nuevoAhorro)
                        }
                    ) {
                        Icon(
                            imageVector = if (ahorro.favorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (ahorro.favorito) Color.Red else Color.Gray
                        )
                    }

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
                                navController.navigate("editar_ahorro/${ahorro.id}/${ahorro.titulo}/${ahorro.frecuencia}/${ahorro.imagen}/${ahorro.descripcion_corta}/${ahorro.descripcion_larga}/${ahorro.monto_objetivo}/${ahorro.monto_actual}/${ahorro.fecha_inicial}/${ahorro.fecha_limite}/${ahorro.hora}/${ahorro.favorito}/${ahorro.borrado}")
                            },
                            text = { Text("Editar") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Editar",
                                    tint = Color.Gray
                                )
                            }
                        )
                        DropdownMenuItem(
                            onClick = {
                                showMenu.value = false
                                viewModel.eliminarAhorro(ahorro)
                            },
                            text = { Text("Eliminar") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar",
                                    tint = Color.Gray
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
