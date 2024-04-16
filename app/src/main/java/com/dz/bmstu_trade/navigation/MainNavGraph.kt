package com.dz.bmstu_trade.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dz.bmstu_trade.ui.main.canvas.CanvasScreen
import com.dz.bmstu_trade.ui.main.connect.connection_progress_bar.ConnectionProgressScreen
import com.dz.bmstu_trade.ui.main.connect.connection_progress_bar.WifiViewModel
import com.dz.bmstu_trade.ui.main.connect.device_code.AddDeviceViewModel
import com.dz.bmstu_trade.ui.main.connect.device_code.DeviceManualConnectScreen
import com.dz.bmstu_trade.ui.main.connect.wifi_picker.DeviceChooseWiFiNetworkScreen
import com.dz.bmstu_trade.ui.main.gallery.GalleryScreen
import com.dz.bmstu_trade.ui.main.home.HomeScreen
import com.dz.bmstu_trade.ui.main.setlanguage.SettingsLanguage
import com.dz.bmstu_trade.ui.main.settings.SettingsScreen

/**
 * Основной внутренний граф с BottomNavBar'ом, `outerNavHostNavController`
 * для перемещения во внешнем графе. Для каждой вкладки создается отдельный
 * nested graph (navigation()). У галлереи его нет, так как там пока один
 * экран, а у канваса так как он доступен из двух вкладок.
 */
@Composable
fun MainNavHost(
    outerNavHostController: NavHostController,
    mainNavController: NavHostController,
    innerPadding: PaddingValues,
) {
    val codeViewModel = remember { AddDeviceViewModel() }

    NavHost(
        mainNavController, startDestination = Routes.HOME.value, Modifier.padding(innerPadding)
    ) {
        navigation(startDestination = Routes.HOME_ROOT.value, route = Routes.HOME.value) {
            composable(Routes.HOME_ROOT.value) { HomeScreen(mainNavController) }
            composable(Routes.ENTER_DEV_CODE.value) {
                DeviceManualConnectScreen(
                    mainNavController,
                    onEnterCode = {
                        mainNavController.navigate("home/connectingProgress")
//                        mainNavController.navigate("home/chooseWiFi")
                        // Комментируем для того, чтобы иметь возможность перескочить через экан в отсутствии девайса с wifi
                    },
                    codeViewModel
                )
            }
            composable(Routes.CONNECTING_PROGRESS.value) {
                ConnectionProgressScreen(
                    mainNavController,
                    codeViewModel,
                    wifiViewModel = WifiViewModel(codeViewModel = codeViewModel),
                    onConnectedToDevice = {
                        mainNavController.navigate("home/chooseWiFi")
                    },
                )
            }
            composable(Routes.CHOOSE_WIFI.value) {
                DeviceChooseWiFiNetworkScreen(navController = mainNavController)
            }
            //composable(Routes.DEV_MAN_CONNECT.value) { DeviceManualConnectScreen(mainNavController) }
            composable(Routes.CANVAS.value) { CanvasScreen(mainNavController) }
        }
        navigation(startDestination = Routes.SETTINGS_ROOT.value, route = Routes.SETTINGS.value) {
            composable(Routes.SETTINGS_ROOT.value) { SettingsScreen(mainNavController) }
            composable(Routes.SETTINGS_LANGUAGE.value) { SettingsLanguage(mainNavController) }
        }
        composable(Routes.GALLERY.value) { GalleryScreen(mainNavController) }
    }
}