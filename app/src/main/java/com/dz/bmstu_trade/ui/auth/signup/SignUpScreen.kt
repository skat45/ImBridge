package com.dz.bmstu_trade.ui.auth.signup

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dz.bmstu_trade.R
import com.dz.bmstu_trade.navigation.Routes
import com.dz.bmstu_trade.ui.auth.signin.SignInViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = viewModel(),
    onSignUp: () -> Unit,
    ) {
    var email by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf("") }
    var submitPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        TopAppBar(
            title = { Text(text = stringResource(R.string.naming_top_bar_register)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            ),
            navigationIcon = {
                IconButton(onClick = { navController.navigate(Routes.SIGN_IN.value) }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.arrow_back_icon_description)
                    )
                }
            }
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
                                contentDescription = stringResource(R.string.clear_text),
                            )
                        }
                    }
                },
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = {
                    password = it
                    viewModel.onPasswordUpdated(it)
                },
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
                else PasswordVisualTransformation(),
                isError = viewModel.enterPassword.value.error != null
            )

            if (viewModel.enterPassword.value.error != null) {
                Text(
                    text = stringResource(viewModel.enterPassword.value.error!!.messageResId),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleSmall,
                )
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = submitPassword,
                onValueChange = {
                    submitPassword = it
                    viewModel.onConfirmPasswordUpdated(it)
                },
                label = { Text(text = stringResource(R.string.submitPassword)) },
                textStyle = MaterialTheme.typography.bodyMedium,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = { /* Handle done action if needed */ }
                ),
                visualTransformation =
                if (passwordVisibility) VisualTransformation.None
                else PasswordVisualTransformation(),
                isError =
                viewModel.repeatPassword.value.error != null ||
                        viewModel.repeatPassword.value.error == PasswordFieldState.Error.PASSWORDS_MISMATCH
            )

            if (viewModel.repeatPassword.value.error != null) {
                Text(
                    text = stringResource(viewModel.repeatPassword.value.error!!.messageResId),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleSmall,
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.submit_register)))

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { navController.navigate(Routes.SIGN_IN.value) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = stringResource(R.string.Register),
                    color = colorResource(R.color.white),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
