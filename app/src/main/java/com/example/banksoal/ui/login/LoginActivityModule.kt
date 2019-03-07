package com.example.banksoal.ui.login

import android.arch.lifecycle.ViewModelProvider
import com.example.banksoal.ViewModelProviderFactory
import dagger.Module
import com.example.banksoal.utils.rx.SchedulerProvider
import com.example.banksoal.data.DataManager
import dagger.Provides



@Module
class LoginActivityModule {
    @Provides
    fun loginViewModelProvider(loginViewModel: LoginViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory<Any>(loginViewModel)
    }

    @Provides
    fun provideLoginViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider): LoginViewModel {
        return LoginViewModel(dataManager, schedulerProvider)
    }
}