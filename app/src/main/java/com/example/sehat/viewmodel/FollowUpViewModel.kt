package com.example.sehat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sehat.data.SehatDatabase
import com.example.sehat.data.entity.FollowUpTask
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FollowUpViewModel(application: Application) : AndroidViewModel(application) {
    private val db = SehatDatabase.get(application)

    private val _selectedTab = MutableStateFlow("Pending") // Pending, Completed, All
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val tasks: StateFlow<List<FollowUpTask>> = _selectedTab
        .flatMapLatest { tab ->
            when (tab) {
                "Pending" -> db.followUpTaskDao().getTasksByStatus("Pending")
                "Completed" -> db.followUpTaskDao().getTasksByStatus("Completed")
                else -> db.followUpTaskDao().getAllTasks()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectTab(tab: String) { _selectedTab.value = tab }

    fun toggleTaskStatus(task: FollowUpTask) {
        viewModelScope.launch {
            val newStatus = if (task.status == "Pending") "Completed" else "Pending"
            db.followUpTaskDao().updateStatus(task.id, newStatus)
        }
    }
}
