package com.fit2081.a1mateysh33878463.data.foodIntake

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.fit2081.a1mateysh33878463.data.patient.Patient

@Entity(
    foreignKeys = [ForeignKey(
        entity = Patient::class,
        parentColumns = ["userId"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)

data class FoodIntake(
    @PrimaryKey val userId: String,
    val fruits: Boolean,
    val vegetables: Boolean,
    val grains: Boolean,
    val redMeat: Boolean,
    val seafood: Boolean,
    val poultry: Boolean,
    val fish: Boolean,
    val eggs: Boolean,
    val nutsSeeds: Boolean,
    val persona: String,
    val biggestMeal: String,
    val sleepTime: String,
    val wakeTime: String
)