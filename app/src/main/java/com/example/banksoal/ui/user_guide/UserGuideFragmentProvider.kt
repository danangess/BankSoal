package com.example.banksoal.ui.user_guide

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UserGuideFragmentProvider {
    @ContributesAndroidInjector(modules = [UserGuideFragmentModule::class])
    abstract fun provideUserGuideFragmentFactory(): UserGuideFragment
}