package com.aaron.pigeons.domain.repository

import com.aaron.pigeons.remote.dto.PigeonDto

interface PigeonRepository {
    suspend fun getPigeons(): List<PigeonDto>
}