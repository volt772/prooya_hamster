package com.apx5.apx5.di

import com.apx5.apx5.repository.PrRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { PrRepository(get()) }
}