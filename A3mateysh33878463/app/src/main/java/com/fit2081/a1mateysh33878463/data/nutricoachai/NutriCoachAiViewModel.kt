package com.fit2081.a1mateysh33878463.data.nutricoachai

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.a1mateysh33878463.BuildConfig
import com.fit2081.a1mateysh33878463.data.patient.Patient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerationConfig



class NutriCoachAiViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NutriCoachAiRepository(application)

    private val _uiState = MutableStateFlow<NutriCoachAiUiState>(NutriCoachAiUiState.Initial)
    val uiState: StateFlow<NutriCoachAiUiState> = _uiState.asStateFlow()

    // Gemini GenerativeModel with adjustable temperature for creativity
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey,
        generationConfig = GenerationConfig.Builder().apply {
            temperature = 1.0f // adjust between 0.0 (more deterministic) and 1.0 (more creative)
        }.build()
    )

    init {
        // Clear previous tips when app launches
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearTips()
        }
    }

    /**
     * Generates a motivational tip using Gemini and saves it.
     */
    fun sendPrompt(prompt: String, userId: String) {
        _uiState.value = NutriCoachAiUiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(prompt)
                val resultText = response.text

                if (!resultText.isNullOrBlank()) {
                    _uiState.value = NutriCoachAiUiState.Success(resultText)
                    repository.saveTip(userId, resultText)
                } else {
                    _uiState.value = NutriCoachAiUiState.Error("Empty AI response")
                }
            } catch (e: Exception) {
                _uiState.value = NutriCoachAiUiState.Error("Gemini SDK error: ${e.localizedMessage}")
            }
        }
    }

    /**
     * Gets the patient object based on user ID.
     */
    suspend fun getPatient(userId: String): Patient {
        return repository.getPatient(userId)
    }

    /**
     * Gets motivational tips stored in Room for this user.
     */
    fun getTips(userId: String) = repository.getTips(userId)

    /**
     * Calls the FruityVice API and returns fruit nutrition info.
     */
    fun getFruitDetails(fruitName: String, onResult: (Map<String, String>) -> Unit) {
        viewModelScope.launch {
            try {
                val result = repository.getFruitDetails(fruitName)
                onResult(result)
            } catch (e: Exception) {
                onResult(emptyMap()) // handle API/network error
            }
        }
    }

    fun generatePersonalisedTip(userId: String) {
        _uiState.value = NutriCoachAiUiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val patient = repository.getPatient(userId)
                val prompt = repository.generatePersonalisedTipPrompt(patient)

                val response = generativeModel.generateContent(prompt) // 👈 THIS IS THE API CALL
                val text = response.text

                if (!text.isNullOrBlank()) {
                    _uiState.value = NutriCoachAiUiState.Success(text)
                    repository.saveTip(userId, text)
                } else {
                    _uiState.value = NutriCoachAiUiState.Error("Empty response from Gemini")
                }
            } catch (e: Exception) {
                _uiState.value = NutriCoachAiUiState.Error("Error: ${e.localizedMessage}")
            }
        }
    }


}