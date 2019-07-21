package com.example.banksoal.ui.user_guide

import com.example.banksoal.data.DataManager
import com.example.banksoal.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class UserGuideFragmentModule {
    @Provides
    fun provideUserGuidViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider): UserGuideViewModel {
        return UserGuideViewModel(dataManager, schedulerProvider)
    }
}