package com.example.myapplication.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun NavBarHeader(onImageClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp), // Añade un poco de padding
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen circular
        Image(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close",
            modifier = Modifier
                .size(34.dp) // Tamaño de la imagen
                .absolutePadding().clickable(){ //acción para el click de la x
                    onImageClick()
                }
        )

        // Espacio entre la imagen y el cerrar
        Spacer(modifier = Modifier.width(16.dp))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(64.dp) // Tamaño de la imagen
                .clip(CircleShape) // Hace la imagen circular
        )


        // Espacio entre la imagen y el texto
        Spacer(modifier = Modifier.width(16.dp))

        // Nombre y descripción alineados verticalmente
        Column {
            Text(text = "Nombre", style = MaterialTheme.typography.bodyLarge) // Estilo para el texto
            Text(text = "Descripción", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun NavBarBody(
    items: List<NavigationItem>,
    currentRoute: String?,
    onClick: (NavigationItem) -> Unit
) {
    items.forEachIndexed { index, navigationItem ->
        NavigationDrawerItem(
            colors = NavigationDrawerItemDefaults.colors(
                unselectedContainerColor = MaterialTheme.colorScheme.background,
                selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            ),
            label = {
                navigationItem.title() // Llama a title como un Composable
            },
            selected = currentRoute == navigationItem.route,
            onClick = {
                onClick(navigationItem)
            },
            icon = {
                Icon(
                    imageVector = if (currentRoute == navigationItem.route) {
                        navigationItem.selectedIcon
                    } else {
                        navigationItem.unselectedIcon
                    },
                    contentDescription = null
                )
            },
            modifier = Modifier.padding(
                PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            )
        )
    }
}
