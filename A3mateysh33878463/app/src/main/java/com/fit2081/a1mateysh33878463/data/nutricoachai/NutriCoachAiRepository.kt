package com.fit2081.a1mateysh33878463.data.nutricoachai

import android.app.Application
import android.util.Log
import com.fit2081.a1mateysh33878463.data.AppDatabase
import com.fit2081.a1mateysh33878463.data.fruityvice.FruityViceApi
import com.fit2081.a1mateysh33878463.data.patient.Patient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NutriCoachAiRepository(private val application: Application) {

    private val db = AppDatabase.getDatabase(application)
    private val tipDao = db.nutriCoachTipDao()
    private val patientDao = db.patientDao()

    /**
     * Retrieves a Patient from the Room DB
     */
    suspend fun getPatient(userId: String): Patient = withContext(Dispatchers.IO) {
        patientDao.getPatientById(userId)!!
    }

    /**
     * Gets fruit info from FruityVice API
     */
    suspend fun getFruitDetails(fruitName: String): Map<String, String> = withContext(Dispatchers.IO) {
        return@withContext try {
            val fruit = FruityViceApi.create().getFruit(fruitName.lowercase())
            if (fruit != null) {
                mapOf(
                    "family" to fruit.family,
                    "calories" to fruit.nutritions.calories.toString(),
                    "fat" to fruit.nutritions.fat.toString(),
                    "sugar" to fruit.nutritions.sugar.toString(),
                    "carbohydrates" to fruit.nutritions.carbohydrates.toString(),
                    "protein" to fruit.nutritions.protein.toString()
                )
            } else {
                emptyMap()
            }
        } catch (e: Exception) {
            Log.e("NutriCoachRepository", "Error fetching fruit data: ${e.message}")
            emptyMap()
        }
    }

    /**
     * Save a motivational tip to the Room DB
     */
    suspend fun saveTip(userId: String, message: String) = withContext(Dispatchers.IO) {
        tipDao.insert(NutriCoachTips(userId = userId, message = message, timestamp = System.currentTimeMillis()))
    }

    /**
     * Delete all tips (e.g. on app restart)
     */
    suspend fun clearTips() = withContext(Dispatchers.IO) {
        tipDao.clearAll()
    }

    /**
     * Load saved tips
     */
    fun getTips(userId: String) = tipDao.getTipsForUser(userId)

    fun generatePersonalisedTipPrompt(patient: Patient): String {
        val isMale = patient.sex.equals("Male", ignoreCase = true)
        val selectedEntry = if (isMale) {
            listOf(
                "Vegetables" to patient.heifaVegMale,
                "Fruits" to patient.heifafruitsMale,
                "Grain/Cereal" to patient.heifaGrainCerealMale,
                "Whole Grain" to patient.heifaWholeGrainMale,
                "Meat" to patient.heifaMeatMale,
                "Dairy" to patient.heifaDairyMale,
                "Water" to patient.heifaWaterMale,
                "Unsaturated Fats" to patient.heifaUnsFatsMale,
                "Sodium" to patient.heifaSodiumMale,
                "Sugar" to patient.heifaSugarMale,
                "Alcohol" to patient.heifaAlcoholMale,
                "Discretionary" to patient.heifaDiscretionaryMale,
                "Saturated Fats" to patient.heifaSatFatsMale,
            ).random()
        } else {
            listOf(
                "Vegetables" to patient.heifaVegFemale,
                "Fruits" to patient.heifafruitsFemale,
                "Grain/Cereal" to patient.heifaGrainCerealFemale,
                "Whole Grain" to patient.heifaWholeGrainFemale,
                "Meat" to patient.heifaMeatFemale,
                "Dairy" to patient.heifaDairyFemale,
                "Water" to patient.heifaWaterFemale,
                "Unsaturated Fats" to patient.heifaUnsFatsFemale,
                "Sodium" to patient.heifaSodiumFemale,
                "Sugar" to patient.heifaSugarFemale,
                "Alcohol" to patient.heifaAlcoholFemale,
                "Discretionary" to patient.heifaDiscretionaryFemale,
                "Saturated Fats" to patient.heifaSatFatsFemale,
            ).random()
        }
        val (category, score) = selectedEntry

        return """
            A ${patient.sex} patient has the following score in a dietary category:
            
            - $category: $score
        
            Give a short, specific nutrition tip (max 20 words) about improving or maintaining this category. Use a supportive tone.
        """.trimIndent()
    }
}
