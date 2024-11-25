package com.example.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.models.Reminds
import com.example.myapplication.viewmodel.ViewModelDatabase
import com.example.myapplication.models.Saving

@Composable
fun FavoriteScreen(viewModel: ViewModelDatabase, innerPadding: PaddingValues) {
    val favoritosStateRecordatorio = viewModel.favoritosStateRecordatorio.value // Obtiene los recordatorios favoritos
    val favoritosStateAhorro = viewModel.favoritosStateAhorro.value // Obtiene los ahorros favoritos

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Título de la pantalla
        Text(
            text = "Favoritos",
            fontSize = 30.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar los recordatorios favoritos
        if (favoritosStateRecordatorio.isEmpty() && favoritosStateAhorro.isEmpty()) {
            NoFavoritosView()
        } else {
            // Mostrar Recordatorios Favoritos
            if (favoritosStateRecordatorio.isNotEmpty()) {
                Text(
                    text = "Recordatorios Favoritos",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    items(favoritosStateRecordatorio.size) { index ->
                        val recordatorio = favoritosStateRecordatorio[index]
                        RecordatorioFavoritoItem(recordatorio, viewModel)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar Ahorros Favoritos
            if (favoritosStateAhorro.isNotEmpty()) {
                Text(
                    text = "Ahorros Favoritos",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    items(favoritosStateAhorro.size) { index ->
                        val ahorro = favoritosStateAhorro[index]
                        AhorroFavoritoItem(ahorro, viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun NoFavoritosView() {
    // Imagen si no hay favoritos
    Image(
        painter = painterResource(id = R.drawable.sinfavoritos),
        contentDescription = "No tienes favoritos",
        modifier = Modifier
            .size(250.dp)
            .padding(8.dp),
        contentScale = ContentScale.Crop
    )

    Spacer(modifier = Modifier.height(8.dp))

    // Texto informativo
    Text(
        text = "Actualmente no tienes nada en favoritos",
        fontSize = 18.sp,
        color = Color.Black
    )

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun RecordatorioFavoritoItem(recordatorio: Reminds, viewModel: ViewModelDatabase) {
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
        // Carga de imagen usando Coil (si tiene una imagen asociada)
        Image(
            painter = rememberAsyncImagePainter(
                if (recordatorio.imagen == "")
                    R.drawable.placeholder
                else
                    recordatorio.imagen,
                error = painterResource(id = R.drawable.error) // Imagen de error
            ),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Detalles del recordatorio (Título y Descripción)
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
            Text(
                text = recordatorio.descripcion_corta,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${recordatorio.fecha} • ${recordatorio.hora}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        // Ícono de favorito
        IconButton(
            onClick = {
                // Alterna el valor del favorito
                val nuevoRecordatorio = recordatorio.copy(favorito = !recordatorio.favorito)
                viewModel.actualizarRecordatorio(nuevoRecordatorio)
            }
        ) {
            Icon(
                imageVector = if (recordatorio.favorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorito",
                tint = if (recordatorio.favorito) Color.Red else Color.Gray
            )
        }

        // Menú de opciones
        IconButton(onClick = { showMenu.value = true }) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Más opciones")
        }

        // Menú desplegable para editar y eliminar
        DropdownMenu(
            expanded = showMenu.value,
            onDismissRequest = { showMenu.value = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    showMenu.value = false
                    viewModel.eliminarRecordatorio(recordatorio)
                },
                text = { Text("Eliminar") }
            )
        }
    }
}

@Composable
fun AhorroFavoritoItem(ahorro: Saving, viewModel: ViewModelDatabase) {
    val showMenu = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFF3E5F5), RoundedCornerShape(8.dp))
            .clickable {
                // Acción al hacer clic en el ahorro
            }
    ) {
        // Carga de imagen usando Coil (si tiene una imagen asociada)
        Image(
            painter = rememberAsyncImagePainter(
                if (ahorro.imagen == "")
                    R.drawable.placeholder
                else
                    ahorro.imagen,
                error = painterResource(id = R.drawable.error) // Imagen de error
            ),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Detalles del ahorro (Nombre y Descripción)
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
                Text(
                    text = "${ahorro.monto_actual} • ${ahorro.fecha_inicial}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        // Ícono de favorito
        IconButton(
            onClick = {
                // Alterna el valor del favorito
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

        // Menú de opciones
        IconButton(onClick = { showMenu.value = true }) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Más opciones")
        }

        // Menú desplegable para editar y eliminar
        DropdownMenu(
            expanded = showMenu.value,
            onDismissRequest = { showMenu.value = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    showMenu.value = false
                    viewModel.eliminarAhorro(ahorro)
                },
                text = { Text("Eliminar") }
            )
        }
    }
}
