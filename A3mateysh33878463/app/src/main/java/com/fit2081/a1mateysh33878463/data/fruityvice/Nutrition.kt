package com.fit2081.a1mateysh33878463.data.fruityvice

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Nutrition(
    val calories: Float,
    val fat: Float,
    val sugar: Float,
    val carbohydrates: Float,
    val protein: Float
)