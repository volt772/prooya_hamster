package com.apx5.domain.usecase

import com.apx5.domain.param.UserDelParam
import com.apx5.domain.repository.PrRepository

/**
 * SettingUseCase
 */

class SettingUseCase(
    private val prRepository: PrRepository
) {

    suspend fun delUser(param: UserDelParam) = prRepository.delUser(param)
}