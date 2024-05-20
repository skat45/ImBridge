package com.dz.bmstu_trade.ui.main.setlanguage

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.intellij.lang.annotations.Language

class LanguageViewModel {
    private val _selectedLanguage = MutableStateFlow("ru")
    val selectedLanguage: StateFlow<String> = _selectedLanguage
    fun setLanguage(language: String){
        _selectedLanguage.value = language
    }
}