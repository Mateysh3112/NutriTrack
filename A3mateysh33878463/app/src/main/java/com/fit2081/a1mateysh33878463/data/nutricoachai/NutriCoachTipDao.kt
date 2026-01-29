package com.fit2081.a1mateysh33878463.data.nutricoachai

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NutriCoachTipDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tip: NutriCoachTips)

    @Query("SELECT * FROM nutri_coach_tips WHERE userId = :userId ORDER BY timestamp DESC")
    fun getTipsForUser(userId: String): LiveData<List<NutriCoachTips>>

    @Query("DELETE FROM nutri_coach_tips")
    suspend fun clearAll()

}