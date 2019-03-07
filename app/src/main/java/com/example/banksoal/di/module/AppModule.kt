package com.example.banksoal.di.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.example.banksoal.data.AppDataManager
import com.example.banksoal.data.DataManager
import com.example.banksoal.data.local.db.*
import com.example.banksoal.data.local.prefs.AppPreferencesHelper
import com.example.banksoal.data.local.prefs.PreferencesHelper
import com.example.banksoal.di.DatabaseInfo
import com.example.banksoal.di.PreferenceInfo
import com.example.banksoal.utils.AppConstants
import com.example.banksoal.utils.rx.AppSchedulerProvider
import com.example.banksoal.utils.rx.SchedulerProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@DatabaseInfo dbName: String, context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, dbName)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String {
        return AppConstants.DB_NAME
    }

    @Provides
    @Singleton
    fun provideCourseRepository(appCourseRepository: AppCourseRepository): CourseRepository {
        return appCourseRepository
    }

    @Provides
    @Singleton
    fun provideQuestionRepository(appQuestionRepository: AppQuestionRepository): QuestionRepository {
        return appQuestionRepository
    }

    @Provides
    @Singleton
    fun provideOptionRepository(appOptionRepository: AppOptionRepository): OptionRepository {
        return appOptionRepository
    }

    @Provides
    @Singleton
    fun provideUserRepository(appUserRepository: AppUserRepository): UserRepository {
        return appUserRepository
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String {
        return AppConstants.PREF_NAME
    }

    @Provides
    @Singleton
    fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): PreferencesHelper {
        return appPreferencesHelper
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }
}
