package com.dz.bmstu_trade.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dz.bmstu_trade.ui.auth.AuthScreen
import com.dz.bmstu_trade.ui.main.MainScreen

// Внешний граф для того, чтобы отделить основные экраны с BottomNavBar от экранов авторизации
// Для того, чтобы осуществить переход во внешнем графе из экранов внутренних графов им передается
// navController внешнего графа
@Composable
fun ImBridgeApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.AUTH.value) {
        composable(Routes.AUTH.value) { AuthScreen(navController) }
        composable(Routes.MAIN.value) { MainScreen(navController) }
    }
}