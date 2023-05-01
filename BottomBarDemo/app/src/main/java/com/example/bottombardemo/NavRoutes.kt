package com.example.bottombardemo

sealed class NavRoutes(val route: String) {
    object Home: NavRoutes("Home")
    object Contacts: NavRoutes("Contacts")
    object Favorites: NavRoutes("Favorites")
}