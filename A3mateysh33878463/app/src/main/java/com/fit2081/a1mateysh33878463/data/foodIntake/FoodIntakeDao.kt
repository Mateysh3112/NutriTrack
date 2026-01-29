package com.fit2081.a1mateysh33878463.data.foodIntake

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FoodIntakeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(foodIntake: FoodIntake)

    @Query("SELECT * FROM foodintake WHERE userId = :userId")
    fun getFoodIntake(userId: String): LiveData<FoodIntake?>

    // Suspend version (for repository use in login check)
    @Query("SELECT * FROM foodintake WHERE userId = :userId")
    suspend fun getFoodIntakeImmediate(userId: String): FoodIntake?
}