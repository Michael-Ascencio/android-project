package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.automirrored.outlined.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.GraficaDeNavegacion
import com.example.myapplication.navigation.NavBarBody
import com.example.myapplication.navigation.NavBarHeader
import com.example.myapplication.navigation.NavigationItem
import com.example.myapplication.navigation.Pantallas
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                //Lista de opciones que se veran reflejados en el menú
                val items = listOf(
                    NavigationItem(
                        title = "Home",
                        route = Pantallas.Home.route,
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                    ),
                    NavigationItem(
                        title = "Home",
                        route = Pantallas.Home2.route,
                        selectedIcon = Icons.AutoMirrored.Filled.Send,
                        unselectedIcon = Icons.AutoMirrored.Outlined.Send,
                    ),
                    NavigationItem(
                        title = "Home",
                        route = Pantallas.Home3.route,
                        selectedIcon = Icons.Filled.FavoriteBorder,
                        unselectedIcon = Icons.Outlined.FavoriteBorder,
                    ),
                    NavigationItem(
                        title = "Home",
                        route = "",
                        selectedIcon = Icons.Filled.DateRange,
                        unselectedIcon = Icons.Outlined.DateRange,
                    ),NavigationItem(
                        title = "Home",
                        route = "",
                        selectedIcon = Icons.AutoMirrored.Filled.List,
                        unselectedIcon = Icons.AutoMirrored.Outlined.List,
                    ),
                    NavigationItem(
                        title = "Home",
                        route = "",
                        selectedIcon = Icons.AutoMirrored.Filled.List,
                        unselectedIcon = Icons.AutoMirrored.Outlined.List,
                    ),
                    NavigationItem(
                        title = "Home",
                        route = "",
                        selectedIcon = Icons.Filled.DateRange,
                        unselectedIcon = Icons.Outlined.DateRange,
                    ),NavigationItem(
                        title = "Home",
                        route = "",
                        selectedIcon = Icons.Filled.Delete,
                        unselectedIcon = Icons.Outlined.Delete,
                    ),
                    NavigationItem(
                        title = "Home",
                        route = "",
                        selectedIcon = Icons.AutoMirrored.Filled.ExitToApp,
                        unselectedIcon = Icons.AutoMirrored.Outlined.ExitToApp
                    ),
                )
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                //Parametro para que el menu quede subrallado en la opción que esté
                val currentRoute = navBackStackEntry?.destination?.route

                val topbarTitle =
                    if(currentRoute != null){
                        items[items.indexOfFirst {
                            it.route == currentRoute
                        }].title
                    }
                    else{
                        items[0].title
                    }
                //El espacio donde se muestra el menú desplegable con modal Drawer
                ModalNavigationDrawer(
                    gesturesEnabled = drawerState.isOpen, drawerContent = {
                    ModalDrawerSheet(
                        
                    ){
                        NavBarHeader()
                        Spacer(modifier = Modifier.height(12.dp))
                        //Uso del parametro Curren Route para que el menu quede subrallado con la opción
                        NavBarBody(items = items, currentRoute = currentRoute) {
                            currentNavigationItem -> navController.navigate(currentNavigationItem.route){
                                navController.graph.startDestinationRoute?.let {
                                        route -> popUpTo(route){
                                            saveState = true
                                        }
                                }
                            launchSingleTop = true
                            restoreState = true
                            }
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    }
                }, drawerState = drawerState) {
                Scaffold(
                        topBar ={
                            TopAppBar(title = {
                                Text(text = topbarTitle)
                            },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    }) {
                                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                                        
                                    }
                                }
                            )
                        }
                    ){ innerPadding: PaddingValues ->
                        GraficaDeNavegacion(navController = navController, innerPadding = innerPadding)
                    }
                }
            }
        }
    }
}
