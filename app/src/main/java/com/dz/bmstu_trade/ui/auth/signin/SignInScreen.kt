package com.dz.bmstu_trade.ui.auth.signin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun SignInScreen(navController: NavHostController, onSignIn: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "IMBRIDGE",
            modifier = Modifier.padding(top = 128.dp),
            color = Color(0xFF6A1B9A),
            fontFamily = FontFamily.SansSerif,
            style = TextStyle(fontSize = 40.sp),
        )

        Spacer(modifier = Modifier.height(116.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = { Text("e-mail") },
            textStyle = TextStyle(fontSize = 16.sp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль") },
            textStyle = TextStyle(fontSize = 16.sp),
        )

        Spacer(modifier = Modifier.height(180.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { /* Handle login button click */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
            ) {
                Text("Вход", color = Color.White, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { /* Handle registration button click */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF6A1B9A)),
                border = BorderStroke(width = 1.dp, color = Color(0xFF6A1B9A))
            ) {
                Text("Регистрация", color = Color(0xFF6A1B9A), fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Также можно",
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black,
                fontSize = 10.sp,

                )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(), //0x0077FF
                onClick = { /* Handle registration button click */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Blue),
                border = BorderStroke(width = 1.dp, color = Color.Blue)
            ) {
                Text("Войти через VK ID", color = Color.Blue, fontSize = 14.sp)
            }

        }
    }
}
