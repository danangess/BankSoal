package com.example.banksoal.ui.course

import com.example.banksoal.data.DataManager
import com.example.banksoal.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class CourseFragmentModule {
    @Provides
    fun provideCourseViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider): CourseViewModel {
        return CourseViewModel(dataManager, schedulerProvider)
    }
}