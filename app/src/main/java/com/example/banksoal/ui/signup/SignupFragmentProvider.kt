package com.example.banksoal.ui.signup

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SignupFragmentProvider {
    @ContributesAndroidInjector(modules = [SignupFragmentModule::class])
    abstract fun provideSignupFragmentFactory(): SignupFragment
}