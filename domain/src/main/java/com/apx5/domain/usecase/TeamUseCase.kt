package com.apx5.domain.usecase

import com.apx5.domain.param.UserRegisterParam
import com.apx5.domain.repository.PrRepository

/**
 *
 */

class TeamUseCase(
    private val prRepository: PrRepository
) {

    suspend fun postUser(param: UserRegisterParam) = prRepository.postUser(param)
}