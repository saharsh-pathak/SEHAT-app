package com.example.sehat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sehat.data.SehatDatabase
import com.example.sehat.data.entity.FollowUpTask
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

data class ScheduleItem(
    val time: String,
    val title: String,
    val patientName: String,
    val village: String
)

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val db = SehatDatabase.get(application)

    val pendingFollowUpsCount: StateFlow<Int> = db.followUpTaskDao().getPendingCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 5)

    val todaySchedule: List<ScheduleItem> = listOf(
        ScheduleItem("09:00", "Home Visit", "Sita Devi", "Khed"),
        ScheduleItem("10:30", "ANC Follow-up", "Lata Shinde", "Nandgaon"),
        ScheduleItem("01:00", "Village Health Camp", "Community", "Khed PHC"),
        ScheduleItem("03:00", "New Screening", "Ramesh Pawar", "Community Centre")
    )
}
