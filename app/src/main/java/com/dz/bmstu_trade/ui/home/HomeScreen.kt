package com.dz.bmstu_trade.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dz.bmstu_trade.navigation.Routes

@Composable
fun HomeScreen(navController: NavHostController) {
    Column (
        modifier = Modifier.padding(16.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Button(
            onClick = { navController.navigate(Routes.DEV_MAN_CONNECT.value) },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Подключение устройства вручную")
        }
    }
}