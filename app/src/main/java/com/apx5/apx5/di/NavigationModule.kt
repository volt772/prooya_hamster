package com.apx5.apx5.di

import androidx.fragment.app.Fragment
import com.apx5.apx5.navigator.PrNavigator
import com.apx5.apx5.navigator.PrNavigatorImpl
import com.apx5.apx5.network.api.PrApiService
import com.apx5.apx5.ui.recordteam.RecordTeamFragment
import com.apx5.apx5.ui.recordteam.RecordTeamNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

/**
 * NavigationModule
 */
@Module
@InstallIn(SingletonComponent::class)
//@InstallIn(ViewModelComponent::class)
abstract class NavigationModule {

    @Binds
    abstract fun bindPrNavigator(impl: PrNavigatorImpl): PrNavigator
}