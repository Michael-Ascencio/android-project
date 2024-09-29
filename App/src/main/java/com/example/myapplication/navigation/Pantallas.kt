package com.example.myapplication.navigation

sealed class Pantallas(var route: String){

    object Home : Pantallas("home")

    object Home2 : Pantallas("home2")

    object Home3 : Pantallas("home3")
}