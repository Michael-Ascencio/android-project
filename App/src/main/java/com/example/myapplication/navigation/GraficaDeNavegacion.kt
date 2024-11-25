package com.example.myapplication.navigation

import android.app.Activity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication.screens.CreateRemindScreen
import com.example.myapplication.screens.CreateSavingScreen
import com.example.myapplication.screens.EditRemindScreen
import com.example.myapplication.screens.EditSavingScreen
import com.example.myapplication.screens.FavoriteScreen
import com.example.myapplication.screens.GraphScreen
import com.example.myapplication.screens.HistoryScreen
import com.example.myapplication.screens.LockScreen
import com.example.myapplication.screens.ProgramSavingScreen
import com.example.myapplication.screens.RemindScreen
import com.example.myapplication.screens.SavingScreen
import com.example.myapplication.screens.WastebasketScreen
import com.example.myapplication.viewmodel.ViewModelDatabase

@Composable
fun GraficaDeNavegacion(viewModel: ViewModelDatabase,
                        navController: NavHostController,
                        innerPadding: PaddingValues,
                        auth: Boolean)
{
    NavHost(navController = navController,
        startDestination = Pantallas.RemindScreen.route)

    {
        composable(Pantallas.RemindScreen.route) {
            RemindScreen(viewModel,
                innerPadding = innerPadding,
                navController = navController,)
        }

        composable(Pantallas.SavingScreen.route) {
            SavingScreenWrapper(viewModel,
                auth = auth,
                innerPadding = innerPadding,
                navController = navController)
        }

        composable(Pantallas.CreateSavingScreen.route) {
            CreateSavingScreen(viewModel, innerPadding = innerPadding, navController = navController)
        }

        composable(Pantallas.CreateReminderScreen.route) {
            CreateRemindScreen(viewModel, innerPadding = innerPadding, navController = navController)
        }

        composable(
            "editar_recordatorio/{id}/{titulo}/{repetir}/{imagen}/{descripcion_corta}/{descripcion_larga}/{frecuencia}/{fecha}/{hora}/{favorito}/{borrado}",
            arguments = listOf(
            navArgument("id") { type = NavType.IntType },
            navArgument("titulo") { type = NavType.StringType },
            navArgument("repetir") { type = NavType.BoolType },
            navArgument("imagen") { type = NavType.StringType },
            navArgument("descripcion_corta") { type = NavType.StringType },
            navArgument("descripcion_larga") { type = NavType.StringType },
            navArgument("frecuencia") { type = NavType.StringType },
            navArgument("fecha") { type = NavType.StringType },
            navArgument("hora") { type = NavType.StringType },
            navArgument("favorito") { type = NavType.BoolType },
            navArgument("borrado") { type = NavType.BoolType },)) {
            EditRemindScreen(viewModel,
                innerPadding = innerPadding,
                navController = navController,
                it.arguments!!.getInt("id"),
                it.arguments!!.getString("titulo"),
                it.arguments!!.getBoolean("repetir"),
                it.arguments!!.getString("imagen"),
                it.arguments!!.getString("descripcion_corta"),
                it.arguments!!.getString("descripcion_larga"),
                it.arguments!!.getString("frecuencia"),
                it.arguments!!.getString("fecha"),
                it.arguments!!.getString("hora"),
                it.arguments!!.getBoolean("favorito"),
                it.arguments!!.getBoolean("borrado"))
        }

        composable(
            "editar_ahorro/{id}/{titulo}/{frecuencia}/{imagen}/{descripcion_corta}/{descripcion_larga}/{monto_objetivo}/{monto_actual}/{fecha_inicial}/{fecha_limite}/{hora}/{favorito}/{borrado}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("titulo") { type = NavType.StringType },
                navArgument("frecuencia") { type = NavType.StringType },
                navArgument("imagen") { type = NavType.StringType },
                navArgument("descripcion_corta") { type = NavType.StringType },
                navArgument("descripcion_larga") { type = NavType.StringType },
                navArgument("monto_objetivo") { type = NavType.StringType },
                navArgument("monto_actual") { type = NavType.StringType },
                navArgument("fecha_inicial") { type = NavType.StringType },
                navArgument("fecha_limite") { type = NavType.StringType },
                navArgument("hora") { type = NavType.StringType },
                navArgument("favorito") { type = NavType.BoolType },
                navArgument("borrado") { type = NavType.BoolType }
            )
        ) {
            EditSavingScreen(
                viewModel = viewModel,
                innerPadding = innerPadding,
                navController = navController,
                id = it.arguments!!.getInt("id"),
                titulo = it.arguments!!.getString("titulo")!!,
                frecuencia = it.arguments!!.getString("frecuencia")!!,
                imagen = it.arguments!!.getString("imagen")!!,
                descripcionCorta = it.arguments!!.getString("descripcion_corta")!!,
                descripcionLarga = it.arguments!!.getString("descripcion_larga")!!,
                montoObjetivo = it.arguments!!.getString("monto_objetivo")!!,
                montoActual = it.arguments!!.getString("monto_actual")!!,
                fechaInicial = it.arguments!!.getString("fecha_inicial")!!,
                fechaLimite = it.arguments!!.getString("fecha_limite")!!,
                hora = it.arguments!!.getString("hora")!!,
                favorito = it.arguments!!.getBoolean("favorito"),
                borrado = it.arguments!!.getBoolean("borrado")
            )
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
fun SavingScreenWrapper(viewModel: ViewModelDatabase, auth: Boolean, innerPadding: PaddingValues, navController: NavController) {
    if (auth) {
        SavingScreen(viewModel, innerPadding = innerPadding, navController = navController)
    } else {
        // Mostrar algún mensaje o redirigir a la pantalla de autenticación
        LockScreen(innerPadding = innerPadding)
    }
}