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
    DEV_MAN_CONNECT("home/deviceManual"),
    HOME_ROOT("home/root"),
    CANVAS("canvas"),
    MAIN("main")
}