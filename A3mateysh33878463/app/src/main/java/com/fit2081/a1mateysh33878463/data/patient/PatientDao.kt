package com.fit2081.a1mateysh33878463.data.patient

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(patients: List<Patient>)

    @Query("SELECT * FROM patients WHERE userId = :id")
    suspend fun getPatient(id: String): Patient?

    @Query("SELECT * FROM patients WHERE userId = :userId")
    suspend fun getPatientById(userId: String): Patient?

    @Query("UPDATE patients SET name = :name, password = :password WHERE userId = :userId")
    suspend fun updateNameAndPassword(userId: String, name: String, password: String)

    @Query("SELECT userId FROM patients")
    fun getAllUserIds(): LiveData<List<String>>

    @Query("SELECT * FROM patients WHERE sex = 'Male'")
    fun getAllMalePatients(): List<Patient>

    @Query("SELECT * FROM patients WHERE sex = 'Female'")
    fun getAllFemalePatients(): List<Patient>

    @Query("SELECT * FROM patients")
    fun getAllPatients(): List<Patient>
}



