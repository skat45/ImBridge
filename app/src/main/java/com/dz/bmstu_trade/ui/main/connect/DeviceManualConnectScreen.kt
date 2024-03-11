package com.dz.bmstu_trade.ui.main.connect

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.dz.bmstu_trade.ui.main.connect.deviceCodeVM.AddDeviceViewModel
import com.dz.bmstu_trade.R
import com.dz.bmstu_trade.ui.main.connect.deviceCodeVM.TextFieldState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceManualConnectScreen(
    navController: NavHostController? = null,
    viewModel: AddDeviceViewModel = remember { AddDeviceViewModel() }
) {
    val codeFieldState by viewModel.code.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = stringResource(R.string.manual_connection_top_bar_title)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            ),
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
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
                Text(text = stringResource(R.string.code_location_label))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(R.dimen.space_between_inputs_texts_buttons)),
                    value = codeFieldState.value,
                    onValueChange = { viewModel.onCodeUpdated(it) },
                    isError = codeFieldState.error != null
                            && codeFieldState.error != TextFieldState.Error.TOO_SHORT,
                    label = { Text(text = stringResource(R.string.input_device_code_label))}
                )
                if (codeFieldState.error != null) {
                    Text(
                        text = stringResource(codeFieldState.error!!.messageResId),
                        modifier = Modifier
                            .padding(top = dimensionResource(R.dimen.space_between_inputs_texts_buttons)),
                        color = when (codeFieldState.error) {
                            TextFieldState.Error.TOO_LARGE -> {
                                MaterialTheme.colorScheme.error
                            }
                            TextFieldState.Error.TOO_SHORT -> {
                                MaterialTheme.colorScheme.inverseSurface
                            }
                            else -> MaterialTheme.colorScheme.onSurface
                        },
                    )
                }
                Button(
                    enabled = run { codeFieldState.error == null },
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
    DeviceManualConnectScreen()
}