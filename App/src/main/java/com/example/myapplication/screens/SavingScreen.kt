package com.example.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.navigation.Pantallas
import com.example.myapplication.viewmodel.ViewModelReminds

@Composable
fun SavingScreen(
    viewModel: ViewModelReminds,
    innerPadding: PaddingValues,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color.White), // Fondo blanco para que coincida con el mock
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Primera imagen superior
        Image(
            painter = painterResource(id = R.drawable.ahorroinicio), // Imagen de la pantalla donde estamos (Rcordatorio o Ahorro)
            contentDescription = "Ahorros",
            modifier = Modifier
                .size(150.dp)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )

        // Texto de título "Ahorros"
        Text(
            text = "Ahorros",
            fontSize = 30.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Segunda imagen inferior
        Image(
            painter = painterResource(id = R.drawable.sinahorrosimagen), // Imagen si no hay nada
            contentDescription = "No tienes ahorros",
            modifier = Modifier
                .size(250.dp)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Texto informativo
        Text(
            text = "Actualmente no tienes ahorros",
            fontSize = 18.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para crear ahorro
        Button(
            onClick = {navController.navigate(Pantallas.CreateSavingScreen.route)},
            modifier = Modifier
                .padding(8.dp)
                .width(150.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(Color(0xFFE0A53E)) // Cambia el color deo botón
        ) {
            Text(text = "Crear Uno", color = Color.White)
        }
    }
}