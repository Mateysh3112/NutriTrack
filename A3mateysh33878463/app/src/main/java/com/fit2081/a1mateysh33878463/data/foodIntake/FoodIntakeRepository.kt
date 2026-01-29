package com.fit2081.a1mateysh33878463.data.foodIntake

import androidx.lifecycle.LiveData

class FoodIntakeRepository(private val dao: FoodIntakeDao) {
    suspend fun insert(foodIntake: FoodIntake) = dao.insert(foodIntake)
    fun getFoodIntake(id: String): LiveData<FoodIntake?> = dao.getFoodIntake(id)
}