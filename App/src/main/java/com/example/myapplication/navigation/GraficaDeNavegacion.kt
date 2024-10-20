package com.example.myapplication.navigation

import android.app.Activity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.screens.CreateRemindScreen
import com.example.myapplication.screens.CreateSavingScreen
import com.example.myapplication.screens.FavoriteScreen
import com.example.myapplication.screens.GraphScreen
import com.example.myapplication.screens.HistoryScreen
import com.example.myapplication.screens.LockScreen
import com.example.myapplication.screens.ProgramSavingScreen
import com.example.myapplication.screens.RemindScreen
import com.example.myapplication.screens.SavingScreen
import com.example.myapplication.screens.WastebasketScreen
import com.example.myapplication.viewmodel.RecordatoriosViewModel

@Composable
fun GraficaDeNavegacion(viewModel: RecordatoriosViewModel,
                        navController: NavHostController,
                        innerPadding: PaddingValues,
                        auth: Boolean)
{
    NavHost(navController = navController,
        startDestination = Pantallas.RemindScreen.route)

    {
        composable(Pantallas.RemindScreen.route) {
            RemindScreen(viewModel, innerPadding = innerPadding, navController = navController,)
        }

        composable(Pantallas.SavingScreen.route) {
            SavingScreenWrapper(viewModel, auth = auth, innerPadding = innerPadding, navController = navController)
        }

        composable(Pantallas.CreateSavingScreen.route) {
            CreateSavingScreen(viewModel, innerPadding = innerPadding, navController = navController)
        }

        composable(Pantallas.CreateReminderScreen.route) {
            CreateRemindScreen(viewModel, innerPadding = innerPadding, navController = navController)
        }

        composable(Pantallas.FavoriteScreen.route) {
            FavoriteScreen(viewModel, innerPadding = innerPadding)
        }

        composable(Pantallas.GraphScreen.route) {
            GraphScreen(innerPadding = innerPadding)
        }

        composable(Pantallas.HistoryScreen.route) {
            HistoryScreen(viewModel, innerPadding = innerPadding)
        }

        composable(Pantallas.ProgramSavingScreen.route) {
            ProgramSavingScreen(viewModel, innerPadding = innerPadding)
        }

        composable(Pantallas.WastebasketScreen.route) {
            WastebasketScreen(viewModel, innerPadding = innerPadding)
        }

        composable(Pantallas.ExitScreen.route) {
            ExitApp()
        }
    }
}

@Composable
fun ExitApp() {
    // Lógica para cerrar la aplicación
    (LocalContext.current as? Activity)?.finish()
}

@Composable
fun SavingScreenWrapper(viewModel: RecordatoriosViewModel, auth: Boolean, innerPadding: PaddingValues, navController: NavController) {
    if (auth) {
        SavingScreen(viewModel, innerPadding = innerPadding, navController = navController)
    } else {
        // Mostrar algún mensaje o redirigir a la pantalla de autenticación
        LockScreen(innerPadding = innerPadding)
    }
}