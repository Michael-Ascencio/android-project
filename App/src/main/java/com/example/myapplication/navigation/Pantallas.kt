package com.example.myapplication.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Pantallas(var route: String){

    object RemindScreen : Pantallas("recordatorio")

    object SavingScreen : Pantallas("ahorro")

    object CreateReminderScreen : Pantallas("creador_de_recordatorios")

    object CreateSavingScreen : Pantallas("creador_de_ahorros")

    object EditSavingScreen : Pantallas("Editar_ahorro")

    object FavoriteScreen : Pantallas("favoritos")

    object GraphScreen : Pantallas("graph")

    object HistoryScreen : Pantallas("historial")

    object ProgramSavingScreen : Pantallas("programar_ahorro")

    object WastebasketScreen : Pantallas("papelera")

    object ExitScreen : Pantallas("exit")
}