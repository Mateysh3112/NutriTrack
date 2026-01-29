package com.fit2081.a1mateysh33878463.data.foodIntake

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.fit2081.a1mateysh33878463.data.AppDatabase
import kotlinx.coroutines.launch

class FoodIntakeViewModel (application: Application): AndroidViewModel(application)  {
    private val db = AppDatabase.getDatabase(application)
    private val repo = FoodIntakeRepository(db.foodIntakeDao())

    fun saveFoodIntake(data: FoodIntake) {
        viewModelScope.launch {
            repo.insert(data)
        }
    }

    fun getFoodIntake(id: String): LiveData<FoodIntake?> = repo.getFoodIntake(id)

}
