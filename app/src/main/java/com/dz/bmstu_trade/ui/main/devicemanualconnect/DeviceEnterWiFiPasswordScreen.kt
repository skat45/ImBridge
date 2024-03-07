package com.dz.bmstu_trade.ui.main.devicemanualconnect

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.dz.bmstu_trade.addDeviceViewModels.WiFiPasswordInputViewModel
import com.dz.bmstu_trade.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterWiFiPasswordScreen(
    viewModel: WiFiPasswordInputViewModel = remember { WiFiPasswordInputViewModel() }
) {
    val passwordFieldState by viewModel.state.collectAsState()

    val passwordIcon = if (passwordFieldState.shown) {
        painterResource(R.drawable.password_shown)
    }
    else {
        painterResource(R.drawable.password_not_shown)
    }

    val passwordIsShown = if (passwordFieldState.shown) {
        VisualTransformation.None}
    else {
        PasswordVisualTransformation()
    }

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
                    label = { Text(text = stringResource(R.string.password_label)) },
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