package com.example.sehat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val _selectedLanguage = MutableStateFlow("Marathi") // Marathi, Hindi, English
    val selectedLanguage: StateFlow<String> = _selectedLanguage.asStateFlow()

    fun selectLanguage(language: String) {
        _selectedLanguage.value = language
    }
}
