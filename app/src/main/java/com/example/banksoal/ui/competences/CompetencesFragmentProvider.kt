package com.example.banksoal.ui.competences

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CompetencesFragmentProvider {
    @ContributesAndroidInjector(
        modules = [
            CompetencesFragmentModule::class]
    )
    abstract fun provideCompetencesFragmentFactory(): CompetencesFragment
}