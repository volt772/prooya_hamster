package com.apx5.apx5.repository

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PrExecutors {
    val default: ExecutorService = Executors.newSingleThreadExecutor()
}