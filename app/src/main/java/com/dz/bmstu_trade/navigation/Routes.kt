package com.dz.bmstu_trade.navigation

/**
 * Для хранения айдишников экранов из графа
 */
enum class Routes(val value: String) {
    GALLERY("gallery"),
    HOME("home"),
    SETTINGS("settings"),
    SETTINGS_ROOT("settings/root"),
    SETTINGS_LANGUAGE("settings/setLanguage"),
    AUTH("auth"),
    SIGN_IN("signIn"),
    SIGN_UP("signUp"),
    ENTER_DEV_CODE("home/deviceManual"),
    CONNECTING_PROGRESS("home/connectingProgress"),
    CHOOSE_WIFI("home/chooseWiFi"),
    ENTER_WIFI_PASSWORD("home/enterWiFiPassword"),
    HOME_ROOT("home/root"),
    CANVAS("canvas"),
    MAIN("main"),
    VK_AUTH("vkAuth")
}