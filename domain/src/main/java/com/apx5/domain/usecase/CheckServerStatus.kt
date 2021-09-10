package com.apx5.domain.usecase

import com.apx5.domain.repository.PrRepository2

class CheckServerStatus(private val hamsterRepository: PrRepository2) {
    suspend fun execute() = hamsterRepository.getServerStatus()
}