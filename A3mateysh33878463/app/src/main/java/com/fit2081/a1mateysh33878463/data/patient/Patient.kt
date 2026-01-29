package com.fit2081.a1mateysh33878463.data.patient

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class Patient(
    @PrimaryKey val userId: String,
    val name: String,
    val password: String,
    val phoneNumber: String,
    val sex: String,
    val heifaScoreMale: String,
    val heifaScoreFemale: String,
    val heifaVegFemale: String,
    val heifaVegMale: String,
    val heifafruitsFemale: String,
    val heifafruitsMale: String,
    val heifaGrainCerealFemale: String,
    val heifaGrainCerealMale: String,
    val heifaWholeGrainFemale: String,
    val heifaWholeGrainMale: String,
    val heifaMeatFemale: String,
    val heifaMeatMale: String,
    val heifaDairyFemale: String,
    val heifaDairyMale: String,
    val heifaWaterFemale: String,
    val heifaWaterMale: String,
    val heifaUnsFatsFemale: String,
    val heifaUnsFatsMale: String,
    val heifaSodiumFemale: String,
    val heifaSodiumMale: String,
    val heifaSugarFemale: String,
    val heifaSugarMale: String,
    val heifaAlcoholFemale: String,
    val heifaAlcoholMale: String,
    val heifaDiscretionaryFemale: String,
    val heifaDiscretionaryMale: String,
    val heifaSatFatsMale: String,
    val heifaSatFatsFemale: String
)