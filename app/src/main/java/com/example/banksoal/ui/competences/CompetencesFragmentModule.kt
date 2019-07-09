package com.example.banksoal.ui.competences

import com.example.banksoal.data.DataManager
import com.example.banksoal.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class CompetencesFragmentModule {
    @Provides
    fun provideCompetencesViewModel(
        dataManager: DataManager,
        schedulerProvider: SchedulerProvider
    ): CompetencesViewModel {
        return CompetencesViewModel(dataManager, schedulerProvider)
    }
}