package com.example.navigationdemo

sealed class NavRoutes(val route: String) {
    object Home: NavRoutes("Home")
    object Welcome: NavRoutes("Welcome")
    object Profile: NavRoutes("Profile")
}