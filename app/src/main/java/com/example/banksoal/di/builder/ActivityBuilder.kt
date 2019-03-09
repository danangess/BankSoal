package com.example.banksoal.di.builder

import com.example.banksoal.ui.about.AboutFragmentProvider
import com.example.banksoal.ui.course.CourseFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.example.banksoal.ui.splash.SplashActivity
import com.example.banksoal.ui.login.LoginActivity
import com.example.banksoal.ui.login.LoginActivityModule
import com.example.banksoal.ui.main.MainActivity
import com.example.banksoal.ui.main.MainActivityModule
import com.example.banksoal.ui.main.fragment.MainFragmentProvider
import com.example.banksoal.ui.quiz.QuizActivity
import com.example.banksoal.ui.quiz.QuizActivityModule
import com.example.banksoal.ui.signup.SignupFragmentProvider
import com.example.banksoal.ui.splash.SplashActivityModule

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [
        LoginActivityModule::class,
        SignupFragmentProvider::class
    ])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [
        MainActivityModule::class,
        MainFragmentProvider::class,
        CourseFragmentProvider::class,
        AboutFragmentProvider::class
    ])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [
        QuizActivityModule::class
    ])
    abstract fun bindQuizActivity(): QuizActivity

    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun bindSplashActivity(): SplashActivity
}