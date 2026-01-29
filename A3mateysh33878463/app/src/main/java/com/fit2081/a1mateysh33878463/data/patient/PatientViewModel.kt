package com.fit2081.a1mateysh33878463.data.patient

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.fit2081.a1mateysh33878463.data.AppDatabase
import kotlinx.coroutines.launch

class PatientViewModel(application: Application): AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).patientDao()
    private val repository = PatientRepository(dao)

    suspend fun getPatientById(userId: String) = repository.getPatientById(userId)

    fun updateNameAndPassword(userId: String, name: String, password: String) {
        viewModelScope.launch {
            repository.updateNameAndPassword(userId, name, password)

        }
    }

    fun getAllUserIds(): LiveData<List<String>> = repository.getAllUserIds()

}