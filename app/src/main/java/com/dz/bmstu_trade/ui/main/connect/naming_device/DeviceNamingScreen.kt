package com.dz.bmstu_trade.ui.main.connect.naming_device

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
import com.dz.bmstu_trade.R

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
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            ),
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.arrow_back_icon_description),
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
                    label = { Text(text = stringResource(R.string.device_name_label)) }
                )

                if (nameFieldState.error != null) {
                    Text(
                        text = stringResource(nameFieldState.error!!.messageResId),
                        modifier = Modifier
                            .padding(top = dimensionResource(R.dimen.space_between_inputs_texts_buttons)),
                        color = MaterialTheme.colorScheme.error,
                    )
                }

                Button(
                    enabled = nameFieldState.error == null,
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