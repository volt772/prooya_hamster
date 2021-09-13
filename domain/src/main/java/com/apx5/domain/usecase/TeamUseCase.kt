package com.apx5.domain.usecase

import com.apx5.domain.param.StaticsParam
import com.apx5.domain.param.UserRegisterParam
import com.apx5.domain.repository.PrRepository2

class TeamUseCase(private val prRepository2: PrRepository2) {
    suspend fun postUser(param: UserRegisterParam) = prRepository2.postUser(param)
}