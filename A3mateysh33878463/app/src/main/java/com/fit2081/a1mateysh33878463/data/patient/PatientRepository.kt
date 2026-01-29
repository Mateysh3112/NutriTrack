package com.fit2081.a1mateysh33878463.data.patient

import androidx.lifecycle.LiveData

class PatientRepository (private val dao: PatientDao) {
    suspend fun insertAll(patients: List<Patient>) = dao.insertAll(patients)
    suspend fun getPatient(id: String): Patient? = dao.getPatient(id)
    suspend fun getPatientById(userId: String): Patient? {
        return dao.getPatientById(userId)
    }

    suspend fun updateNameAndPassword(userId: String, name: String, password: String) {
        dao.updateNameAndPassword(userId, name, password)
    }

    fun getAllUserIds(): LiveData<List<String>> = dao.getAllUserIds()


}