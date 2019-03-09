package com.example.banksoal.ui.quiz

import com.example.banksoal.data.DataManager
import com.example.banksoal.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class QuizActivityModule {
    @Provides
    fun provideQuizViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider): QuizViewModel{
        return QuizViewModel(dataManager, schedulerProvider)
    }
}