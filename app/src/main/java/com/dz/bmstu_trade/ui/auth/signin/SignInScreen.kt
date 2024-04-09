package com.dz.bmstu_trade.ui.auth.signin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dz.bmstu_trade.R
import androidx.compose.ui.text.input.TextFieldValue
import com.dz.bmstu_trade.navigation.Routes
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun SignInScreen(
    navController: NavHostController,
    viewModel: SignInViewModel = viewModel(),
    onSignIn: () -> Unit,
    ) {
    val signinFieldState by viewModel.signin.collectAsState()

    var email by remember { mutableStateOf(TextFieldValue()) }
    var passwordVisibility by remember { mutableStateOf(false) }
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
                trailingIcon = {
                    if (email.text.isNotEmpty()) {
                        IconButton(onClick = { email = TextFieldValue() }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_clean_field_20),
                                contentDescription = "Clear text",
                            )
                        }
                    }
                },
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = signinFieldState.value,
                onValueChange = { viewModel.onPasswordUpdated(it) },
                isError = signinFieldState.error != null
                        && signinFieldState.error != PasswordFieldState.Error.TOO_SHORT,
                label = { Text(text = stringResource(R.string.Password)) },
                textStyle = MaterialTheme.typography.bodyMedium,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = { /* Handle done action if needed */ }
                ),
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(
                            painter = if (passwordVisibility) painterResource(R.drawable.password_shown)
                            else painterResource(R.drawable.password_not_shown),
                            contentDescription = stringResource(R.string.show_password_icon_description)
                        )
                    }
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None
                else PasswordVisualTransformation()
            )
            if (signinFieldState.error != null) {
                Text(
                    text = stringResource(signinFieldState.error!!.messageResId),
                    modifier = Modifier
                        .padding(top = dimensionResource(R.dimen.space_between_inputs_texts_buttons)),
                    color = when (signinFieldState.error) {
                        PasswordFieldState.Error.TOO_LONG -> {
                            MaterialTheme.colorScheme.error
                        }
                        PasswordFieldState.Error.TOO_SHORT -> {
                            MaterialTheme.colorScheme.inverseSurface
                        }
                        else -> MaterialTheme.colorScheme.onSurface
                    },
                )
            }
        }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(dimensionResource(R.dimen.enter_register_padding)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onSignIn,
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
                    onClick = { navController.navigate(Routes.SIGN_UP.value) },
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
