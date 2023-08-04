package com.aaron.pigeons.remote

import com.aaron.pigeons.remote.dto.PigeonDto
import retrofit2.http.GET

interface PigeonApi {
    @GET("pictures.json")
    suspend fun getPigeons(): List<PigeonDto>
}