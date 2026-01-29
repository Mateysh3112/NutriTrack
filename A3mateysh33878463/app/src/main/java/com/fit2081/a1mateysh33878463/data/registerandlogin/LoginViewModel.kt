package com.fit2081.a1mateysh33878463.data.registerandlogin


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.fit2081.a1mateysh33878463.data.AppDatabase
import com.fit2081.a1mateysh33878463.data.patient.Patient
import com.fit2081.a1mateysh33878463.data.foodIntake.FoodIntakeDao
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val patientDao = db.patientDao()
    private val foodIntakeDao: FoodIntakeDao = db.foodIntakeDao()

    private val repository = LoginRegisterRepository(patientDao, foodIntakeDao)

    fun getAllUserIds(): LiveData<List<String>> {
        return repository.getAllUserIds()
    }

    fun attemptLogin(
        userId: String,
        password: String,
        onResult: (Boolean, Patient?, Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val result = repository.loginWithCheck(userId, password)
            onResult(result.first, result.second, result.third)
        }
    }

}
