package com.example.myapplication.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.screens.HomeScreen
import com.example.myapplication.screens.HomeScreen2
import com.example.myapplication.screens.HomeScreen3
import com.example.myapplication.screens.HomeScreen4

@Composable
fun GraficaDeNavegacion( navController: NavHostController,
                         innerPadding: PaddingValues
                       ){
    NavHost(navController = navController,
        startDestination = Pantallas.Home.route){
            composable(Pantallas.Home.route){
                HomeScreen(innerPadding = innerPadding)
            }
            composable(Pantallas.Home2.route){
                HomeScreen2(innerPadding = innerPadding)
            }
            composable(Pantallas.Home3.route){
                HomeScreen3(innerPadding = innerPadding)
            }
            composable(Pantallas.Home4.route){
                HomeScreen4(innerPadding = innerPadding)
            }
        }
    }