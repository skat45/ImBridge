package com.dz.bmstu_trade.ui.main.settings


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import com.dz.bmstu_trade.R
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = remember { SettingsViewModel() }
) {
    val emailField by viewModel.email.collectAsState()
    val switchState by viewModel.switch.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(top = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                SelectLanguageItem(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            ChangeThemeItem(
                changed = switchState.value,
                onClick = { viewModel.onSwitchChanged(!switchState.value) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier.weight(1f)

        ) {
            ExitFromAccountItem(
                emailField = emailField,
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxSize()
            )


        }
    }
}

@Composable
private fun SelectLanguageItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_language_24),
                contentDescription = stringResource(R.string.Change_language),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = stringResource(R.string.Change_language),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 14.dp)
            )

        }
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowRight,
            contentDescription = stringResource(R.string.Arrow_descr),
            modifier = Modifier.padding(horizontal = 16.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

    }


}

@Composable
private fun ChangeThemeItem(
    changed: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Spacer(modifier = Modifier.width((24 + 16).dp))
            Text(
                text = stringResource(R.string.Turn_on_darkTheme),
                modifier = Modifier.padding(horizontal = 14.dp),
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
        }
        Switch(
            checked = changed, onCheckedChange = {
                onClick()
            }, modifier = Modifier.padding(horizontal = 16.dp)
        )

    }
}

@Composable
private fun ExitFromAccountItem(
    emailField: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = emailField,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )

        Button(
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Text(
                text = stringResource(R.string.Exit_from_account),
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

