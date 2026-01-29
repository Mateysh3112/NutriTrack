package com.fit2081.a1mateysh33878463.data.registerandlogin

import com.fit2081.a1mateysh33878463.data.foodIntake.FoodIntakeDao
import com.fit2081.a1mateysh33878463.data.patient.Patient
import com.fit2081.a1mateysh33878463.data.patient.PatientDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRegisterRepository(
    private val patientDao: PatientDao,
    private val foodIntakeDao: FoodIntakeDao
) {

    suspend fun loginWithCheck(userId: String, password: String): Triple<Boolean, Patient?, Boolean> {
        return withContext(Dispatchers.IO) {
            val patient = patientDao.getPatientById(userId)
            val foodIntake = foodIntakeDao.getFoodIntakeImmediate(userId)

            val completed = foodIntake != null &&
                    foodIntake.persona.isNotBlank() &&
                    foodIntake.biggestMeal != "00:00" &&
                    foodIntake.sleepTime != "00:00" &&
                    foodIntake.wakeTime != "00:00"

            if (patient != null && patient.password == password) {
                Triple(true, patient, completed)
            } else {
                Triple(false, null, false)
            }
        }
    }

    suspend fun register(userId: String, phone: String, name: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val patient = patientDao.getPatientById(userId)
            if (patient != null && patient.phoneNumber == phone) {
                patientDao.updateNameAndPassword(userId, name, password)
                true
            } else {
                false
            }
        }
    }

    fun getAllUserIds() = patientDao.getAllUserIds()
}
