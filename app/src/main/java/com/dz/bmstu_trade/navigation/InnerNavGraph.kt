package com.dz.bmstu_trade.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dz.bmstu_trade.ui.canvas.CanvasScreen
import com.dz.bmstu_trade.ui.devicemanualconnect.DeviceManualConnectScreen
import com.dz.bmstu_trade.ui.gallery.GalleryScreen
import com.dz.bmstu_trade.ui.home.HomeScreen
import com.dz.bmstu_trade.ui.settings.SettingsScreen

// Внутренний граф с BottomNavBar'ом
// Есть вопросики к канвасу и экранам подключения, пока что оставил их здесь, но судя по нашему дизайну
// в фигме, они должны быть во внешнем графе
@Composable
fun MainNavHost(navController: NavHostController, innerPadding: PaddingValues){
    NavHost(navController, startDestination = Routes.HOME.value, Modifier.padding(innerPadding)) {
        // Я думаю, что будет удобно если у каждой вкладки будет своя изолированная навигация, поэтому использовал
        // для этого nested Navigation. Но если вдруг что, нетрудно вытащить экраны из вложенных графов
        navigation(startDestination = Routes.HOME_ROOT.value, route = Routes.HOME.value){
            composable(Routes.HOME_ROOT.value) { HomeScreen(navController) }
            composable(Routes.DEV_MAN_CONNECT.value) { DeviceManualConnectScreen(navController) }
        }
        navigation(startDestination = Routes.SETTINGS_ROOT.value, route = Routes.SETTINGS.value){
            composable(Routes.SETTINGS_ROOT.value) { SettingsScreen(navController) }
        }
        // Для этих вложенную навигацию пока не создавал так как они под вопросом
        composable(Routes.GALLERY.value) { GalleryScreen(navController) }
        composable(Routes.CANVAS.value) { CanvasScreen(navController) }
    }
}