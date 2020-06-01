package com.apx5.apx5.network

import com.apx5.apx5.constants.PrConstants

class PrDataApiHelper {

    private var api: PrDataApi? = null

    @Synchronized
    fun getApi(): PrDataApi {
        api = getDataApi()
        return api as PrDataApi
    }

    private fun getDataApi(): PrDataApi {
        val baseUrl = PrConstants.App.FLK_HOST
        return PrDataApiInterface.getClient(baseUrl)
    }
}
