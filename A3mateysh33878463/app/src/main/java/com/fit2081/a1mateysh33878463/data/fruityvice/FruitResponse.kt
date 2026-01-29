package com.fit2081.a1mateysh33878463.data.fruityvice

import kotlinx.serialization.SerialName

import kotlinx.serialization.Serializable

@Serializable
data class FruitResponse(
    val id: Int,
    val name: String,
    val family: String,
    val order: String,
    val genus: String,
    val nutritions: Nutrition
)