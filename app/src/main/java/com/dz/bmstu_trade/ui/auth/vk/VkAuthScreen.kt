package com.dz.bmstu_trade.ui.auth.vk

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dz.bmstu_trade.R

@Composable
fun VkAuthScreen(
    navController: NavHostController,
    viewModel: VkAuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onSignIn: () -> Unit,
) {
    val authActivityLauncher = rememberLauncherForActivityResult(
        contract = VK.getVKAuthActivityResultContract(),
        onResult = {
            when (it) {
                is VKAuthenticationResult.Failed -> {
                    viewModel.onFailure(it.exception)
                }
                is VKAuthenticationResult.Success -> {
                    onSignIn()
                }
            }
        }
    )

    val authState by viewModel.authState.collectAsState()
    when (val currentAuthState = authState) {
        is VkAuthState.Loading -> {
            LoadingContent()
        }
        is VkAuthState.Error -> {
            ErrorContent(currentAuthState, onRetry = viewModel::onRetry)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.eventsFlow.collectLatest {
            when (it) {
                is VkAuthEvent.StartAuth -> {
                    authActivityLauncher.launch(emptyList())
                }
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp
        )
    }
}

@Composable
private fun ErrorContent(
    errorState: VkAuthState.Error,
    onRetry : () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.vk_auth_error_padding)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.vk_auth_failed),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.vk_auth_error_padding)))
        Text(
            text = stringResource(R.string.vk_auth_failed_subtitle),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.vk_auth_error_padding)))
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            onClick = { onRetry() }
        ) {
            Text(
                text = stringResource(R.string.vk_auth_retry),
                color = colorResource(R.color.white),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}