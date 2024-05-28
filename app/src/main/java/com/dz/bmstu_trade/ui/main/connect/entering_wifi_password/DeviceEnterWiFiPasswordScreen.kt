package com.dz.bmstu_trade.ui.main.connect.entering_wifi_password

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dz.bmstu_trade.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterWiFiPasswordScreen(
    navController: NavHostController,
    viewModel: WiFiPasswordInputViewModel,
) {
    val passwordFieldState by viewModel.state.collectAsState()
    LaunchedEffect (passwordFieldState) {
        if (passwordFieldState.connectionStatus == PasswordFieldState.Status.CONNECTED) {
            navController.navigate("home")
        }
    }


    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(
                text = stringResource(R.string.choose_wi_fi_top_bar_title),
                style = MaterialTheme.typography.titleLarge,
            ) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            ),
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate("home/chooseWiFi")
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.arrow_back_icon_description)
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
                Text(
                    text = stringResource(R.string.connect_to_label) + " " + passwordFieldState.ssid,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(R.dimen.space_between_inputs_texts_buttons)),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    value = passwordFieldState.password,
                    onValueChange = { viewModel.onPasswordUpdated(it) },
                    label = { Text(text = stringResource(R.string.password_label)) },
                    isError = passwordFieldState.error != null
                            && passwordFieldState.error != PasswordFieldState.Error.TOO_SHORT,
                    trailingIcon = {
                        IconButton(onClick = { viewModel.onShownUpdated() }) {
                            Icon(
                                painter = if (passwordFieldState.shown) painterResource(R.drawable.password_shown)
                                else painterResource(R.drawable.password_not_shown),
                                contentDescription = stringResource(R.string.show_password_icon_description)
                            )
                        }
                    },
                    visualTransformation = if (passwordFieldState.shown) VisualTransformation.None
                    else PasswordVisualTransformation()
                )

                passwordFieldState.error?.let {
                    Text(
                        text = stringResource(it.messageResId),
                        modifier = Modifier
                            .padding(top = dimensionResource(R.dimen.space_between_inputs_texts_buttons)),
                        color = when (it) {
                            PasswordFieldState.Error.TOO_LONG -> MaterialTheme.colorScheme.error
                            PasswordFieldState.Error.TOO_SHORT -> MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
                Button(
                    enabled = passwordFieldState.error == null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(R.dimen.space_between_inputs_texts_buttons)),
                    onClick = {
                        viewModel.onClickSendButton()
                    }
                ) {
                    Text(text = stringResource(id = R.string.connect_by_code_button_title))
                }
            }
        }
    }
}