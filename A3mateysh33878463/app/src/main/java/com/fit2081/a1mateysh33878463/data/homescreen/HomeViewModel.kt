package com.fit2081.a1mateysh33878463.data.homescreen
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.a1mateysh33878463.data.homescreen.HomeRepository
import com.fit2081.a1mateysh33878463.data.patient.Patient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = HomeRepository(application)

    private val _patient = MutableStateFlow<Patient?>(null)
    val patient: StateFlow<Patient?> = _patient.asStateFlow()

    private val _heifaScore = MutableStateFlow("N/A")
    val heifaScore: StateFlow<String> = _heifaScore.asStateFlow()

    private val _patientName = MutableStateFlow("Unknown")
    val patientName: StateFlow<String> = _patientName.asStateFlow()


    fun loadPatient(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val patientData = repository.getPatient(userId)
            _patient.value = patientData
            patientData?.let {
                _heifaScore.value = repository.calculateHeifaScore(it)
                _patientName.value = it.name
            }
        }
    }
}
