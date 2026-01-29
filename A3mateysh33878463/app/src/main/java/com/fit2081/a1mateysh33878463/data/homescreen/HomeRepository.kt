package com.fit2081.a1mateysh33878463.data.homescreen

import android.app.Application
import com.fit2081.a1mateysh33878463.data.AppDatabase
import com.fit2081.a1mateysh33878463.data.patient.Patient

class HomeRepository(application: Application) {
    private val patientDao = AppDatabase.getDatabase(application).patientDao()

    suspend fun getPatient(userId: String): Patient? {
        return patientDao.getPatientById(userId)
    }

    fun calculateHeifaScore(patient: Patient): String {
        return when (patient.sex) {
            "Male" -> patient.heifaScoreMale
            "Female" -> patient.heifaScoreFemale
            else -> "N/A"
        }
    }
}