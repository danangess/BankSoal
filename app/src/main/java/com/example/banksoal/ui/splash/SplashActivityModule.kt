package com.example.banksoal.ui.splash

import dagger.Module
import com.example.banksoal.utils.rx.SchedulerProvider
import com.example.banksoal.data.DataManager
import dagger.Provides

@Module
class SplashActivityModule {
    @Provides
    fun provideSplashViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider): SplashViewModel {
        return SplashViewModel(dataManager, schedulerProvider)
    }
}