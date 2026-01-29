package com.fit2081.a1mateysh33878463.data.admin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.fit2081.a1mateysh33878463.data.nutricoachai.NutriCoachAiUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AdminRepository(application)
    private val _genAiState = MutableStateFlow<NutriCoachAiUiState>(NutriCoachAiUiState.Initial)
    val genAiPatterns: StateFlow<NutriCoachAiUiState> = _genAiState.asStateFlow()

    val maleAverage = liveData(Dispatchers.IO) { emit(repository.getMaleAverage()) }
    val femaleAverage = liveData(Dispatchers.IO) { emit(repository.getFemaleAverage()) }

    fun generatePatterns() {
        _genAiState.value = NutriCoachAiUiState.Loading

        viewModelScope.launch {
            try {
                val patients = repository.getAllPatients()
                val prompt = repository.generateInsightsPrompt(patients)
                val result = repository.getPatternInsights(prompt)
                _genAiState.value = NutriCoachAiUiState.Success(result)
            } catch (e: Exception) {
                _genAiState.value = NutriCoachAiUiState.Error("Failed: ${e.localizedMessage}")
            }
        }
    }
}
