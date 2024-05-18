package com.dz.bmstu_trade.ui.main.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dz.bmstu_trade.R
import com.dz.bmstu_trade.data.model.DeviceState
import com.dz.bmstu_trade.navigation.Routes
import com.dz.bmstu_trade.ui.main.canvas.DrawingGrid
import com.dz.bmstu_trade.ui.main.canvas.InteractMode
import com.dz.bmstu_trade.ui.main.canvas.Picture

@Composable
fun DevicePanel(
    navController: NavHostController,
    deviceState: DeviceState,
    viewModel: HomeViewModel,
    openDeviceListSheet: () -> Unit,
    openAddDeviceSheet: () -> Unit,
) {
    var sliderPosition = deviceState.brightness / 100f
    var isDisplayOnSwitchChecked = deviceState.isOn
    var deviceName = deviceState.name

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        DeviceNameTextField(
            deviceName = deviceName,
            onValueChange = {
                viewModel.sendState(
                    name = it,
                    brightness = (sliderPosition * 100).toInt(),
                    isOn = isDisplayOnSwitchChecked
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        DeviceImage(
            picture = viewModel.pictureState,
            onEditButtonClick = {
                navController.navigate(Routes.CANVAS.value)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        MoreImages(
            onMoreButtonClick = {
                /* Todo: Перейти в галлерею? */
            },
        )

        Spacer(modifier = Modifier.height(24.dp))

        BrightnessSlider(
            sliderPosition = sliderPosition,
            onValueChange = { newValue ->
                sliderPosition = newValue
                viewModel.sendState(
                    name = deviceName,
                    brightness = (sliderPosition * 100).toInt(),
                    isOn = isDisplayOnSwitchChecked
                )
            },
        )

        DisplayTurnOnSwitch(
            isDisplayOnSwitchChecked = isDisplayOnSwitchChecked,
            onValueChange = { newValue ->
                isDisplayOnSwitchChecked = newValue
                viewModel.sendState(
                    name = deviceName,
                    brightness = (sliderPosition * 100).toInt(),
                    isOn = isDisplayOnSwitchChecked
                )
            },
        )

        AddDeviceButton(openAddDeviceSheet)

        DeviceListButton(openDeviceListSheet)
    }
}

@Composable
fun AddDeviceButton(onButtonClick: () -> Unit) {
    TextButton(
        onClick = onButtonClick,
        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.home_screen_horizontal_padding))
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.add_new_device), style = TextStyle(fontSize = 18.sp))
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_link_24),
                contentDescription = null
            )
        }
    }
}

@Composable
fun DeviceListButton(onButtonClick: () -> Unit) {
    TextButton(
        onClick = onButtonClick,
        modifier = Modifier
            .padding(horizontal = dimensionResource(R.dimen.home_screen_horizontal_padding))
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.change_device), style = TextStyle(fontSize = 18.sp))
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.device_icon),
                contentDescription = "Кнопка для просмотра списка устройств"
            )
        }
    }
}

@Composable
fun DeviceNameTextField(deviceName:String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = deviceName,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.home_screen_horizontal_padding)),
        label = { Text(stringResource(R.string.device_name_text_field)) },
        readOnly = false,
        maxLines = 1,
    )
}

@Composable
fun DeviceImage(picture: MutableState<Picture> ,onEditButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(400.dp)
            .padding(horizontal = dimensionResource(R.dimen.home_screen_horizontal_padding))
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        DrawingGrid(
            picture = picture,
            interactMode = remember {
                mutableStateOf(InteractMode.ShowState)
            }
        )
        IconButton(
            onClick = onEditButtonClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.edit_icon_button),
                contentDescription = stringResource(R.string.drawing_edit_button),
            )
        }
    }
}

@Composable
fun MoreImages(modifier: Modifier = Modifier, onMoreButtonClick: () -> Unit) {
    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                stringResource(R.string.change_picture),
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)
            )
            TextButton(onClick = onMoreButtonClick) {
                Text(stringResource(R.string.watch_other))
            }
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                Spacer(modifier = Modifier.width(12.dp))
            }
            items(6) { _ ->
                ImageItem(
                    Modifier
                        .size(100.dp)
                        .padding(horizontal = 4.dp)
                )
            }
            item {
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }
}

@Composable
fun BrightnessSlider(sliderPosition: Float, onValueChange: (Float) -> Unit) {
    Text(
        text = stringResource(R.string.brightness),
        Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.home_screen_horizontal_padding))
    )

    Slider(
        value = sliderPosition,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.home_screen_horizontal_padding))
    )
}

@Composable
fun DisplayTurnOnSwitch(isDisplayOnSwitchChecked: Boolean, onValueChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onValueChange(!isDisplayOnSwitchChecked) }
            .padding(
                horizontal = dimensionResource(R.dimen.home_screen_horizontal_padding),
                vertical = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(stringResource(R.string.turn_on_display), modifier = Modifier.weight(1f))
        Switch(
            checked = isDisplayOnSwitchChecked,
            onCheckedChange = null
        )
    }
}

@Composable
fun ImageItem(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
    }
}