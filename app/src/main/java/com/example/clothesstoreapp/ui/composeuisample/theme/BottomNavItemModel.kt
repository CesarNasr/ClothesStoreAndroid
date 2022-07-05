package com.example.clothesstoreapp.ui.composeuisample.theme

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItemModel(
    val name : String,
    val route : String,
    val icon : ImageVector,
    val badgeCount : Int = 10
)
