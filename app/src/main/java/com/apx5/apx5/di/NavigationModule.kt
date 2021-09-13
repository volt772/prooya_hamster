package com.apx5.apx5.di

import com.apx5.apx5.navigator.PrNavigator
import com.apx5.apx5.navigator.PrNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * NavigationModule
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {

    @Binds
    abstract fun bindPrNavigator(impl: PrNavigatorImpl): PrNavigator
}