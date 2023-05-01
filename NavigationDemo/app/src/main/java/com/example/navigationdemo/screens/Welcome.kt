package com.example.navigationdemo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.navigationdemo.NavRoutes
import java.time.format.TextStyle

@Composable
fun Welcome(navController: NavHostController, userName: String?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Welcome $userName", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.size(30.dp))
            Button(onClick= {
                navController.navigate(NavRoutes.Profile.route) {
                    popUpTo(NavRoutes.Home.route)
                }
            }) { Text(text = "Set up your profile") }
        }
    }
}
