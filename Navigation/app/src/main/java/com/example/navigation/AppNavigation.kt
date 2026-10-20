package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigation.screens.ListScreen
import com.example.navigation.screens.ReadyScreen
import com.example.navigation.screens.TextDetailScreen
import com.example.navigation.screens.TextFieldScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController , startDestination ="ready"){
        composable("ready") {
            ReadyScreen(onReadyClick = { navController.navigate("list") })
        }
        composable("list"){
            ListScreen(onItemClick = { route -> navController.navigate(route) })
        }
        composable("text"){
            TextDetailScreen(onBack = { navController.popBackStack() })
        }
        composable("textfield"){
            TextFieldScreen(onBack ={navController.popBackStack()})
        }
    }
}