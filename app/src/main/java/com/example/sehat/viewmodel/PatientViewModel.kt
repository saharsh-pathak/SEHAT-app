package com.example.sehat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sehat.data.SehatDatabase
import com.example.sehat.data.entity.Patient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PatientViewModel(application: Application) : AndroidViewModel(application) {
    private val db = SehatDatabase.get(application)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val patients: StateFlow<List<Patient>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                db.patientDao().getAllPatients()
            } else {
                db.patientDao().searchPatients(query)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun createAbhaPatient(name: String, age: Int, gender: String, village: String, aadhaar: String, mobile: String, onCreated: (Patient) -> Unit) {
        viewModelScope.launch {
            val generatedAbha = "${(1000..9999).random()} ${(1000..9999).random()} ${(1000..9999).random()}"
            val newPatient = Patient(
                abhaId = generatedAbha,
                aadhaarRef = aadhaar,
                name = name,
                age = age,
                gender = gender,
                village = village,
                mobile = mobile
            )
            db.patientDao().insert(newPatient)
            onCreated(newPatient)
        }
    }
}
