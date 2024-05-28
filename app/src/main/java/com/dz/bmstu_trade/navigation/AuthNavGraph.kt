package com.dz.bmstu_trade.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dz.bmstu_trade.ui.auth.signin.SignInScreen
import com.dz.bmstu_trade.ui.auth.signup.SignUpScreen
import com.dz.bmstu_trade.ui.auth.vk.VkAuthScreen
import com.vk.api.sdk.VK

/**
 * Граф навигации для экранов авторизации, параметры аналогично главному графу
 */
@Composable
fun AuthNavHost(outerNavHostController: NavHostController, authNavController: NavHostController) {
    NavHost(authNavController, startDestination = Routes.VK_AUTH.value) {
        composable(Routes.SIGN_UP.value) {
            SignUpScreen(
                navController = authNavController,
                onSignUp = {
                    outerNavHostController.navigate(Routes.MAIN.value) {
                        popUpTo(Routes.AUTH.value) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.SIGN_IN.value) {
            SignInScreen(
                navController = authNavController,
                onSignIn = {
                    outerNavHostController.navigate(Routes.MAIN.value) {
                        popUpTo(Routes.AUTH.value) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.VK_AUTH.value) {
            VkAuthScreen(
                // VK.getUserId()
                // Это получение id для привязки устройства к аккаунту
                navController = authNavController,
                onSignIn = {
                    outerNavHostController.navigate(Routes.MAIN.value) {
                        popUpTo(Routes.AUTH.value) { inclusive = true }
                    }
                }
            )
        }
    }
}