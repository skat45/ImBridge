package com.dz.bmstu_trade.ui.main.devicemanualconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.dz.bmstu_trade.addDeviceViewModels.ShowWiFiViewModel
import com.dz.bmstu_trade.addDeviceViewModels.WiFiNetwork
import com.dz.bmstu_trade.R
import com.germainkevin.collapsingtopbar.CollapsingTopBar
import com.germainkevin.collapsingtopbar.CollapsingTopBarDefaults
import com.germainkevin.collapsingtopbar.rememberCollapsingTopBarScrollBehavior

@Composable
fun DeviceChooseWiFiNetworkScreen(
    viewModel: ShowWiFiViewModel = remember { ShowWiFiViewModel() }
) {
    val scrollBehavior = rememberCollapsingTopBarScrollBehavior()
    val scrollableState = rememberLazyListState()
    val networks by viewModel.networks.collectAsState()

    val lazyColumnWiFiListModifier: Modifier = if (!scrollBehavior.isCollapsed) {
        Modifier.padding(
            top = dimensionResource(R.dimen.general_padding_start_end),
            start = dimensionResource(R.dimen.general_padding_start_end),
            end = dimensionResource(R.dimen.general_padding_start_end),
        )
    }
    else {
        Modifier.padding(
            start = dimensionResource(R.dimen.general_padding_start_end),
            end = dimensionResource(R.dimen.general_padding_start_end),
        )
    }

    Column(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
        CollapsingTopBar(
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            scrollBehavior = scrollBehavior,
            title = { Text(
                text = stringResource(R.string.choose_wi_fi_top_bar_title),
                fontSize = dimensionResource(R.dimen.connecting_to_router_top_bar_title).value.sp,
            ) },
            subtitle = { Text(
                text = stringResource(R.string.choose_wi_fi_label),
                fontSize = dimensionResource(R.dimen.choose_wi_fi_network_label_font_size).value.sp,
            ) },
            colors = CollapsingTopBarDefaults.colors(
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            ),
        )
        LazyColumn(
            state = scrollableState,
            modifier = lazyColumnWiFiListModifier
        ) {
            items(networks) {
                Box(
                    modifier = Modifier
                        .padding(bottom = dimensionResource(R.dimen.general_padding_start_end))
                ) {
                    DrawWiFiItem(it)
                }
            }
        }
    }
}


@Composable
fun DrawWiFiNetworks(networks: List<WiFiNetwork>) {
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = dimensionResource(R.dimen.general_padding_start_end),
                start = dimensionResource(R.dimen.general_padding_start_end),
                end = dimensionResource(R.dimen.general_padding_start_end),
            )
    ) {
        items(networks) {
            Box(
                modifier = Modifier
                    .padding(bottom = dimensionResource(R.dimen.general_padding_start_end))
            ) {
                DrawWiFiItem(it)
            }
        }
    }
}


@Composable
fun DrawWiFiItem(network: WiFiNetwork) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.wi_fi_card_height))
            .background(
                MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(dimensionResource(R.dimen.wi_fi_card_border_radius))
            )
            .border(
                width = dimensionResource(R.dimen.wi_fi_item_border_width),
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(dimensionResource(R.dimen.wi_fi_card_border_radius))
            )
            .clickable(
                onClick = { /* TODO */ }
            ),
    ) {
        val wiFiIcon = if (network.protected) {
            painterResource(R.drawable.protected_wi_fi)
        }
        else {
            painterResource(R.drawable.not_protected_wi_fi)
        }
        Box(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.wi_fi_network_icon_padding))
        ) {
            Icon(
                painter = wiFiIcon,
                contentDescription = stringResource(R.string.wi_fi_icon_description)
            )
        }
        Box(
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.wi_fi_ssid_name_padding_top))
        ) {
            Text(
                text = network.ssid,
                fontSize = dimensionResource(R.dimen.wi_fi_network_name_font_size).value.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChooseWiFiNetworkPreview() {
    DeviceChooseWiFiNetworkScreen()
}