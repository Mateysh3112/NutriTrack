package com.fit2081.a1mateysh33878463.data.nutricoachai

sealed interface NutriCoachAiUiState {
    object Initial : NutriCoachAiUiState
    object Loading : NutriCoachAiUiState
    data class Success(val outputText: String) : NutriCoachAiUiState
    data class Error(val errorMessage: String) : NutriCoachAiUiState
}