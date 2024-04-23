package com.dz.bmstu_trade.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dz.bmstu_trade.ui.auth.signin.SignInScreen
import com.dz.bmstu_trade.ui.auth.signup.SignUpScreen

/**
 * Граф навигации для экранов авторизации, параметры аналогично главному графу
 */
@Composable
fun AuthNavHost(outerNavHostController: NavHostController, authNavController: NavHostController) {
    NavHost(authNavController, startDestination = Routes.SIGN_IN.value) {
        composable(Routes.SIGN_UP.value) { SignUpScreen(authNavController) }
        composable(Routes.SIGN_IN.value) {
                SignInScreen(authNavController) {
                outerNavHostController.navigate(Routes.MAIN.value) {
                    popUpTo(Routes.AUTH.value) { inclusive = true }
                }
            }
        }
    }
}