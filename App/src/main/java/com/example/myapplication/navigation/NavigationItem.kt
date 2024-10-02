package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title : @Composable () -> Unit,
    val route : String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector,
    val badgeCount : Int? = null
)