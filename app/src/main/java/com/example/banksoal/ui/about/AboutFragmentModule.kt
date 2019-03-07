package com.example.banksoal.ui.about

import com.example.banksoal.data.DataManager
import com.example.banksoal.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class AboutFragmentModule {
    @Provides
    fun provideAboutViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider): AboutViewModel {
        return AboutViewModel(dataManager, schedulerProvider)
    }
}