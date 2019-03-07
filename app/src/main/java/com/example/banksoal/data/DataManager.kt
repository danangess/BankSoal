package com.example.banksoal.data

import com.example.banksoal.data.local.db.*
import com.example.banksoal.data.local.prefs.PreferencesHelper
import com.example.banksoal.data.model.*
import io.reactivex.Observable
import io.reactivex.Single

interface DataManager : PreferencesHelper {
    val userRepository: UserRepository
    val courseRepository: CourseRepository
    val questionRepository: QuestionRepository
    val optionRepository: OptionRepository

    fun getCourseData(): Observable<List<CourseData>>

    fun getQuestionData(courseId: Long): Observable<List<QuestionData>>

    fun seedDatabaseCourses(): Observable<Boolean>

    fun seedDatabaseQuestions(): Observable<Boolean>

    fun seedDatabaseOptions(): Observable<Boolean>

    fun setUserAsLoggedOut()

    fun updateUserInfo(userName: String?)

    fun doLogin(userName: String, password: String): Single<Boolean>

    fun doLogout(): Single<Boolean>

    fun doSignUp(firstName: String, lastName: String, userName: String, password: String): Single<Boolean>

    enum class LoggedInMode{
        LOGOUT,
        LOGIN,
    }
}