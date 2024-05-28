package com.dz.bmstu_trade.ui.main.connect.wifi_picker

sealed interface WiFiPickerEvent {
    class ShowPermissionsAlertDialog: WiFiPickerEvent
}