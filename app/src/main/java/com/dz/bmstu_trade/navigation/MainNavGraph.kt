package com.dz.bmstu_trade.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dz.bmstu_trade.ui.main.canvas.CanvasScreen
import com.dz.bmstu_trade.ui.main.devicemanualconnect.DeviceManualConnectScreen
import com.dz.bmstu_trade.ui.main.gallery.GalleryScreen
import com.dz.bmstu_trade.ui.main.home.HomeScreen
import com.dz.bmstu_trade.ui.main.setlanguage.SettingsLanguage
import com.dz.bmstu_trade.ui.main.settings.SettingsScreen

// Основной внутренний граф с BottomNavBar'ом, outerNavHostNavController для перемещения во внешнем графе
@Composable
fun MainNavHost(
    outerNavHostController: NavHostController,
    mainNavController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        mainNavController,
        startDestination = Routes.HOME.value,
        Modifier.padding(innerPadding)
    ) {
        // Я думаю, что будет удобно если у каждой вкладки будет своя изолированная навигация, поэтому использовал
        // для этого nested Navigation. Но если вдруг что, нетрудно вытащить экраны из вложенных графов
        navigation(startDestination = Routes.HOME_ROOT.value, route = Routes.HOME.value) {
            composable(Routes.HOME_ROOT.value) { HomeScreen(mainNavController) }
            composable(Routes.DEV_MAN_CONNECT.value) { DeviceManualConnectScreen(mainNavController) }
        }
        navigation(startDestination = Routes.SETTINGS_ROOT.value, route = Routes.SETTINGS.value) {
            composable(Routes.SETTINGS_ROOT.value) { SettingsScreen(mainNavController) }
            composable(Routes.SETTINGS_LANGUAGE.value) { SettingsLanguage(mainNavController) }
        }
        // По мере увеличения числа экранов в галерее её тоже следует обернуть в navigation()
        composable(Routes.GALLERY.value) { GalleryScreen(mainNavController) }
        // Canvas пока предлагаю оставить так, потому что он не относится к конкретной вкладке
        composable(Routes.CANVAS.value) { CanvasScreen(mainNavController) }
    }
}