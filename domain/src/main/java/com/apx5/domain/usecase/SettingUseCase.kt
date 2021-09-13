package com.apx5.domain.usecase

import com.apx5.domain.param.UserDelParam
import com.apx5.domain.repository.PrRepository2

class SettingUseCase(private val prRepository2: PrRepository2) {
    suspend fun delUser(param: UserDelParam) = prRepository2.delUser(param)
}