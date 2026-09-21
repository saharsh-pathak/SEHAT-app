package com.example.sehat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sehat.data.SehatDatabase
import com.example.sehat.data.entity.Patient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TimelineItemData(
    val date: String,
    val title: String,
    val subtitle: String,
    val details: String,
    val dayBadge: String
)

class TimelineViewModel(application: Application) : AndroidViewModel(application) {
    private val db = SehatDatabase.get(application)

    private val _patient = MutableStateFlow<Patient?>(null)
    val patient: StateFlow<Patient?> = _patient.asStateFlow()

    val timelineItems = listOf(
        TimelineItemData("16 Sep 2025", "Home Screening (Medical Worker)", "Fever, cough | Triage: Mild", "Vitals stable, URTI probable", "1"),
        TimelineItemData("18 Sep 2025", "Visit at AAM-SHC", "Prescribed supportive care", "Paracetamol 500mg, Rest & hydration", "3"),
        TimelineItemData("20 Sep 2025", "Lab Report", "CBC - Normal", "Hemoglobin 11.2 g/dL", "20"),
        TimelineItemData("25 Sep 2025", "PHC Visit", "Follow-up scheduled", "Recovery positive", "3")
    )

    fun loadPatient(abhaId: String) {
        viewModelScope.launch {
            _patient.value = db.patientDao().getByAbhaId(abhaId) ?: Patient(
                abhaId = abhaId,
                name = "Sita Devi",
                age = 34,
                gender = "Female",
                village = "Khed"
            )
        }
    }
}
