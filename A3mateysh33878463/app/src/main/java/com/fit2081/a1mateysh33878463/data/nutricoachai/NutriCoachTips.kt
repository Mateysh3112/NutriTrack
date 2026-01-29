package com.fit2081.a1mateysh33878463.data.nutricoachai

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nutri_coach_tips")
data class NutriCoachTips(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val message: String,
    val timestamp: Long
)

