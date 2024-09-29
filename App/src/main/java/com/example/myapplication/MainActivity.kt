package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.navigation.NavigationItem
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.automirrored.outlined.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.unit.dp
import com.example.myapplication.navigation.NavBarHeader
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val items = listOf(
                    NavigationItem(
                        title = "Home",
                        route = "",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                    ),
                    NavigationItem(
                        title = "Home",
                        route = "",
                        selectedIcon = Icons.AutoMirrored.Filled.Send,
                        unselectedIcon = Icons.AutoMirrored.Outlined.Send,
                    ),
                    NavigationItem(
                        title = "Home",
                        route = "",
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

                ModalNavigationDrawer(drawerContent = {
                    //El espacio donde se muestra el menú desplegable con modal Drawer
                    ModalDrawerSheet {
                        NavBarHeader()
                        Spacer(modifier = Modifier.height(0.dp))
                    }

                }, drawerState = drawerState) {
                    Scaffold(
                        topBar ={
                            TopAppBar(title = {
                                Text(text = "Name")
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
                    ){
                        val paddingValues = it
                    }
                }
            }
        }
    }
}
