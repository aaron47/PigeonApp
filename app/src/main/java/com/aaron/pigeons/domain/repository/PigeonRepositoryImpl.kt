package com.aaron.pigeons.domain.repository

import com.aaron.pigeons.remote.PigeonApi
import com.aaron.pigeons.remote.dto.PigeonDto
import javax.inject.Inject

class PigeonRepositoryImpl @Inject constructor(private val pigeonApi: PigeonApi) :
    PigeonRepository {
    override suspend fun getPigeons(): List<PigeonDto> = pigeonApi.getPigeons()
}