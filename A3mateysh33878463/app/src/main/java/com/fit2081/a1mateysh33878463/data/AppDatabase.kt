package com.fit2081.a1mateysh33878463.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fit2081.a1mateysh33878463.data.foodIntake.FoodIntake
import com.fit2081.a1mateysh33878463.data.foodIntake.FoodIntakeDao
import com.fit2081.a1mateysh33878463.data.nutricoachai.NutriCoachTips
import com.fit2081.a1mateysh33878463.data.nutricoachai.NutriCoachTipDao
import com.fit2081.a1mateysh33878463.data.patient.Patient
import com.fit2081.a1mateysh33878463.data.patient.PatientDao

@Database (entities = [Patient::class, FoodIntake::class, NutriCoachTips::class], version = 13)
abstract class AppDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
    abstract fun foodIntakeDao(): FoodIntakeDao
    abstract fun nutriCoachTipDao(): NutriCoachTipDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Application): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "nutritrack_db"
                )
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
        }
    }
}