package com.dz.bmstu_trade.ui.main.connect.wifi_picker

import android.net.wifi.ScanResult
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dz.bmstu_trade.R
import com.dz.bmstu_trade.app_context_holder.AppContextHolder
import com.dz.bmstu_trade.ui.main.connect.net_errors.NoWiFiConnectionLabel
import com.dz.bmstu_trade.ui.main.connect.permission_dialog.PermissionAlertDialog
import com.germainkevin.collapsingtopbar.CollapsingTopBar
import com.germainkevin.collapsingtopbar.CollapsingTopBarDefaults
import com.germainkevin.collapsingtopbar.rememberCollapsingTopBarScrollBehavior
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun DeviceChooseWiFiNetworkScreen(
    navController: NavHostController,
    viewModel: WiFiPickerViewModel = viewModel()
) {
    val networks by viewModel.networks.collectAsState()
    val requiredPermissions by viewModel.requiredPermissions.collectAsState()
    val permissionsGranted by viewModel.permissionsGranted.collectAsState()
    val isWifiEnabled by viewModel.wiFiIsEnabled.collectAsState()

    var isAlertDialogShown by remember { mutableStateOf(false) }

    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {
            CoroutineScope(Dispatchers.Default).launch {
                viewModel.onPermissionsResult(it)
            }
        })

    LaunchedEffect(Unit) {
        viewModel.eventsFlow.collectLatest {
            when (it) {
                is WiFiPickerEvent.ShowPermissionsAlertDialog -> {
                    isAlertDialogShown = true
                }
            }
        }
    }

    LaunchedEffect(requiredPermissions) {
        if (requiredPermissions.isNotEmpty()) {
            permissionsLauncher.launch(requiredPermissions)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        WiFContent(networks, navController, permissionsGranted, isWifiEnabled) {
            permissionsLauncher.launch(requiredPermissions)
        }
        if (isAlertDialogShown) {
            PermissionAlertDialog {
                isAlertDialogShown = false
            }
        }
    }
}

@Composable
private fun WiFContent(
    networks: ImmutableList<ScanResult>,
    navController: NavHostController,
    permissionsGranted: Boolean,
    isWifiEnabled: Boolean,
    onRefreshPermissions: () -> Unit
) {
    val scrollBehavior = rememberCollapsingTopBarScrollBehavior()
    val scrollableState = rememberLazyListState()

    Column(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        CollapsingTopBar(
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate("home/deviceManual")
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.arrow_back_icon_description),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            scrollBehavior = scrollBehavior,
            title = {
                Text(
                    text = stringResource(R.string.choose_wi_fi_top_bar_title),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            subtitle = {
                Text(
                    text = stringResource(R.string.choose_wi_fi_label),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            colors = CollapsingTopBarDefaults.colors(
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            ),
        )
        when {
            isWifiEnabled && permissionsGranted -> {
                LazyColumn(
                    state = scrollableState,
                ) {
                    item {
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.general_padding_start_end)))
                    }
                    if (networks.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.no_wifi_detected_label),
                                )
                            }
                        }
                    } else {
                        items(networks) {
                            val ssid = if (Build.VERSION.SDK_INT >= 33)
                                it.wifiSsid
                                    .toString()
                            else
                                it.SSID
                            // Не показываем скрытые сети и сети, раздаваемые другими функциональными устройствами
                            if (
                                !ssid.isNullOrEmpty()
                                && !ssid.contains("DIRECT")
                                && !ssid.contains("IMBRIDGE-")
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(bottom = dimensionResource(R.dimen.general_padding_start_end))
                                        .padding(horizontal = dimensionResource(R.dimen.general_padding_start_end))
                                ) {
                                    WiFiItem(
                                        it,
                                        navController
                                    )
                                }
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.wi_fi_card_height)))
                    }
                }
            }

            !isWifiEnabled -> {
                NoWiFiConnectionLabel()
            }

            else -> { // Permissions not granted and WiFi is enabled
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(R.string.no_access_to_location_label),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier
                                .padding(top = dimensionResource(R.dimen.spinner_description_label_padding)),
                            textAlign = TextAlign.Center
                        )
                        Button(modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.space_between_inputs_texts_buttons)),
                            onClick = { onRefreshPermissions() }
                        ) {
                            Text(text = stringResource(R.string.update_label))
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun WiFiItem(
    network: ScanResult,
    navController: NavHostController,
) {
    val isInvalidFrequency5G = network.frequency in 5000..5825
    val ssid =
        if (Build.VERSION.SDK_INT >= 33)
            network.wifiSsid
                .toString()
                .removePrefix("\"")
                .removeSuffix("\"")
        else
            network.SSID

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(dimensionResource(R.dimen.wi_fi_card_border_radius))
            )
            .clip(RoundedCornerShape(dimensionResource(R.dimen.wi_fi_card_border_radius)))
            .clickable(
                onClick =
                if (!isInvalidFrequency5G) {
                    {
                        navController.navigate("home/enterWiFiPassword$ssid")
                    }
                } else {
                    {
                        Toast
                            .makeText(
                                AppContextHolder.getContext(),
                                "Вы не можете выбрать эту сеть",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true)
            )
            .padding(horizontal = dimensionResource(R.dimen.wi_fi_network_space)),
    ) {
        Icon(
            painter = if (isInvalidFrequency5G) {
                painterResource(R.drawable.wifi_off)
            } else if (network.capabilities.contains("WPA")) {
                painterResource(R.drawable.protected_wi_fi)
            } else {
                painterResource(R.drawable.not_protected_wi_fi)
            },
            contentDescription = stringResource(R.string.wi_fi_icon_description),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(vertical = dimensionResource(R.dimen.wi_fi_network_space))
        )
        Column(
            modifier = Modifier
                .padding(vertical = dimensionResource(R.dimen.wi_fi_network_space))
                .padding(start = dimensionResource(R.dimen.wi_fi_network_space))
        ) {
            Text(
                text = ssid,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            if (isInvalidFrequency5G) {
                Text(
                    text = stringResource(R.string.devise_doesnt_support_5g_label),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}