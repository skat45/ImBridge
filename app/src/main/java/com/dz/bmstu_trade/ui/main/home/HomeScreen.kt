package com.dz.bmstu_trade.ui.main.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.deviceState.collectAsState()

    when (state.value) {
        is DeviceStateScreen.Success -> DevicePanel(
            navController = navController,
            (state.value as DeviceStateScreen.Success).data
        )

        DeviceStateScreen.Disconnected -> DisconnectedView()
        DeviceStateScreen.Error -> ErrorView(message = "Error was occurred")
        DeviceStateScreen.Loading -> LoadingView()
    }
}

@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message, color = Color.Red)
    }
}

@Composable
fun DisconnectedView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Disconnected from the device", color = Color.Gray)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        HomeScreen(navController = navController)
    }
}