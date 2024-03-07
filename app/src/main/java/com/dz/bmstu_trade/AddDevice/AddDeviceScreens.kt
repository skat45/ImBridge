package com.dz.bmstu_trade.AddDevice

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dz.bmstu_trade.R
import com.germainkevin.collapsingtopbar.CollapsingTopBar
import com.germainkevin.collapsingtopbar.CollapsingTopBarDefaults
import com.germainkevin.collapsingtopbar.rememberCollapsingTopBarScrollBehavior


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualConnectionScreen(
    viewModel: AddDeviceViewModel = remember { AddDeviceViewModel() }
) {
    val codeFieldState by viewModel.code.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = stringResource(R.string.manual_connection_top_bar_title)) },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            ),
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(R.dimen.padding_top_code_connection_screen),
                    start = dimensionResource(R.dimen.general_padding_start_end),
                    end = dimensionResource(R.dimen.general_padding_start_end),
                ),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(text = stringResource(R.string.code_location_label))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(R.dimen.space_between_inputs_texts_buttons)),
                    value = codeFieldState.value,
                    onValueChange = { viewModel.onCodeUpdated(it) },
                    isError = codeFieldState.errorCode != null,
                    label = { Text(text = stringResource(R.string.input_device_code_label))}
                )
                if (codeFieldState.errorCode == 1) {
                    Text(
                        text = stringResource(R.string.too_long_device_code_error),
                        modifier = Modifier
                            .padding(top = dimensionResource(R.dimen.space_between_inputs_texts_buttons)),
                        color = Color.Red,
                    )
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(R.dimen.space_between_inputs_texts_buttons)),
                    onClick = { /* TODO */ }
                ) {
                    Text(text = stringResource(id = R.string.connect_by_code_button_title))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManualConnectionPreview() {
    ManualConnectionScreen()
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectionProgressScreen() {
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = stringResource(R.string.connection_progress_top_bar_title)) },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            ),
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                LoadingSpinner()
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(R.dimen.space_between_spinner_and_label)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.connection_progress_label),
                        fontSize = dimensionResource(R.dimen.spinner_loader_label_font_size).value.sp
                    )
                }
            }
        }
    }
}


@Composable
fun LoadingSpinner() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        CircularProgressIndicator(
            color = Color.Blue,
            strokeWidth = dimensionResource(R.dimen.spinner_stroke_width),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConnectionProgressPreview() {
    ConnectionProgressScreen()
}



@Composable
fun ChooseWiFiNetworkScreen(
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
    ChooseWiFiNetworkScreen()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterWiFiPasswordScreen(
    viewModel: WiFiPasswordInputViewModel = remember { WiFiPasswordInputViewModel() }
) {
    val passwordFieldState by viewModel.state.collectAsState()

    val passwordIcon = if (passwordFieldState.shown) {painterResource(R.drawable.password_shown)}
                       else {painterResource(R.drawable.password_not_shown)}

    val passwordIsShown = if (passwordFieldState.shown) {VisualTransformation.None}
                          else {PasswordVisualTransformation()}

    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = stringResource(R.string.choose_wi_fi_top_bar_title)) },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            ),
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(R.dimen.padding_top_code_connection_screen),
                    start = dimensionResource(R.dimen.general_padding_start_end),
                    end = dimensionResource(R.dimen.general_padding_start_end),
                ),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(text = stringResource(R.string.connect_to_label) + " " + passwordFieldState.ssid)
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(R.dimen.space_between_inputs_texts_buttons)),
                    value = passwordFieldState.password,
                    onValueChange = { viewModel.onPasswordUpdated(it) },
                    label = { Text(text = stringResource(R.string.password_label))},
                    trailingIcon = {
                        IconButton(onClick = { viewModel.onShownUpdated() }) {
                            Icon(
                                painter = passwordIcon,
                                contentDescription = stringResource(R.string.show_password_icon_description)
                            )
                        }
                    },
                    visualTransformation = passwordIsShown
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(R.dimen.space_between_inputs_texts_buttons)),
                    onClick = { /* TODO */ }
                ) {
                    Text(text = stringResource(id = R.string.connect_by_code_button_title))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EnterWiFiPasswordPreview() {
    EnterWiFiPasswordScreen()
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceNamingScreen(
    viewModel: NameDeviceViewModel = remember { NameDeviceViewModel() }
) {
    val nameFieldState by viewModel.name.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = stringResource(R.string.naming_top_bar_title)) },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            ),
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(R.dimen.padding_top_code_connection_screen),
                    start = dimensionResource(R.dimen.general_padding_start_end),
                    end = dimensionResource(R.dimen.general_padding_start_end),
                ),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(text = stringResource(R.string.naming_rules_label))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(R.dimen.space_between_inputs_texts_buttons)),
                    value = nameFieldState.value,
                    onValueChange = { viewModel.onNameUpdated(it) },
                    isError = nameFieldState.error != null,
                    label = { Text(text = stringResource(R.string.device_name_label))}
                )
                if (nameFieldState.error != null) {
                    Text(
                        text = nameFieldState.error.orEmpty(),
                        modifier = Modifier
                            .padding(top = dimensionResource(R.dimen.space_between_inputs_texts_buttons)),
                        color = Color.Red,
                    )
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(R.dimen.space_between_inputs_texts_buttons)),
                    onClick = { /* TODO */ }
                ) {
                    Text(text = stringResource(id = R.string.apply_label))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DeviceNamingPreview() {
    DeviceNamingScreen()
}

