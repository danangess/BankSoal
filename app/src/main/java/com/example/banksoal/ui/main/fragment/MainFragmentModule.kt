package com.example.banksoal.ui.main.fragment

import com.example.banksoal.data.DataManager
import com.example.banksoal.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class MainFragmentModule {
    @Provides
    fun provideMainFragmentViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider): MainFragmentViewModel {
        return MainFragmentViewModel(dataManager, schedulerProvider)
    }
}