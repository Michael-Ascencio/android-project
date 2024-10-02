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
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.GraficaDeNavegacion
import com.example.myapplication.navigation.NavBarBody
import com.example.myapplication.navigation.NavBarHeader
import com.example.myapplication.navigation.NavigationItem
import com.example.myapplication.navigation.Pantallas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var canAuthenticate = false
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private fun setupAuth() {
        if (BiometricManager.from(this).canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            ) == BIOMETRIC_SUCCESS
        ) {
            canAuthenticate = true

            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación Biométrica")
                .setSubtitle("Ingresa con tu huella o contraseña")
                .setAllowedAuthenticators(
                    BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                )
                .build()
        }
    }

    private fun authenticate(authCallback: (Boolean) -> Unit) {
        if (canAuthenticate) {
            BiometricPrompt(this, ContextCompat.getMainExecutor(this),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        authCallback(true)
                    }
                }).authenticate(promptInfo)
        } else {
            authCallback(true)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setupAuth()
        setContent {
            MyApplicationTheme {
                MyAppContent(authenticate = { authCallback ->
                    authenticate(authCallback)
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppContent(authenticate: (authCallback: (Boolean) -> Unit) -> Unit) {
    var auth by remember { mutableStateOf(false) }

    // Lista de opciones del menú
    val items = listOf(
        NavigationItem(
            title = { Text(text = "Home") },
            route = Pantallas.Home.route,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        NavigationItem(
            title = { Text(text = "Remind") },
            route = Pantallas.Home2.route,
            selectedIcon = Icons.AutoMirrored.Filled.Send,
            unselectedIcon = Icons.AutoMirrored.Outlined.Send,
        ),
        NavigationItem(
            title = { Text(text = "Favorite") },
            route = Pantallas.Home3.route,
            selectedIcon = Icons.Filled.FavoriteBorder,
            unselectedIcon = Icons.Outlined.FavoriteBorder,
        ),
        NavigationItem(
            title = { Text(text = "Saving") },
            route = Pantallas.Home4.route,
            selectedIcon = Icons.Filled.DateRange,
            unselectedIcon = Icons.Outlined.DateRange,
        ),
        NavigationItem(
            title = { Text(text = "History") },
            route = "",
            selectedIcon = Icons.AutoMirrored.Filled.List,
            unselectedIcon = Icons.AutoMirrored.Outlined.List,
        ),
        NavigationItem(
            title = { Text(text = "Graph") },
            route = "",
            selectedIcon = Icons.AutoMirrored.Filled.List,
            unselectedIcon = Icons.AutoMirrored.Outlined.List,
        ),
        NavigationItem(
            title = { Text(text = "Program Saving") },
            route = "",
            selectedIcon = Icons.Filled.DateRange,
            unselectedIcon = Icons.Outlined.DateRange,
        ),
        NavigationItem(
            title = { Text(text = "Wastebasket") },
            route = "",
            selectedIcon = Icons.Filled.Delete,
            unselectedIcon = Icons.Outlined.Delete,
        ),
        NavigationItem(
            title = { Text(text = "Log Out") },
            route = "",
            selectedIcon = Icons.AutoMirrored.Filled.ExitToApp,
            unselectedIcon = Icons.AutoMirrored.Outlined.ExitToApp
        ),
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val topbarTitle = items.find { it.route == currentRoute }?.title ?: items[0].title

    ModalNavigationDrawer(
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            DrawerContent(
                items = items,
                currentRoute = currentRoute,
                drawerState = drawerState,
                scope = scope,
                navController = navController
            )
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(
                    auth = auth,
                    onAuthenticate = { shouldAuthenticate ->
                        if (shouldAuthenticate) {
                            authenticate { success ->
                                auth = success
                            }
                        } else {
                            auth = false
                        }
                    },
                    scope = scope,
                    drawerState = drawerState
                )
            }
        ) { innerPadding ->
            GraficaDeNavegacion(navController = navController, innerPadding = innerPadding)
        }
    }
}

@Composable
fun DrawerContent(
    items: List<NavigationItem>,
    currentRoute: String?,
    drawerState: DrawerState,
    scope: CoroutineScope,
    navController: NavController
) {
    ModalDrawerSheet(
        modifier = Modifier
            .fillMaxHeight()
            .width(340.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            NavBarHeader(onImageClick = {
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    auth: Boolean,
    onAuthenticate: (Boolean) -> Unit,
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Botones de Recordatorio y Ahorro
                Row(modifier = Modifier.weight(1f)) {
                    IconButton(
                        onClick = { /* Acción de Recordatorio */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Recordatorio")
                    }
                    IconButton(
                        onClick = {
                            if (auth) {
                                onAuthenticate(false)
                            } else {
                                onAuthenticate(true)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Ahorro")
                    }
                }
                // Icono de perfil a la derecha
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
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menú")
            }
        }
    )
}
