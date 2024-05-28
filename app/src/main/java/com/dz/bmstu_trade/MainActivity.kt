package com.dz.bmstu_trade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.dz.bmstu_trade.navigation.ImBridgeApp
import com.dz.bmstu_trade.ui.main.setlanguage.LanguageViewModel
import com.dz.bmstu_trade.ui.main.settings.SettingsViewModel
import com.dz.bmstu_trade.ui.theme.BMSTU_TradeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeViewModel: SettingsViewModel = hiltViewModel()
            val isDarkTheme by themeViewModel.switch.collectAsState()

            val languageViewModel: LanguageViewModel = hiltViewModel()
            val currentLocale by languageViewModel.currentLocale.collectAsState()

            val context = LocalContext.current
            val resources = context.resources

            val configuration = resources.configuration
            if (configuration.locales.get(0) != currentLocale) {
                configuration.setLocale(currentLocale)
                resources.updateConfiguration(configuration, resources.displayMetrics)
            }

            BMSTU_TradeTheme(darkTheme = isDarkTheme) {
                ImBridgeApp()
            }
        }
    }
}

