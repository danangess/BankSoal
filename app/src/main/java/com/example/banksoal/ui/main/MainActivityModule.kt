package com.example.banksoal.ui.main

import android.arch.lifecycle.ViewModelProvider
import com.example.banksoal.ViewModelProviderFactory
import com.example.banksoal.data.DataManager
import com.example.banksoal.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    internal fun mainViewModelProvider(mainViewModel: MainViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory<MainViewModel>(mainViewModel)
    }

    @Provides
    internal fun provideMainViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider): MainViewModel {
        return MainViewModel(dataManager, schedulerProvider)
    }
}