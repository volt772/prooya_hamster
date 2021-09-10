package com.apx5.data.repository

import com.apx5.data.network.PrApiService2
import com.apx5.domain.dto.ServerStatusVO
import com.apx5.domain.repository.PrRepository2
import javax.inject.Inject

class PrRepositoryImpl @Inject constructor(private val prApiService: PrApiService2): PrRepository2 {
    override suspend fun getServerStatus(): ServerStatusVO {
        val res = prApiService.getServerStatus()
        return ServerStatusVO(res.data?.status?: 0)
    }
}