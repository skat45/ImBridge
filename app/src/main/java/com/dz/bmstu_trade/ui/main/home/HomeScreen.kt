package com.dz.bmstu_trade.ui.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dz.bmstu_trade.R
import com.dz.bmstu_trade.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.connectToDevice()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val state = viewModel.deviceState.collectAsState()
    val devices = viewModel.devices.collectAsState()

    var showConnectionBottomSheet by remember { mutableStateOf(false) }
    var showDeviceListBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    when (state.value) {
        is DeviceStateScreen.Success -> DevicePanel(
            navController = navController,
            (state.value as DeviceStateScreen.Success).data,
            viewModel = viewModel,
            openAddDeviceSheet = {
                showConnectionBottomSheet = true
            },
            openDeviceListSheet = {
                showDeviceListBottomSheet = true
            }
        )

        DeviceStateScreen.Empty -> EmptyView(addDevice = {
            showConnectionBottomSheet = true
        })
        DeviceStateScreen.Error -> ErrorView(
            message = "Error was occurred",
            onRepeatConnection = {
                viewModel.connectToDevice()
            },
            onChangeDevice = {
                showDeviceListBottomSheet = true
            },
            onManualConnectClick = {
                navController.navigate(Routes.ENTER_DEV_CODE.value)
            }
        )
        DeviceStateScreen.Loading -> LoadingView()
        DeviceStateScreen.Undefined -> UndefinedView(
            openDeviceList = {
                viewModel.getDevices()
                showDeviceListBottomSheet = true
            }
        )
    }

    if (showConnectionBottomSheet) {
        HomeBottomSheet(
            sheetState,
            onValueChange = {
                showConnectionBottomSheet = it
            },
            onManualConnectClick = {
                navController.navigate(Routes.ENTER_DEV_CODE.value)
            },
            onQrConnectClick = {
                // Todo: перейти на экран подключения по qr-коду
            },
        )
    }

    if (showDeviceListBottomSheet) {
        DeviceListBottomSheet(
            sheetState = sheetState,
            devices = devices.value,
            onValueChange = {
                showDeviceListBottomSheet = it
            },
            onDeviceClick = {
                viewModel.onDeviceItemClick(it.code)
            }
        )
    }
}

@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(message: String,
              onRepeatConnection:() -> Unit,
              onChangeDevice: () -> Unit,
              onManualConnectClick: () -> Unit
              ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(message, modifier = Modifier.padding(bottom = 8.dp))
        Button(onClick = onRepeatConnection) {
            Text(stringResource(R.string.retry_btn_label))
        }
        Button(onClick = onChangeDevice) {
            Text(stringResource(R.string.change_device))
        }
        Button(onClick = onManualConnectClick) {
            Text(stringResource(R.string.connect_new_device_btn_label))
        }
    }
}

@Composable
fun EmptyView(addDevice: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(R.string.empty_state_home_text), modifier = Modifier.padding(bottom = 8.dp))
        Button(onClick = addDevice) {
            Text(stringResource(R.string.add_new_device))
        }
    }
}

@Composable
fun UndefinedView(openDeviceList: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(R.string.undefined_state_text), modifier = Modifier.padding(bottom = 8.dp))
        Button(onClick = openDeviceList) {
            Text(stringResource(R.string.select_device))
        }
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