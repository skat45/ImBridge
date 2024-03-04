package com.dz.bmstu_trade.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dz.bmstu_trade.ui.main.MainScreen
import com.dz.bmstu_trade.ui.signin.SignInScreen
import com.dz.bmstu_trade.ui.signup.SignUpScreen

// Внешний граф для того, чтобы отделить основные экраны с BottomNavBar от экранов авторизации
@Composable
fun ImBridgeApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.SIGN_IN.value) {
        composable(Routes.SIGN_UP.value) { SignUpScreen(navController) }
        composable(Routes.SIGN_IN.value) { SignInScreen(navController) }
        composable(Routes.MAIN.value) { MainScreen(navController) }
    }
}