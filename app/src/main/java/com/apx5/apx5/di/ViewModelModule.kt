package com.apx5.apx5.di

import com.apx5.apx5.ui.dashboard.DashBoardViewModel
import com.apx5.apx5.ui.days.DaysViewModel
import com.apx5.apx5.ui.recordall.RecordAllViewModel
import com.apx5.apx5.ui.recordteam.RecordTeamViewModel
import com.apx5.apx5.ui.setting.SettingViewModel
import com.apx5.apx5.ui.splash.SplashViewModel
import com.apx5.apx5.ui.statics.StaticsViewModel
import com.apx5.apx5.ui.team.TeamViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { TeamViewModel(get()) }
    viewModel { DashBoardViewModel() }
    viewModel { StaticsViewModel(get()) }
    viewModel { RecordTeamViewModel(get()) }
    viewModel { RecordAllViewModel(get()) }
    viewModel { DaysViewModel(get()) }
    viewModel { SettingViewModel(get()) }
}