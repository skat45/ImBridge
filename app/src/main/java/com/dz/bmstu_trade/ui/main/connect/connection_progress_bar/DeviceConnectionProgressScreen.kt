package com.dz.bmstu_trade.ui.main.connect.connection_progress_bar

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dz.bmstu_trade.R
import com.dz.bmstu_trade.ui.main.connect.net_errors.NoWiFiConnectionLabel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectionProgressScreen(
    navController: NavHostController,
    code: String,
    onConnectedToDevice: () -> Unit,
) {
    val wifiViewModel = hiltViewModel(
        creationCallback = { factory: WifiViewModelFactory ->
            factory.create(code)
        }
    )
    val isWifiEnabled by wifiViewModel.wiFiIsEnabled.collectAsState()
    val connectedToDevice by wifiViewModel.connectedToDevice.collectAsState()
    if (connectedToDevice) {
        onConnectedToDevice()
    }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        TopAppBar(
            title = { Text(text = stringResource(R.string.connection_progress_top_bar_title)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            ),
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.arrow_back_icon_description),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (isWifiEnabled) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = dimensionResource(R.dimen.spinner_stroke_width),
                    )
                    Text(
                        text = stringResource(R.string.connection_to_progress_label) +
                                " " + code,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(top = dimensionResource(R.dimen.spinner_description_label_padding)),
                        textAlign = TextAlign.Center
                    )
                } else {
                    NoWiFiConnectionLabel()
                }
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    Text(
                        text = stringResource(R.string.required_access_to_location_old_device_label),
                        textAlign = TextAlign.Center,
                    )
                    Button(onClick = {
                        context.startActivity(
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                .setData(
                                    Uri.fromParts(
                                        "package",
                                        context.packageName.toString(),
                                        null
                                    )
                                )
                        )
                    }) {
                        Text(text = stringResource(R.string.to_settings_label))
                    }
                }
            }
        }
    }
}
