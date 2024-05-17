package com.dz.bmstu_trade.ui.main.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dz.bmstu_trade.R
import com.dz.bmstu_trade.data.vo.DeviceVO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceListBottomSheet(
    sheetState: SheetState,
    devices: List<DeviceVO>,
    onDeviceClick: (DeviceVO) -> Unit,
    onValueChange: (Boolean) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { onValueChange(false) },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items(devices) { device ->
                DeviceItem(device, onDeviceClick)
            }
        }
    }
}

@Composable
fun DeviceItem(device: DeviceVO, onDeviceClick: (DeviceVO) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onDeviceClick(device) },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = device.name, fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurface)
            Text(
                text =
                if (device.isConnected)
                    stringResource(R.string.device_connected)
                else
                    stringResource(
                        R.string.device_not_connected
                    ),
                fontSize = 14.sp,
                color = if (device.isConnected) Color.Green else Color.Red
            )
        }
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.device_icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}
