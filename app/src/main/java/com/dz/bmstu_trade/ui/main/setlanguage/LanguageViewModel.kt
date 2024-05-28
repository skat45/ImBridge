package com.dz.bmstu_trade.ui.main.setlanguage

import android.app.Application
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.intellij.lang.annotations.Language
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(private  val localeState:MutableStateFlow<Locale>): ViewModel() {
    val currentLocale: StateFlow<Locale> = localeState

    fun setLanguage(language: String){
        val locale = Locale(language)
        Locale.setDefault(locale)
        localeState.value=locale
    }
}