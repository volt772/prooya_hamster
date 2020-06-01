package com.apx5.apx5.repository

import android.content.Context
import com.apx5.apx5.network.PrDataApiHelper

class PrRepository private constructor(
    private val applicationContext: Context
) {
    /* 쓰래드 기능*/
    private val executors = PrExecutors()

    /* 통신 API 관리*/
    private val apiHelper = PrDataApiHelper()

    /* Repository : Game*/
    val game: PrGameRepository by lazy { PrGameRepository(applicationContext, executors, apiHelper) }

    /* Repository : Setting*/
    val setting: PrSettingRepository by lazy { PrSettingRepository(applicationContext, executors, apiHelper) }
}