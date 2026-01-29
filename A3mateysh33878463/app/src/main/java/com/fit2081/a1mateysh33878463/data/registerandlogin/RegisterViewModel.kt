package com.fit2081.a1mateysh33878463.data.registerandlogin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.fit2081.a1mateysh33878463.data.AppDatabase
import com.fit2081.a1mateysh33878463.data.patient.PatientRepository
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val repository = LoginRegisterRepository(
        db.patientDao(),
        db.foodIntakeDao()
    )


    fun getAllUserIds(): LiveData<List<String>> = repository.getAllUserIds()

    fun attemptRegister(
        userId: String,
        phone: String,
        name: String,
        password: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val success = repository.register(userId, phone, name, password)
            onResult(success)
        }
    }
}