package com.apx5.apx5.base

import android.content.Context
import com.apx5.apx5.network.PrDataApiHelper
import com.apx5.apx5.repository.PrExecutors

abstract class BaseRepository (
    val applicationContext: Context,
    val es: PrExecutors,
    val apiHelper: PrDataApiHelper
)
