package com.example.banksoal.ui.signup

import com.example.banksoal.data.DataManager
import com.example.banksoal.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class SignupFragmentModule {
    @Provides
    fun provideSignupViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider): SignupViewModel {
        return SignupViewModel(dataManager, schedulerProvider)
    }
}