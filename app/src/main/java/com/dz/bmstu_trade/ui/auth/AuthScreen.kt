package com.dz.bmstu_trade.ui.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dz.bmstu_trade.navigation.AuthNavHost

@Composable
fun AuthScreen(outerNavController: NavHostController) {
    val navController = rememberNavController()
    AuthNavHost(outerNavController, navController)
}