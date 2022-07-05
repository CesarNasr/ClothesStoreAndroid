package com.example.clothesstoreapp.ui.composeuisample

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val badgeCount : Int = 10
) {
    object Catalogue : BottomBarScreen(
        route = "catalogue",
        title = "Catalogue",
        icon = Icons.Default.List
    )

    object Wishlist : BottomBarScreen(
        route = "wishlist",
        title = "Wishlist",
        icon = Icons.Default.Favorite
    )

    object Basket : BottomBarScreen(
        route = "basket",
        title = "Basket",
        icon = Icons.Default.ShoppingCart
    )
}