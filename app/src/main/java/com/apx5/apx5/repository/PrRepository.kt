package com.apx5.apx5.repository

import com.apx5.apx5.datum.pitcher.PtPostStatics
import com.apx5.apx5.network.api.PrApi

class PrRepository(private val prApi: PrApi) {
    suspend fun getServerStatus() = prApi.getServerStatus()
    suspend fun getStatics(param: PtPostStatics) = prApi.getStatics2(param)
}
