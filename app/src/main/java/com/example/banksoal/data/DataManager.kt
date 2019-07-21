package com.example.banksoal.data

import com.example.banksoal.data.local.db.*
import com.example.banksoal.data.local.prefs.PreferencesHelper
import com.example.banksoal.data.model.*
import io.reactivex.Observable
import io.reactivex.Single

interface DataManager {
    val preferencesHelper: PreferencesHelper
    val userRepository: UserRepository
    val courseRepository: CourseRepository
    val questionRepository: QuestionRepository
    val optionRepository: OptionRepository

    fun getUserGuide(): Observable<List<String>>

    fun getCourseData(): Observable<List<CourseData>>

    fun getCourse(courseId: Long): Single<CourseData>

    fun getQuestionGroupData(courseId: Long): Observable<List<String>>

    fun getQuestionData(courseId: Long): Observable<List<QuestionData>>

//    fun getQuestionData(courseId: Long, group: String): Observable<List<QuestionData>>

    fun seedDatabaseUsers(): Observable<Boolean>

    fun seedDatabaseCourses(): Observable<Boolean>

    fun seedDatabaseQuestions(): Observable<Boolean>

    fun seedDatabaseOptions(): Observable<Boolean>

    fun truncateDatabaseCourses(): Observable<Boolean>

    fun truncateDatabaseQuestions(): Observable<Boolean>

    fun truncateDatabaseOptions(): Observable<Boolean>

    fun setUserAsLoggedOut()

    fun updateUserInfo(userName: String?)

    fun doLogin(userName: String, password: String): Single<Boolean>

    fun doLogout(): Single<Boolean>

    fun doSignUp(firstName: String, lastName: String, userName: String, password: String): Single<Boolean>

    enum class LoggedInMode {
        LOGOUT,
        LOGIN,
    }
}