package com.example.sehat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sehat.data.SehatDatabase
import com.example.sehat.data.entity.Appointment
import com.example.sehat.data.entity.Referral
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReferralViewModel(application: Application) : AndroidViewModel(application) {
    private val db = SehatDatabase.get(application)

    private val _createdAppointment = MutableStateFlow<Appointment?>(null)
    val createdAppointment: StateFlow<Appointment?> = _createdAppointment.asStateFlow()

    fun createReferralAndAutoBook(
        episodeId: Long,
        patientAbhaId: String,
        destinationFacility: String,
        notes: String,
        onComplete: (Long) -> Unit
    ) {
        viewModelScope.launch {
            val referral = Referral(
                episodeId = episodeId,
                patientAbhaId = patientAbhaId,
                destinationFacility = destinationFacility,
                priority = "Routine",
                notes = notes
            )
            val refId = db.referralDao().insert(referral)

            val apptCode = "APT${(1000000..9999999).random()}"
            val appointment = Appointment(
                appointmentCode = apptCode,
                referralId = refId,
                patientAbhaId = patientAbhaId,
                facility = destinationFacility,
                dateTime = "17 Sep 2025, 10:00 AM",
                consultationType = "General Consultation",
                queueToken = "Token #${(1..25).random()}"
            )
            val apptId = db.appointmentDao().insert(appointment)
            _createdAppointment.value = appointment.copy(appointmentId = apptId)
            onComplete(refId)
        }
    }
}
