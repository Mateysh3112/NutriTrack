package com.fit2081.a1mateysh33878463.data.fruityvice

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface FruityViceApi {
    @GET("/api/fruit/{name}")
    suspend fun getFruit(@Path("name") name: String): FruitResponse

    companion object {
        private const val BASE_URL = "https://www.fruityvice.com/api/"

        @OptIn(ExperimentalSerializationApi::class)
        fun create(): FruityViceApi {
            val json = Json { ignoreUnknownKeys = true}
            val contentType = "application/json".toMediaType()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(json.asConverterFactory(contentType))
                .build()

            return retrofit.create(FruityViceApi::class.java)
        }
    }
}
