package com.dz.bmstu_trade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dz.bmstu_trade.navigation.ImBridgeApp
import com.dz.bmstu_trade.ui.theme.BMSTU_TradeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMSTU_TradeTheme {
                ImBridgeApp()
            }
        }
    }
}

