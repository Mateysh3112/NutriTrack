package com.fit2081.a1mateysh33878463.data.insights


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.a1mateysh33878463.data.AppDatabase
import com.fit2081.a1mateysh33878463.data.patient.Patient
import com.fit2081.a1mateysh33878463.data.patient.PatientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InsightsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PatientRepository(AppDatabase.getDatabase(application).patientDao())

    private val _patient = MutableStateFlow<Patient?>(null)
    val patient: StateFlow<Patient?> = _patient.asStateFlow()

    fun loadPatient(userId: String) {
        viewModelScope.launch {
            _patient.value = repository.getPatientById(userId)
        }
    }
}
