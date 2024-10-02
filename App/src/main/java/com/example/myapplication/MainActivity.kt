package com.example.myapplication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.GraficaDeNavegacion
import com.example.myapplication.navigation.NavBarBody
import com.example.myapplication.navigation.NavBarHeader
import com.example.myapplication.navigation.NavigationItem
import com.example.myapplication.navigation.Pantallas
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private var canAuthenticate = false
    //lateinit se usara para inicializarlo mas tarde y tener un monitoreo
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private fun setupAuth(){
        if(BiometricManager.from(this).canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            == BIOMETRIC_SUCCESS){
            canAuthenticate = true

            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autentificación Biometrica")
                .setSubtitle("Ingresa con tu huella o contraseña")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build()
        }
    }

    private fun authenticate(auth: (auth: Boolean) -> Unit) {
        if (canAuthenticate) {
            BiometricPrompt(this, ContextCompat.getMainExecutor(this),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)

                        auth(true)
                    }
                }).authenticate(promptInfo)
        } else {
            auth(true)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                var auth by remember { mutableStateOf(false) } // Mueve esto dentro del bloque de composición

                //Lista de opciones que se veran reflejados en el menú
                val items = listOf(
                    NavigationItem(
                        title = { Text(text = stringResource(R.string.home)) },
                        route = Pantallas.Home.route,
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                    ),
                    NavigationItem(
                        title = { Text(text = stringResource(R.string.remind)) },
                        route = Pantallas.Home2.route,
                        selectedIcon = Icons.AutoMirrored.Filled.Send,
                        unselectedIcon = Icons.AutoMirrored.Outlined.Send,
                    ),
                    NavigationItem(
                        title = { Text(text = stringResource(R.string.favorite)) },
                        route = Pantallas.Home3.route,
                        selectedIcon = Icons.Filled.FavoriteBorder,
                        unselectedIcon = Icons.Outlined.FavoriteBorder,
                    ),
                    NavigationItem(
                        title = { Text(text = stringResource(R.string.saving)) },
                        route = Pantallas.Home4.route,
                        selectedIcon = Icons.Filled.DateRange,
                        unselectedIcon = Icons.Outlined.DateRange,
                    ),NavigationItem(
                        title = { Text(text = stringResource(R.string.history)) },
                        route = "",
                        selectedIcon = Icons.AutoMirrored.Filled.List,
                        unselectedIcon = Icons.AutoMirrored.Outlined.List,
                    ),
                    NavigationItem(
                        title = { Text(text = stringResource(R.string.graph)) },
                        route = "",
                        selectedIcon = Icons.AutoMirrored.Filled.List,
                        unselectedIcon = Icons.AutoMirrored.Outlined.List,
                    ),
                    NavigationItem(
                        title = { Text(text = stringResource(R.string.program_saving)) },
                        route = "",
                        selectedIcon = Icons.Filled.DateRange,
                        unselectedIcon = Icons.Outlined.DateRange,
                    ),NavigationItem(
                        title = { Text(text = stringResource(R.string.wastebasket)) },
                        route = "",
                        selectedIcon = Icons.Filled.Delete,
                        unselectedIcon = Icons.Outlined.Delete,
                    ),
                    NavigationItem(
                        title = { Text(text = stringResource(R.string.log_out)) },
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
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(340.dp) //Altura de la pantalla
                        ) {
                            // Centrado vertical del contenido dentro del menú
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(horizontal = 16.dp), // Padding opcional para separación lateral
                                verticalArrangement = Arrangement.Center, // Centra el contenido verticalmente
                                horizontalAlignment = Alignment.Start // Mantiene el contenido alineado a la izquierda
                            ) {
                                NavBarHeader(onImageClick = {
                                    // Acción a realizar al hacer clic en la imagen
                                    scope.launch {
                                        drawerState.close()
                                    }
                                })
                                Spacer(modifier = Modifier.height(12.dp))
                                NavBarBody(items = items, currentRoute = currentRoute) { currentNavigationItem ->
                                    navController.navigate(currentNavigationItem.route) {
                                        navController.graph.startDestinationRoute?.let { route ->
                                            popUpTo(route) {
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
                        }
                    }, drawerState = drawerState) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        // Coloca los botones de Recordatorio y Ahorro con espacio equitativo
                                        Row(modifier = Modifier.weight(1f)) {
                                            IconButton(onClick = { /* Acción de Recordatorio */ }, modifier = Modifier.weight(1f)) {
                                                Text(text = "Recordatorio")
                                            }
                                            IconButton(onClick = {
                                                if(auth) {
                                                    auth = false
                                                }else{
                                                    authenticate {
                                                        auth = it
                                                    }
                                                }

                                            }, modifier = Modifier.weight(1f)) {
                                                Text(text = "Ahorro")
                                            }
                                        }
                                        // Coloca el icono de perfil a la derecha
                                        IconButton(onClick = { /* Acción de perfil */ }) {
                                            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Perfil")
                                        }
                                    }
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
                    ) { innerPadding: PaddingValues ->
                        GraficaDeNavegacion(navController = navController, innerPadding = innerPadding)
                    }
                }
            }
        }
    }
}
