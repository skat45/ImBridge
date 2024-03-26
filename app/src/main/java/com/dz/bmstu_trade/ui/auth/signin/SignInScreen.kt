package com.dz.bmstu_trade.ui.auth.signin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dz.bmstu_trade.R


@Composable
fun SignInScreen(navController: NavHostController, onSignIn: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = stringResource(R.string.Logo),
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.logo_padding))
                .align(alignment = Alignment.TopCenter),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 40.sp)
        )

        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.email_password_padding))
                .align(alignment = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                label = { Text(text = stringResource(R.string.Email)) },
                textStyle = MaterialTheme.typography.bodyMedium,
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it },
                label = { Text(text = stringResource(R.string.Password)) },
                textStyle = MaterialTheme.typography.bodyMedium,
            )
        }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(dimensionResource(R.dimen.enter_register_padding)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { /* Handle login button click */ },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = stringResource(R.string.Login),
                        color = colorResource(R.color.white),
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { /* Handle registration button click */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = stringResource(R.string.Register),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Text(
                    text = stringResource(R.string.also_you_can),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = colorResource(R.color.black),
                    style = MaterialTheme.typography.bodySmall
                )

                Button(
                    modifier = Modifier.fillMaxWidth(), //0x0077FF
                    onClick = { /* Handle registration button click */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.white),
                        contentColor = colorResource(R.color.vk_blue)
                    ),
                    border = BorderStroke(width = 1.dp, color = colorResource(R.color.vk_blue))
                ) {
                    Text(
                        text = stringResource(R.string.Log_in_with_VK_ID),
                        color = colorResource(R.color.vk_blue),
                        style = MaterialTheme.typography.labelLarge
                    )
                }

            }
    }
}
