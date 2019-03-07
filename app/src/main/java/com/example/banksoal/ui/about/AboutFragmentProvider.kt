package com.example.banksoal.ui.about

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AboutFragmentProvider {
    @ContributesAndroidInjector(modules = [AboutFragmentModule::class])
    abstract fun provideAboutFragmentFactory(): AboutFragment
}