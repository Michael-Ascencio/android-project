package com.example.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun GraphScreen(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color.White), // Fondo blanco para que coincida con la imagen
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Texto de título "Gráficas"
        Text(
            text = "Gráficas",
            fontSize = 30.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // imagen inferior
        Image(
            painter = painterResource(id = R.drawable.singraficas), // Imagen si no hay nada
            contentDescription = "No tienes ahorros",
            modifier = Modifier
                .size(250.dp)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Texto informativo
        Text(
            text = "Próximamente",
            fontSize = 18.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

    }
}
