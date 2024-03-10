package com.dz.bmstu_trade.ui.main.settings


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

data class Parameters(val width: Double = 0.0,
                      val height: Double = 40.0,

                      val fromBottom: Double = 0.0,
                      val fromTop: Double = 0.0,

                      val fromRight:Double = 0.0,
                      val fromLeft:Double = 0.0,

                      val color: Color = Color.Black,
                      val backgroundColor: Color = Color.Black)
@Composable
fun SettingsScreen(navController: NavHostController, viewModel: SettingsViewModel = remember { SettingsViewModel() }) {
    val emailFieldState by viewModel.email.collectAsState()
    val switchState by viewModel.switch.collectAsState()

    val paramForLanguageButton = Parameters(width = 330.0, fromTop = 40.0, color = MaterialTheme.colorScheme.primary)
    val paramForMail = Parameters(fromTop = 243.0, color = MaterialTheme.colorScheme.primary)
    val paramForLeaveButton = Parameters(width = 175.0, fromTop = 24.0, backgroundColor = MaterialTheme.colorScheme.secondaryContainer, color = MaterialTheme.colorScheme.onSecondaryContainer)


    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paramForLanguageButton.fromTop.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        //Выбор языка
        TextButton(
            modifier = Modifier
                .width(paramForLanguageButton.width.dp)
                .height(paramForLanguageButton.height.dp),
            shape = RoundedCornerShape(10.dp),
            onClick = { }
        )
        {
            Icon(painter = painterResource(R.drawable.baseline_language_24), contentDescription = "Сменить язык", modifier = Modifier.weight(1f))
            Text("Выбрать язык", modifier = Modifier.weight(7f), fontWeight = FontWeight.Medium)
            Icon(Icons.Rounded.KeyboardArrowRight, contentDescription = "Стрелочка", modifier = Modifier.weight(1f))

        }

        //Switch для смены темы
        Row(
            modifier = Modifier
                .width(paramForLanguageButton.width.dp)
                .height(paramForLanguageButton.height.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            //var checked = remember { mutableStateOf (false) }
            Text(text = "", modifier = Modifier.weight(1f))
            Text(text = "Включить темную тему", modifier = Modifier.weight(8f), color = paramForLanguageButton.color, fontWeight = FontWeight.Medium)
            Switch (
                checked = switchState.value,
                onCheckedChange = {
                    viewModel.onSwitchChanged(it)
                }, modifier = Modifier.weight(4f))
        }

        //e-mail
        Text(text = emailFieldState.value, modifier = Modifier.padding(top = paramForMail.fromTop.dp), color = paramForMail.color, fontWeight = FontWeight.Medium)

        //Кнопка выхода из аккаунта
        Button(
            onClick = { /* TODO */ }, modifier = Modifier
                .padding(top = paramForLeaveButton.fromTop.dp)
                .width(paramForLeaveButton.width.dp)
                .height(paramForLeaveButton.height.dp),
            colors = ButtonDefaults.buttonColors(paramForLeaveButton.backgroundColor)
        )
        {
            Text(text = "Выйти из аккаунта", color = paramForLeaveButton.color, fontWeight = FontWeight.Medium)
        }

    }
}

