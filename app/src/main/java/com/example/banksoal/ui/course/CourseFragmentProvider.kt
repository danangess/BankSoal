package com.example.banksoal.ui.course

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CourseFragmentProvider {
    @ContributesAndroidInjector(modules = [CourseFragmentModule::class])
    abstract fun provideCourseFragmentFactory(): CourseFragment
}