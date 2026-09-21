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

    private val _selectedFilter = MutableStateFlow("All")
    val selectedFilter: StateFlow<String> = _selectedFilter.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val medicines: StateFlow<List<Medicine>> = combine(_searchQuery, _selectedFilter) { query, filter ->
        query to filter
    }.flatMapLatest { (query, filter) ->
        val flow = if (query.isBlank()) db.medicineDao().getAllMedicines() else db.medicineDao().searchMedicines(query)
        flow.map { list ->
            if (filter == "All") list
            else list.filter { it.status.equals(filter, ignoreCase = true) }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateSearch(query: String) { _searchQuery.value = query }
    fun updateFilter(filter: String) { _selectedFilter.value = filter }
}
