package com.example.sehat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sehat.data.SehatDatabase
import com.example.sehat.data.entity.Medicine
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class MedicineViewModel(application: Application) : AndroidViewModel(application) {
    private val db = SehatDatabase.get(application)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedMedicine = MutableStateFlow<Medicine?>(null)
    val selectedMedicine: StateFlow<Medicine?> = _selectedMedicine.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val medicines: StateFlow<List<Medicine>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) db.medicineDao().getAllMedicines()
            else db.medicineDao().searchMedicines(query.trim())
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val facilityStock: StateFlow<List<Medicine>> = _selectedMedicine
        .flatMapLatest { medicine ->
            if (medicine == null) flowOf(emptyList())
            else db.medicineDao().getFacilitiesForMedicine(medicine.name)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allFacilityStocks: StateFlow<List<Medicine>> = db.medicineDao().getAllFacilityStocks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateSearch(query: String) { _searchQuery.value = query }
    fun selectMedicine(medicine: Medicine?) { _selectedMedicine.value = medicine }
}
