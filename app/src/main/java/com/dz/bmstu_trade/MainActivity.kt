package com.dz.bmstu_trade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.dz.bmstu_trade.navigation.ImBridgeApp
import com.dz.bmstu_trade.ui.main.settings.SettingsViewModel
import com.dz.bmstu_trade.ui.theme.BMSTU_TradeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val themeViewModel:SettingsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme by themeViewModel.switch.collectAsState()
            BMSTU_TradeTheme(darkTheme = isDarkTheme) {
                ImBridgeApp(themeViewModel)
            }
        }
    }
}

