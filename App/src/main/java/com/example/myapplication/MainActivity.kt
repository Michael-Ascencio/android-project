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
            title = { Text(text = stringResource(R.string.remind)) },
            route = Pantallas.RemindScreen.route,
            selectedIcon = Icons.AutoMirrored.Filled.Send,
            unselectedIcon = Icons.AutoMirrored.Outlined.Send,
        ),
        NavigationItem(
            title = { Text(text = stringResource(R.string.favorite)) },
            route = Pantallas.FavoriteScreen.route,
            selectedIcon = Icons.Filled.FavoriteBorder,
            unselectedIcon = Icons.Outlined.FavoriteBorder,
        ),
        NavigationItem(
            title = { Text(text = stringResource(R.string.saving)) },
            route = Pantallas.SavingScreen.route, //Toca implementar el auth
            selectedIcon = Icons.Filled.DateRange,
            unselectedIcon = Icons.Outlined.DateRange,
        ),
        NavigationItem(
            title = { Text(text = stringResource(R.string.history)) },
            route = Pantallas.HistoryScreen.route,
            selectedIcon = Icons.AutoMirrored.Filled.List,
            unselectedIcon = Icons.AutoMirrored.Outlined.List,
        ),
        NavigationItem(
            title = { Text(text = stringResource(R.string.graph)) },
            route = Pantallas.GraphScreen.route,
            selectedIcon = Icons.AutoMirrored.Filled.List,
            unselectedIcon = Icons.AutoMirrored.Outlined.List,
        ),
        NavigationItem(
            title = { Text(text = stringResource(R.string.program_saving)) },
            route = Pantallas.ProgramSavingScreen.route,
            selectedIcon = Icons.Filled.DateRange,
            unselectedIcon = Icons.Outlined.DateRange,
        ),
        NavigationItem(
            title = { Text(text = stringResource(R.string.wastebasket)) },
            route = Pantallas.WastebasketScreen.route,
            selectedIcon = Icons.Filled.Delete,
            unselectedIcon = Icons.Outlined.Delete,
        ),
        NavigationItem(
            title = { Text(text = stringResource(R.string.exit)) },
            route = Pantallas.ExitScreen.route,
            selectedIcon = Icons.AutoMirrored.Filled.ExitToApp,
            unselectedIcon = Icons.AutoMirrored.Outlined.ExitToApp
        ),
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

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
                    navController = navController,
                    scope = scope,
                    drawerState = drawerState
                )
            }
        ) { innerPadding ->
            GraficaDeNavegacion(navController = navController, innerPadding = innerPadding, auth = auth)
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
    navController: NavController,
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
                        onClick = { navController.navigate(Pantallas.RemindScreen.route) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Recordatorio")
                    }
                    IconButton(
                        onClick = {
                            if (auth) {
                                onAuthenticate(false) // Cerrar sesión
                                navController.navigate(Pantallas.RemindScreen.route) // Redirigir a Recordatorio
                            } else {
                                onAuthenticate(true) // Iniciar sesión
                                navController.navigate(Pantallas.SavingScreen.route) // Redirigir a Ahorro
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
