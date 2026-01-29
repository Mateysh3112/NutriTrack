package com.fit2081.a1mateysh33878463.functions

import android.content.Context
import android.util.Log
import com.fit2081.a1mateysh33878463.data.AppDatabase
import com.fit2081.a1mateysh33878463.data.patient.Patient

suspend fun migrateCSV(context: Context, db: AppDatabase) {
    val prefs = context.getSharedPreferences("migrationPrefs", Context.MODE_PRIVATE)
    val migrated = prefs.getBoolean("csvMigrated", false)
    if (!migrated) {
        val users = loadDataFromCSV(context, "users.csv")
        db.patientDao().insertAll(users.map {
            Patient(
                userId = it.userId,
                phoneNumber = it.phoneNumber,
                name = "",
                password = "",
                sex = it.sex,
                heifaScoreMale = it.heifaScoreMale,
                heifaScoreFemale = it.heifaScoreFemale,
                heifaVegFemale = it.heifaVegFemale,
                heifaVegMale = it.heifaVegMale,
                heifafruitsFemale = it.heifafruitsFemale,
                heifafruitsMale = it.heifafruitsMale,
                heifaGrainCerealFemale = it.heifaGrainCerealFemale,
                heifaGrainCerealMale = it.heifaGrainCerealMale,
                heifaWholeGrainFemale = it.heifaWholeGrainFemale,
                heifaWholeGrainMale = it.heifaWholeGrainMale,
                heifaMeatFemale = it.heifaMeatFemale,
                heifaMeatMale = it.heifaMeatMale,
                heifaDairyFemale = it.heifaDairyFemale,
                heifaDairyMale = it.heifaDairyMale,
                heifaWaterFemale = it.heifaWaterFemale,
                heifaWaterMale = it.heifaWaterMale,
                heifaUnsFatsFemale = it.heifaUnsFatsFemale,
                heifaUnsFatsMale = it.heifaUnsFatsMale,
                heifaSodiumFemale = it.heifaSodiumFemale,
                heifaSodiumMale = it.heifaSodiumMale,
                heifaSugarFemale = it.heifaSugarFemale,
                heifaSugarMale = it.heifaSugarMale,
                heifaAlcoholFemale = it.heifaAlcoholFemale,
                heifaAlcoholMale = it.heifaAlcoholMale,
                heifaDiscretionaryFemale = it.heifaDiscretionaryFemale,
                heifaDiscretionaryMale = it.heifaDiscretionaryMale,
                heifaSatFatsMale = it.heifaSatFatsMale,
                heifaSatFatsFemale = it.heifaSatFatsFemale
            )
        })
        prefs.edit().putBoolean("csvMigrated", true).apply()
    }
}