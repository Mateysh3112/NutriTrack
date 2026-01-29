package com.fit2081.a1mateysh33878463.data.admin

import android.app.Application
import com.fit2081.a1mateysh33878463.BuildConfig
import com.fit2081.a1mateysh33878463.data.AppDatabase
import com.fit2081.a1mateysh33878463.data.patient.Patient
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AdminRepository(application: Application) {
    private val patientDao = AppDatabase.getDatabase(application).patientDao()
    private val model = GenerativeModel("gemini-1.5-flash", BuildConfig.apiKey)

    suspend fun getMaleAverage(): String = withContext(Dispatchers.IO) {
        val males = patientDao.getAllMalePatients()
        val avg = males.mapNotNull { it.heifaScoreMale.toFloatOrNull() }.average()
        "%.2f".format(avg)
    }

    suspend fun getFemaleAverage(): String = withContext(Dispatchers.IO) {
        val females = patientDao.getAllFemalePatients()
        val avg = females.mapNotNull { it.heifaScoreFemale.toFloatOrNull() }.average()
        "%.2f".format(avg)
    }

    suspend fun getAllPatients(): List<Patient> = withContext(Dispatchers.IO) {
        patientDao.getAllPatients()
    }

    suspend fun generateInsightsPrompt(patients: List<Patient>): String {
        val summary = patients.joinToString("\n") {
            val fruits = if (it.sex == "Male") it.heifafruitsMale else it.heifafruitsFemale
            val veg = if (it.sex == "Male") it.heifaVegMale else it.heifaVegFemale
            "${it.sex}, Fruits=$fruits, Veg=$veg"
        }

        return """
            You are analyzing HEIFA scores of patients.
            Data:
            $summary

            Write 3 unique patterns or trends observed across patients.
            Make them concise and insightful.
        """.trimIndent()
    }

    suspend fun getPatternInsights(prompt: String): String = withContext(Dispatchers.IO) {
        val result = model.generateContent(prompt)
        result.text ?: "No output generated"
    }
}
