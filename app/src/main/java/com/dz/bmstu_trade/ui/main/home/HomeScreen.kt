package com.dz.bmstu_trade.ui.main.home

import androidx.compose.foundation.background
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dz.bmstu_trade.R
import com.dz.bmstu_trade.navigation.Routes
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Column (
        modifier = Modifier.padding(16.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Button(
            onClick = { navController.navigate(Routes.ENTER_DEV_CODE.value) },
            modifier = Modifier.padding(top = 16.dp)

    var sliderPosition by remember { mutableFloatStateOf(0f) }
    var isDisplayOnSwitchChecked by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    val devices = persistentListOf("Device 1", "Device 2", "Device 3")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AddDeviceButton {
            showBottomSheet = true
        }

        DeviceNameTextField(devices = devices)

        Spacer(modifier = Modifier.height(16.dp))

        DeviceImage(
            onEditButtonClick = {
                navController.navigate(Routes.CANVAS.value)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

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
            },
        )

        DisplayTurnOnSwitch(
            isDisplayOnSwitchChecked = isDisplayOnSwitchChecked,
            onValueChange = { newValue ->
                isDisplayOnSwitchChecked = newValue
            },
        )
    }

    if (showBottomSheet) {
        HomeBottomSheet(
            sheetState,
            onValueChange = {
                showBottomSheet = it
            },
            onManualConnectClick = {
                navController.navigate(Routes.DEV_MAN_CONNECT.value)
            },
            onQrConnectClick = {
                // Todo: перейти на экран подключения по qr-коду
            },
        )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceNameTextField(devices: ImmutableList<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Device") }
    var textFieldValue by remember { mutableStateOf(selectedOption) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.home_screen_horizontal_padding))
    ) {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            label = { Text(stringResource(R.string.device_name_text_field)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            readOnly = true,
            maxLines = 1
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier.exposedDropdownSize(true)
        ) {
            devices.forEach { device ->
                DropdownMenuItem(
                    text = { Text(device) },
                    onClick = {
                        selectedOption = device
                        textFieldValue = device
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DeviceImage(onEditButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(300.dp)
            .padding(horizontal = dimensionResource(R.dimen.home_screen_horizontal_padding))
            .fillMaxWidth()
            .background(Color.LightGray, RoundedCornerShape(10.dp))
            .clickable { onEditButtonClick() },
        contentAlignment = Alignment.Center
    ) {

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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        HomeScreen(navController = navController)
    }
}