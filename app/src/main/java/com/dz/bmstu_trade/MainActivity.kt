package com.dz.bmstu_trade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dz.bmstu_trade.data.mappers.imageVkMapper
import com.dz.bmstu_trade.navigation.ImBridgeApp
import com.dz.bmstu_trade.ui.theme.BMSTU_TradeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMSTU_TradeTheme {
                ImBridgeApp()

                //imageVkMapper("https://developer.alexanderklimov.ru/android/images/android_cat.jpg")
            }
        }
    }
}

