package com.fit2081.a1mateysh33878463.functions

import android.content.Context
import android.util.Log
import com.fit2081.a1mateysh33878463.User
import java.io.BufferedReader
import java.io.InputStreamReader

/*
This function below  was built upon the CSV Counter Function code provided from the link below:
https://edstem.org/au/courses/20813/lessons/77916/slides/525130
 */
fun loadDataFromCSV (context: Context, fileName: String): List<User> {
    val users = mutableListOf<User>() // List to store the data
    var assets = context.assets //  Get asset manager

    try { // Try open CSV and read line by line
        val inputStream = assets.open(fileName) // Open file from assets
        // Create buffered reader for efficient reading
        val reader = BufferedReader(InputStreamReader(inputStream))

        val lines = reader.readLines() // Read all lines from file
        if (lines.isEmpty()) return emptyList()

        val headers = lines[0].split(",").map {it.trim().removePrefix("\uFEFF")}

        for (line in lines.drop(1)) {
            val columns = line.split(",").map {it.trim()}
            if (columns.size < headers.size) continue

            val dataMap = headers.zip(columns).toMap()

            val user = User(
                phoneNumber = dataMap["PhoneNumber"] ?: "",
                userId = dataMap["User_ID"] ?: "",
                sex = dataMap["Sex"] ?: "",
                heifaScoreMale = dataMap["HEIFAtotalscoreMale"] ?: "",
                heifaScoreFemale = dataMap["HEIFAtotalscoreFemale"] ?: "",
                heifaDiscretionaryMale = dataMap["DiscretionaryHEIFAscoreMale"] ?: "",
                heifaDiscretionaryFemale = dataMap["DiscretionaryHEIFAscoreFemale"] ?: "",
                heifaVegMale = dataMap["VegetablesHEIFAscoreMale"] ?: "",
                heifaVegFemale = dataMap["VegetablesHEIFAscoreFemale"] ?: "",
                heifafruitsMale = dataMap["FruitHEIFAscoreMale"] ?: "",
                heifafruitsFemale = dataMap["FruitHEIFAscoreFemale"] ?: "",
                heifaGrainCerealMale = dataMap["GrainsandcerealsHEIFAscoreMale"] ?: "",
                heifaGrainCerealFemale = dataMap["GrainsandcerealsHEIFAscoreFemale"] ?: "",
                heifaWholeGrainMale = dataMap["WholegrainsHEIFAscoreMale"] ?: "",
                heifaWholeGrainFemale = dataMap["WholegrainsHEIFAscoreFemale"] ?: "",
                heifaMeatMale = dataMap["MeatandalternativesHEIFAscoreMale"] ?: "",
                heifaMeatFemale = dataMap["MeatandalternativesHEIFAscoreFemale"] ?: "",
                heifaDairyMale = dataMap["DairyandalternativesHEIFAscoreMale"] ?: "",
                heifaDairyFemale = dataMap["DairyandalternativesHEIFAscoreFemale"] ?: "",
                heifaWaterMale = dataMap["WaterHEIFAscoreMale"] ?: "",
                heifaWaterFemale = dataMap["WaterHEIFAscoreFemale"] ?: "",
                heifaUnsFatsMale = dataMap["UnsaturatedFatHEIFAscoreMale"] ?: "",
                heifaUnsFatsFemale = dataMap["UnsaturatedFatHEIFAscoreFemale"] ?: "",
                heifaSodiumMale = dataMap["SodiumHEIFAscoreMale"] ?: "",
                heifaSodiumFemale = dataMap["SodiumHEIFAscoreFemale"] ?: "",
                heifaSugarMale = dataMap["SugarHEIFAscoreMale"] ?: "",
                heifaSugarFemale = dataMap["SugarHEIFAscoreFemale"] ?: "",
                heifaAlcoholMale = dataMap["AlcoholHEIFAscoreMale"] ?: "",
                heifaAlcoholFemale = dataMap["AlcoholHEIFAscoreFemale"] ?: "",
                heifaSatFatsMale = dataMap["SaturatedFatHEIFAscoreMale"] ?: "",
                heifaSatFatsFemale = dataMap["SaturatedFatHEIFAscoreFemale"] ?: ""
            )
            users.add(user)
            Log.d("CSV_DEBUG", "DataMap: $dataMap")
            Log.d("CSV", "users: $user")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        // Handle any exceptions that might occur during file reading
        // In case file does not exist or is corrupted
    }
    return users
}