package com.dz.bmstu_trade.ui.main.devicemanualconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dz.bmstu_trade.R
import com.dz.bmstu_trade.addDeviceViewModels.WiFiNetwork
import com.dz.bmstu_trade.addDeviceViewModels.WiFiPickerViewModel
import com.germainkevin.collapsingtopbar.CollapsingTopBar
import com.germainkevin.collapsingtopbar.CollapsingTopBarDefaults
import com.germainkevin.collapsingtopbar.rememberCollapsingTopBarScrollBehavior

@Composable
fun DeviceChooseWiFiNetworkScreen(
    viewModel: WiFiPickerViewModel = remember { WiFiPickerViewModel() }
) {
    val scrollBehavior = rememberCollapsingTopBarScrollBehavior()
    val scrollableState = rememberLazyListState()
    val networks by viewModel.networks.collectAsState()

    Column(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
        CollapsingTopBar(
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            scrollBehavior = scrollBehavior,
            title = { Text(
                text = stringResource(R.string.choose_wi_fi_top_bar_title),
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            ) },
            subtitle = { Text(
                text = stringResource(R.string.choose_wi_fi_label),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ) },
            colors = CollapsingTopBarDefaults.colors(
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            ),
        )
        LazyColumn(
            state = scrollableState,
        ) {
            item {
                Spacer(
                    modifier = Modifier
                        .height(dimensionResource(R.dimen.general_padding_start_end))
                )
            }
            items(networks) {
                Box(
                    modifier = Modifier
                        .padding(bottom = dimensionResource(R.dimen.general_padding_start_end))
                        .padding(horizontal = dimensionResource(R.dimen.general_padding_start_end))
                ) {
                    WiFiItem(it)
                }
            }
        }
    }
}


@Composable
fun WiFiItem(network: WiFiNetwork) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(dimensionResource(R.dimen.wi_fi_card_border_radius))
            )
            .clip(RoundedCornerShape(dimensionResource(R.dimen.wi_fi_card_border_radius)))
            .clickable(
                onClick = { /* TODO */ },
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple( bounded = true )
            )
            .padding(horizontal = dimensionResource(R.dimen.wi_fi_network_space)),
    ) {
        Icon(
            painter = run {
                if (network.protected) {
                    painterResource(R.drawable.protected_wi_fi)
                }
                else {
                    painterResource(R.drawable.not_protected_wi_fi)
                }
            },
            contentDescription = stringResource(R.string.wi_fi_icon_description),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(vertical = dimensionResource(R.dimen.wi_fi_network_space))
        )
        Text(
            text = network.ssid,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(vertical = dimensionResource(R.dimen.wi_fi_network_space))
                .padding(start = dimensionResource(R.dimen.wi_fi_network_space))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChooseWiFiNetworkPreview() {
    DeviceChooseWiFiNetworkScreen()
}