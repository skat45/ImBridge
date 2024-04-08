package com.dz.bmstu_trade.ui.main.connect.permission_dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.dz.bmstu_trade.R

@Composable
fun PermissionAlertDialog(
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = stringResource(R.string.access_permission_title))
        },
        text = {
            Text(text = stringResource(R.string.required_access_to_fine_location_wifi_list_label))
        },
        onDismissRequest = { onDismissRequest() }, //isAlertDialogShown = false
        confirmButton = {
            TextButton(onClick = {
                onDismissRequest()
                GoToSettingsButtonOnClick()
            }) {
                Text(text = stringResource(R.string.to_settings_label))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = stringResource(R.string.cancel_label))
            }
        }
    )
}